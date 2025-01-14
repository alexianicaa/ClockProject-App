package com.example.clockproject

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CalendarActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var location: EditText
    private lateinit var description: EditText
    private lateinit var addEvent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar2)

        title = findViewById(R.id.editText)
        location = findViewById(R.id.etLocation)
        description = findViewById(R.id.etDescription)
        addEvent = findViewById(R.id.btnAdd)

        addEvent.setOnClickListener {
            if (title.text.toString().isNotEmpty() &&
                location.text.toString().isNotEmpty() &&
                description.text.toString().isNotEmpty()
            ) {
                val intent = Intent(Intent.ACTION_INSERT).apply {
                    data = CalendarContract.Events.CONTENT_URI
                    putExtra(CalendarContract.Events.TITLE, title.text.toString())
                    putExtra(CalendarContract.Events.EVENT_LOCATION, location.text.toString())
                    putExtra(CalendarContract.Events.DESCRIPTION, description.text.toString())
                    putExtra(CalendarContract.Events.ALL_DAY, true)
                    putExtra(
                        Intent.EXTRA_EMAIL,
                        arrayOf("test@yahoo.com", "test2@yahoo.com", "test3@yahoo.com")
                    )
                }

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "There is no app that can support this action",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}