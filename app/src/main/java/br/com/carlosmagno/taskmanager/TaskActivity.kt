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

class TaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val saveBtn = findViewById<Button>(R.id.save_button)
        val logoutBtn = findViewById<ImageView>(R.id.logout)
        val profileBtn = findViewById<ImageView>(R.id.profile)

        saveBtn.setOnClickListener {
            Toast.makeText(this, "Tarefa criada!", Toast.LENGTH_LONG).show()
            Navigation.goToScreen(this, MainActivity::class.java)
        }

        logoutBtn.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }

        profileBtn.setOnClickListener {
            Navigation.goToScreen(this, ProfileActivity::class.java)
        }

    }
}