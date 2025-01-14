package com.example.clockproject

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn = findViewById<TextView>(R.id.alreadyHaveAnAccount)
        btn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val regbtn = findViewById<Button>(R.id.btnRegister)
        regbtn.setOnClickListener {
            val username = findViewById<EditText>(R.id.inputUsername).text.toString().trim()
            val email = findViewById<EditText>(R.id.inputEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.inputPassword).text.toString()
            val confirmPassword = findViewById<EditText>(R.id.inputConfirmPassword).text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "You missed something!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Consider using encryption or a security library for password storage
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username", username)
            editor.putString("email", email)
            editor.putString("password", password) // Consider encrypting this
            editor.putBoolean("isRegistered", true)
            editor.apply()

            Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}