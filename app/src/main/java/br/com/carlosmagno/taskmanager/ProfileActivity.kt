package br.com.carlosmagno.taskmanager

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.carlosmagno.taskmanager.utils.Navigation

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val homeIcon = findViewById<ImageView>(R.id.home_icon)
        val logoutIcon = findViewById<ImageView>(R.id.logout_icon)

        homeIcon.setOnClickListener {
            Navigation.goToScreen(this, MainActivity::class.java)
        }
        logoutIcon.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }

    }
}