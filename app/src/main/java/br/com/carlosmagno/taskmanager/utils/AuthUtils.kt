package br.com.carlosmagno.taskmanager.utils

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import br.com.carlosmagno.taskmanager.LoginActivity

class AuthUtils {
    companion object {
        private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

        fun logout(context: Context){
            try {
                firebaseAuth.signOut()
                Toast.makeText(context, "Hasta la vista, baby!", Toast.LENGTH_SHORT).show()
                Navigation.goToScreen(context, LoginActivity::class.java)
            } catch (error: Exception) {
                Toast.makeText(context, "Falha ao efetuar logout!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}