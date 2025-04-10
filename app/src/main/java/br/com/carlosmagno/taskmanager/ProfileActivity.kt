package br.com.carlosmagno.taskmanager

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.carlosmagno.taskmanager.utils.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CAMERA = 0
    private val PERMISSION_REQUEST_MEDIA = 1
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val db_ref = FirebaseDatabase.getInstance().getReference("users/${uid}/data")
    var _image: Bitmap? = null;

    companion object {
        private const val REQUEST_IMAGE_CAMERA = 1
        private const val REQUEST_IMAGE_MEDIA = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val homeIcon = findViewById<ImageView>(R.id.home_icon)
        val logoutIcon = findViewById<ImageView>(R.id.logout_icon)
        val saveBtn = findViewById<Button>(R.id.save_button)

        homeIcon.setOnClickListener {
            Navigation.goToScreen(this, MainActivity::class.java)
        }
        logoutIcon.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }

        saveBtn.setOnClickListener {
            Toast.makeText(this, "Perfil salvo!", Toast.LENGTH_LONG).show()
            Navigation.goToScreen(this, MainActivity::class.java)
        }
    }
}