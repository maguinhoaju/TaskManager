package br.com.carlosmagno.taskmanager.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

class Navigation {
    companion object {
        public fun goToScreen (context: Context, activity: Class<*>) {
            val intent = Intent(context, activity)
            context.startActivity(intent, null)
        }
        fun goToScreen(context: Context, intent: Intent){
            startActivity(context, intent, null);
        }
    }
}