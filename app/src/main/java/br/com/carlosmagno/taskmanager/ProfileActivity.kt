package br.com.carlosmagno.taskmanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.carlosmagno.taskmanager.utils.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CAMERA = 0
    private val PERMISSION_REQUEST_MEDIA = 1
    private var _image: Bitmap? = null;
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();
    private val uid = firebaseAuth.currentUser?.uid
    private val profileRef = FirebaseDatabase.getInstance().getReference("users/${uid}/data")

    companion object {
        private const val REQUEST_IMAGE_CAMERA = 1
        private const val REQUEST_IMAGE_MEDIA = 2
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val homeIcon = findViewById<ImageView>(R.id.home_icon)
        val logoutIcon = findViewById<ImageView>(R.id.logout_icon)
        val saveBtn = findViewById<Button>(R.id.save_button)
        val profileImage = findViewById<CircleImageView>(R.id.profile_image)
        val cameraAction = findViewById<ImageView>(R.id.fab_camera)
        val galleryAction = findViewById<ImageView>(R.id.fab_galery)

        homeIcon.setOnClickListener {
            Navigation.goToScreen(this, MainActivity::class.java)
        }
        logoutIcon.setOnClickListener {
            Navigation.goToScreen(this, LoginActivity::class.java)
        }
        saveBtn.setOnClickListener {
            saveProfile()
        }
        cameraAction.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAMERA)
        }
        galleryAction.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_IMAGE_MEDIA);
        }
        requestPermissions()
        loadProfile()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), PERMISSION_REQUEST_MEDIA)
        }
    }

    private fun loadProfile() {
        //o email já foi cadastrado e não pode ser alterado pelo usuário
        findViewById<EditText>(R.id.email_input).setText("${firebaseAuth.currentUser?.email}")
        val ctx = this@ProfileActivity

        // Carrega dados do Realtime Database
        profileRef?.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val username = snapshot.child("username").value as? String
                    val name = snapshot.child("name").value as? String
                    val phone = snapshot.child("phone").value as? String
                    val image = snapshot.child("image").value as? String

                    username?.let {
                        findViewById<EditText>(R.id.username_input).setText(it)
                    }
                    name?.let {
                        findViewById<EditText>(R.id.name_input).setText(it)
                    }
                    phone?.let {
                        findViewById<EditText>(R.id.phone_input).setText(it)
                    }
                    image.let {
                        val decodedString = android.util.Base64.decode(image, android.util.Base64.DEFAULT)
                        val decodedByte = android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                        findViewById<CircleImageView>(R.id.profile_image).setImageBitmap(decodedByte)
                    }
                } else {
                    Toast.makeText(ctx, R.string.pendding_profile, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(ctx, R.string.error_loading_task, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveProfile() {
        val ctx = this@ProfileActivity

        // Cria/atualiza o perfil com a URL da imagem
        val username = findViewById<EditText>(R.id.username_input).text.trim()
        val name = findViewById<EditText>(R.id.name_input).text.trim()
        val phone = findViewById<EditText>(R.id.phone_input).text.trim()
        val baos = ByteArrayOutputStream();
        this._image?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray();
        val base64String = android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT);

        val profile = hashMapOf<String, String>(
            "username" to username.toString(),
            "name" to name.toString(),
            "phone" to phone.toString(),
            "image" to base64String
        )
        profileRef.setValue(profile)
            .addOnSuccessListener {
                Toast.makeText(ctx, R.string.profile_updated, Toast.LENGTH_SHORT).show()
                android.os.Handler().postDelayed({
                }, 1500)
            }
            .addOnFailureListener { error ->
                Toast.makeText(
                    ctx,
                    "${getString(R.string.profile_update_error)}: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

        android.os.Handler().postDelayed({
            Navigation.goToScreen(this, MainActivity::class.java)
        }, 5000)

    }

    @Deprecated("This method as been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ){
        super.onActivityResult(requestCode, resultCode, data);
        val profileImage = findViewById<CircleImageView>(R.id.profile_image)

        if(resultCode == RESULT_OK) {
            var imageBitmap: Bitmap? = null;
            when(requestCode) {
                REQUEST_IMAGE_CAMERA -> {
                    imageBitmap = data?.extras?.get("data") as Bitmap;
                }
                REQUEST_IMAGE_MEDIA -> {
                    val selectedImage: Uri? = data?.data
                    if (selectedImage != null) {
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage);
                    }
                }
            }
            profileImage.setImageBitmap(imageBitmap)
            this._image = imageBitmap
        } else {
            Toast.makeText(this, R.string.error_to_capture_image, Toast.LENGTH_SHORT).show()
        }
    }

    //verifica se o usuario aceitou ou negou a permissão de utilização da câmera
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.permission_refused, Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == PERMISSION_REQUEST_MEDIA){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.permission_refused, Toast.LENGTH_SHORT).show()
            }
        }
    }
}