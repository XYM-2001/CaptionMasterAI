package com.example.techjam

import android.net.Uri
import android.os.Bundle
import android.media.MediaPlayer
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class FullscreenVideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_video)

        val videoView = findViewById<VideoView>(R.id.video_view)
        val backButton = findViewById<ImageButton>(R.id.btn_back)

        // Get the video URI from the intent
        val videoUri = intent.getStringExtra("videoUri")?.let { Uri.parse(it) }

        // Set up the video view
        if (videoUri != null) {
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(videoUri)
            videoView.requestFocus()
            videoView.start()
        }
        else{
            Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show()
        }

        // Set up the back button
        backButton.setOnClickListener {
            finish()
        }
    }
}
