package com.example.clockproject

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainPageActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(this, "Notification permission is required for foreground service", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        val btnClock = findViewById<Button>(R.id.clock_btn)
        val btnCalendar = findViewById<Button>(R.id.calendar_btn)
        val btnFocus = findViewById<Button>(R.id.focusPage_btn)
        btnClock.setOnClickListener {
            startActivity(Intent(this@MainPageActivity, ClockActivity::class.java))
        }
        btnCalendar.setOnClickListener {
            startActivity(Intent(this@MainPageActivity, CalendarActivity::class.java))
        }
        btnFocus.setOnClickListener {
            startActivity(Intent(this@MainPageActivity, FocusActivity::class.java))
        }



    }



}