package br.com.carlosmagno.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.carlosmagno.taskmanager.fragments.PasswordDifficult
import br.com.carlosmagno.taskmanager.utils.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnSubmitRegister = findViewById<Button>(R.id.btnSubmitRegister)
        val loginBtn = findViewById<ImageView>(R.id.login)
        val confirmPassword = findViewById<EditText>(R.id.confirmPasswordInput)
        val password = supportFragmentManager.findFragmentById(R.id.passwordInput) as PasswordDifficult

        btnSubmitRegister.setOnClickListener {
            val email = findViewById<TextView>(R.id.emailInput).text.toString()
            val passwordText = password.passwordInput.text.toString()
            val confirmPasswordText = confirmPassword.text.toString()
            if (isValidPasswords(passwordText, confirmPasswordText)) {
                CoroutineScope(Dispatchers.IO).launch {
                    register(email, passwordText, confirmPasswordText)
                }
            }
        }

        loginBtn.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }

    private fun isValidPasswords(password: String, confirmPassword: String): Boolean {
        if (password != confirmPassword){
            Toast.makeText(
                baseContext,
                "As senhas não são iguais!",
                Toast.LENGTH_SHORT,
            ).show()
            return false
        } else if (password.isEmpty() && confirmPassword.isNotEmpty()){
            Toast.makeText(
                baseContext,
                "As senhas não podem ser vazias!",
                Toast.LENGTH_SHORT,
            ).show()
            return false
        } else {
            return true
        }
    }
    private fun register(email: String, password: String, confirmPassword: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Usuário registrado com sucesso!",
                        Toast.LENGTH_SHORT,
                    ).show()
                    Navigation.goToScreen(this, MainActivity::class.java)
                } else {
                    Toast.makeText(
                        baseContext,
                        "Falha ao registrar usuário. (${task.exception?.message})",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}