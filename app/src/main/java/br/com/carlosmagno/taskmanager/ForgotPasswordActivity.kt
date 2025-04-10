package br.com.carlosmagno.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.carlosmagno.taskmanager.utils.Navigation
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnSubmitForgotPassword = findViewById<Button>(R.id.btnSubmitForgotPassword)
        val loginBtn = findViewById<ImageView>(R.id.login)

        btnSubmitForgotPassword.setOnClickListener {
            val emailText = findViewById<TextView>(R.id.emailInput).text.toString();
            recoveryPassword(emailText)
        }

        loginBtn.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }

    fun recoveryPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Link para recuperação enviado com sucesso.",
                        Toast.LENGTH_SHORT).show()
                    Navigation.goToScreen(this@ForgotPasswordActivity, LoginActivity::class.java)
                } else {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Falha ao enviar link de recuperação!",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}