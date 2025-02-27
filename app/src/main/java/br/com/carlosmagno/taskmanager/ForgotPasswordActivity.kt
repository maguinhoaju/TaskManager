package br.com.carlosmagno.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.carlosmagno.taskmanager.utils.Navigation

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnSubmitForgotPassword = findViewById<Button>(R.id.btnSubmitForgotPassword)
        val loginLink = findViewById<TextView>(R.id.loginLink)

        btnSubmitForgotPassword.setOnClickListener {
            Toast.makeText(this, "Verifique o seu e-mail!", Toast.LENGTH_LONG).show()
        }

        loginLink.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }
}