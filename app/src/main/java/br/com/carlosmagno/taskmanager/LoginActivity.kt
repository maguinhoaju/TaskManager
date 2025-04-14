package br.com.carlosmagno.taskmanager

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.carlosmagno.taskmanager.fragments.ButtonFragment
import br.com.carlosmagno.taskmanager.fragments.EmailInputFragment
import br.com.carlosmagno.taskmanager.fragments.PasswordInputFragment
import br.com.carlosmagno.taskmanager.utils.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLoginFragment = supportFragmentManager.findFragmentById(R.id.btnSubmitLogin) as ButtonFragment
        val forgotPasswordLink = findViewById<TextView>(R.id.forgotPasswordLink)
        val registerLink = findViewById<TextView>(R.id.registerLink)
        val email = supportFragmentManager.findFragmentById(R.id.emailInput) as EmailInputFragment
        val password = supportFragmentManager.findFragmentById(R.id.passwordInput) as PasswordInputFragment

        val btnLogin = btnLoginFragment.btnSubmitLogin
        btnLogin.setOnClickListener {
            val emailText = email.emailEditText.text.toString()
            val passwordText = password.passwordEditText.text.toString().trim()

            val emailValido = email.validateEmail()
            if (!emailValido || passwordText.isNullOrEmpty()) {
                Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    login(emailText, passwordText)
                }
            }
        }

        forgotPasswordLink.setOnClickListener {
            Navigation.goToScreen(this, ForgotPasswordActivity::class.java)
        }

        registerLink.setOnClickListener {
            Navigation.goToScreen(this, RegisterActivity::class.java)
        }
    }

    private fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        Toast.makeText(this@LoginActivity, "Seja bem vindo ${user.email}!", Toast.LENGTH_SHORT).show()
                        Navigation.goToScreen(this@LoginActivity, MainActivity::class.java)
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Falha ao realizar login", Toast.LENGTH_SHORT).show()
                }
            }
    }

}