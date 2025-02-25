package br.com.carlosmagno.taskmanager

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import br.com.carlosmagno.taskmanager.utils.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabAddTask = findViewById<FloatingActionButton>(R.id.fab_add_task)
        val logoutBtn = findViewById<ImageView>(R.id.logout)
        val profileBtn = findViewById<ImageView>(R.id.profile)

        fabAddTask.setOnClickListener {
            Navigation.goToScreen(this, TaskActivity::class.java)
        }

        logoutBtn.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }

        profileBtn.setOnClickListener {
            Navigation.goToScreen(this, ProfileActivity::class.java)
        }

        verifySession()
    }

    private fun verifySession() {
//        val activity = Intent(this, LoginActivity::class.java);
//        startActivity(activity)
//        finish();
    }
}