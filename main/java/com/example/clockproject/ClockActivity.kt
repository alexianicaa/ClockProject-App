package com.example.clockproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.InetSocketAddress
import java.net.Socket

private const val SERVER_PORT = 8080
private const val INTERNET_PERMISSION_CODE = 1001

fun createJsonPacket(action: String, hour: Int? = null, minute: Int? = null): String {
    val jsonData = JSONObject()
    jsonData.put("action", action)
    hour?.let { jsonData.put("hour", it) }
    minute?.let { jsonData.put("minute", it) }
    return jsonData.toString()
}

fun sendPackage(ipAddress: String, jsonPacket: String) {
    Thread {
        try {
            val socket = Socket()
            val socketAddress = InetSocketAddress(ipAddress, SERVER_PORT)
            socket.connect(socketAddress, 1000)

            val writer = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
            writer.write(jsonPacket)
            writer.newLine()
            writer.flush()

            println("Sent successfully: $jsonPacket")
            socket.close()
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }.start()
}

class ClockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)

        val btnSetAlarm = findViewById<Button>(R.id.set_alarm_button)
        val hourEditText = findViewById<EditText>(R.id.hour_edit_text)
        val minuteEditText = findViewById<EditText>(R.id.minute_edit_text)
        val ipEditText = findViewById<EditText>(R.id.textIP)
        val btnTemperature = findViewById<Button>(R.id.temperature_button)
        val btnSettings = findViewById<Button>(R.id.settings_btn)

        btnSetAlarm.setOnClickListener {
            val ipAddress = ipEditText.text.toString()
            val hour = hourEditText.text.toString().toIntOrNull()
            val minute = minuteEditText.text.toString().toIntOrNull()

            if (hour == null || minute == null) {
                Toast.makeText(this, "Please enter valid time values.", Toast.LENGTH_SHORT).show()
            } else {
                val jsonPacket = createJsonPacket("set_alarm", hour, minute)
                sendPackage(ipAddress, jsonPacket)
                Toast.makeText(this, "Alarm set for $hour:$minute", Toast.LENGTH_SHORT).show()
            }
        }

        // Temperature Button
        btnTemperature.setOnClickListener {
            val ipAddress = ipEditText.text.toString()
            val jsonPacket = createJsonPacket("get_temperature")
            sendPackage(ipAddress, jsonPacket)
            Toast.makeText(this, "Requesting Temperature", Toast.LENGTH_SHORT).show()
        }



    }
}
