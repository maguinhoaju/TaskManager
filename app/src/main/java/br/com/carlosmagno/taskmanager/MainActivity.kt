package br.com.carlosmagno.taskmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verifySession()
    }

    private fun verifySession() {
//        val activity = Intent(this, LoginActivity::class.java);
//        startActivity(activity)
//        finish();
    }
}