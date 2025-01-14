package com.example.clockproject

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.services03.QuoteFetchService

class FocusActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(this, "Notification permission is required for foreground service", Toast.LENGTH_SHORT).show()
            }
        }

    private var quoteService: QuoteFetchService? = null
    private var isServiceBound = false

    private val quoteServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as QuoteFetchService.QuoteBinder
            quoteService = binder.getService()
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            quoteService = null
            isServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focus)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        val chrono = findViewById<Chronometer>(R.id.chronometer)
        val startBtn = findViewById<Button>(R.id.focus_btn)
        val stopBtn = findViewById<Button>(R.id.stop_focus_btn)
        val quoteTextView = findViewById<TextView>(R.id.quoteTextView)

        startBtn.setOnClickListener {
            // Bind to the QuoteFetchService
            val intent = Intent(this, QuoteFetchService::class.java)
            bindService(intent, quoteServiceConnection, BIND_AUTO_CREATE)

            // Request permission if it's not already granted
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                // If permission is granted, start the services
                startService(Intent(this, ForegroundService::class.java))
                startService(Intent(this, BackgroundService::class.java))
            } else {
                // Otherwise, request the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }

            // Start the chronometer
            chrono.base = SystemClock.elapsedRealtime()
            chrono.start()

            // Update the quote text view
            if (isServiceBound) {
                val (quote, author) = quoteService?.getLatestQuote() ?: "No Quote" to ""
                quoteTextView.text = "\"$quote\"\n\n- $author"
            }
        }

        stopBtn.setOnClickListener {
            chrono.stop()

            // Stop the Foreground and Background Services
            stopService(Intent(this, ForegroundService::class.java))
            stopService(Intent(this, BackgroundService::class.java))

            // Unbind the service when stopping
            if (isServiceBound) {
                unbindService(quoteServiceConnection)
                isServiceBound = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unbind the service when the activity is destroyed
        if (isServiceBound) {
            unbindService(quoteServiceConnection)
            isServiceBound = false
        }
    }
}