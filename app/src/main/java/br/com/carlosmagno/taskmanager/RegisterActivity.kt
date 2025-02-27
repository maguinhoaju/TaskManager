package br.com.carlosmagno.taskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.carlosmagno.taskmanager.utils.Navigation

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnSubmitRegister = findViewById<Button>(R.id.btnSubmitRegister)
        val loginBtn = findViewById<ImageView>(R.id.login)

        btnSubmitRegister.setOnClickListener {
            //Só para retornar por enquanto
            Toast.makeText(this, "Registro efetuado!", Toast.LENGTH_LONG).show()
            Navigation.goToScreen(this, LoginActivity::class.java)
        }

        loginBtn.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }
}