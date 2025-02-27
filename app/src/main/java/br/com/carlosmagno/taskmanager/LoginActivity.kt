package br.com.carlosmagno.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.carlosmagno.taskmanager.utils.Navigation

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnSubmitLogin)
        val forgotPasswordLink = findViewById<TextView>(R.id.forgotPasswordLink)
        val registerLink = findViewById<TextView>(R.id.registerLink)

        btnLogin.setOnClickListener {
            Toast.makeText(this, "Bem vindo!", Toast.LENGTH_LONG).show()
            Navigation.goToScreen(this, MainActivity::class.java)
        }

        forgotPasswordLink.setOnClickListener {
            Navigation.goToScreen(this, ForgotPasswordActivity::class.java)
        }

        registerLink.setOnClickListener {
            Navigation.goToScreen(this, RegisterActivity::class.java)
        }
    }

}