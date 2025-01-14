package com.example.clockproject

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSignUp = findViewById<TextView>(R.id.textSignUp)
        btnSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        val btnLogin = findViewById<TextView>(R.id.btnlogin)
        btnLogin.setOnClickListener {
            val emailInput = findViewById<EditText>(R.id.inputEmail).text.toString().trim()
            val passwordInput = findViewById<EditText>(R.id.inputPassword).text.toString()

            // Citim datele salvate în `SharedPreferences`
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val savedEmail = sharedPreferences.getString("email", null)
            val savedPassword = sharedPreferences.getString("password", null)

            // Verificăm dacă datele introduse sunt corecte
            if (emailInput == savedEmail && passwordInput == savedPassword) {
                // Login reușit
                Toast.makeText(this, "Succesfully Logged In", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainPageActivity::class.java))
                finish()
            } else {
                // Date incorecte
                Toast.makeText(this, "You got one wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
