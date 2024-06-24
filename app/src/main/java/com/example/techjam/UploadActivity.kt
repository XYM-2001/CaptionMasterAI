package com.example.techjam

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

class UploadActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->
        if (uri != null){
            val intent = Intent(this, FullscreenVideoActivity::class.java)
            intent.putExtra("videoUri", uri.toString())
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        auth = FirebaseAuth.getInstance()

        val logoutButton = findViewById<Button>(R.id.btn_logout)
        val uploadButton = findViewById<Button>(R.id.btn_upload)
        val userInfoTextView = findViewById<TextView>(R.id.user_info)
        val user = auth.currentUser

        if (user == null){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        else{
            val welcomeMessage = getString(R.string.welcome_message, user.email)
            userInfoTextView.text = welcomeMessage
        }

        logoutButton.setOnClickListener{
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        uploadButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED) {
                launcher.launch("video/*")
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_VIDEO), VIDEO_PICK_CODE)
            }
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == VIDEO_PICK_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            launcher.launch("video/*")
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val VIDEO_PICK_CODE = 1000
    }
}