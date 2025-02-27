package br.com.carlosmagno.taskmanager

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.carlosmagno.taskmanager.utils.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton

data class Task(val id: Int, val title: String, val subtitle: String)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabAddTask = findViewById<FloatingActionButton>(R.id.fab_add_task)
        val logoutBtn = findViewById<ImageView>(R.id.logout)
        val profileBtn = findViewById<ImageView>(R.id.profile)
        val listView = findViewById<ListView>(R.id.tasks_list_view)
        val fakeData = listOf(
            Task(1, "Tarefa 1", "Descrição 1"),
            Task(2, "Tarefa 2", "Descrição 2"),
            Task(3, "Tarefa 3", "Descrição 3"),
            Task(4, "Tarefa 4", "Descrição 4"),
            Task(5, "Tarefa 5", "Descrição 5"),
            Task(6, "Tarefa 6", "Descrição 6"),
            Task(7, "Tarefa 7", "Descrição 7"),
            Task(8, "Tarefa 8", "Descrição 8"),
            Task(9, "Tarefa 9", "Descrição 9"),
            Task(10, "Tarefa 10", "Descrição 10"),
            Task(11, "Tarefa 11", "Descrição 11"),
            Task(12, "Tarefa 12", "Descrição 12"),
            Task(13, "Tarefa 13", "Descrição 13"),
            Task(14, "Tarefa 14", "Descrição 14"),
            Task(15, "Tarefa 15", "Descrição 15"),
            Task(16, "Tarefa 16", "Descrição 16"),
            Task(17, "Tarefa 17", "Descrição 17"),
            Task(18, "Tarefa 18", "Descrição 18"),
            Task(19, "Tarefa 19", "Descrição 19"),
            Task(20, "Tarefa 20", "Descrição 20"),
        )
        val extractTitles = fakeData.map { it.title }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, extractTitles)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val task = fakeData[position]
            Toast.makeText(this, "Tarefa ${task.id} selecionada", Toast.LENGTH_LONG).show()
            Navigation.goToScreen(this, TaskActivity::class.java)
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            val task = fakeData[position]
            Toast.makeText(this, "Tarefa ${task.id} deletada!", Toast.LENGTH_SHORT).show()
            true
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            val task = fakeData[position]
            Toast.makeText(this, "Tarefa ${task.id} long deleted", Toast.LENGTH_SHORT).show()
            true
        }
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