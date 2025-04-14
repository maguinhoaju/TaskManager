package br.com.carlosmagno.taskmanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.carlosmagno.taskmanager.fragments.EmailInputFragment
import br.com.carlosmagno.taskmanager.fragments.TaskListFragment
import br.com.carlosmagno.taskmanager.utils.AuthUtils
import br.com.carlosmagno.taskmanager.utils.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class Task(val id: Int, val title: String, val subtitle: String)

class MainActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();
    private val firebaseUser: FirebaseUser? = firebaseAuth.currentUser

    val listItems = ArrayList<String>();
    val db_ref = FirebaseDatabase.getInstance().getReference("users/${firebaseUser?.uid}/tasks")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //supportFragmentManager.beginTransaction().replace(R.id.fragment_weather, WeatherFragment()).commit()

        val fabAddTask = findViewById<FloatingActionButton>(R.id.fab_add_task)
        val logoutBtn = findViewById<ImageView>(R.id.logout)
        val profileBtn = findViewById<ImageView>(R.id.profile)
        val listView = supportFragmentManager.findFragmentById(R.id.tasks_list_view) as TaskListFragment

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        val taskListView = listView.taskListView
        taskListView.adapter = adapter

        fabAddTask.setOnClickListener {
            Navigation.goToScreen(this, TaskActivity::class.java)
        }

        logoutBtn.setOnClickListener {
            AuthUtils.logout(this);
        }

        profileBtn.setOnClickListener {
            Navigation.goToScreen(this, ProfileActivity::class.java)
        }

        verifySession(firebaseUser)
        loadData(adapter, listView.taskListView)
        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }
    }

    private fun verifySession(firebaseUser: Any?) {
        if(firebaseUser == null) {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
    }

    private fun loadData(adapter: ArrayAdapter<String>, listView: ListView) {
        db_ref.addValueEventListener(object: ValueEventListener {
            val ctx = this@MainActivity

            override fun onDataChange(snapshot: DataSnapshot) {
                listItems.clear()

                for(element in snapshot.children) {
                    listItems.add(element.child("title").value.toString());
                }
                adapter.notifyDataSetChanged()

                listView.setOnItemLongClickListener { parent, view, position, id ->
                    val taskId = snapshot.children.toList()[position].key
                    if (taskId != null) {
                        AlertDialog.Builder(ctx)
                            .setTitle(R.string.title_delete_task)
                            .setMessage(R.string.message_delete_task)
                            .setPositiveButton(R.string.confirm) { dialog, which ->
                                db_ref.child(taskId).removeValue();
                                dialog.dismiss()
                                Toast.makeText(ctx, R.string.task_deleted, Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton(R.string.cancel) { dialog, which ->
                                dialog.dismiss()
                            }
                            .show()
                    };
                    true
                }

                listView.setOnItemClickListener { _, _, position, _ ->
                    val taskId = snapshot.children.toList()[position].key

                    val activity = Intent(ctx, TaskActivity::class.java)
                    activity.putExtra("taskId", taskId)

                    Navigation.goToScreen(ctx, activity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(ctx, R.string.error_loading_tasks, Toast.LENGTH_SHORT).show()
            }
        });
    }

}