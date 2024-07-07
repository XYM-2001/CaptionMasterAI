package com.example.techjam

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FullscreenVideoActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var backButton: ImageButton
    private lateinit var captionsTextView: TextView
    private lateinit var descriptionInput: EditText
    private lateinit var generateCaptionsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_video)

        videoView = findViewById(R.id.video_view)
        backButton = findViewById(R.id.btn_back)
        captionsTextView = findViewById(R.id.captions_text_view)
        descriptionInput = findViewById(R.id.description_input)
        generateCaptionsButton = findViewById(R.id.generate_captions_button)
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.apiKey
        )

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
        } else {
            Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show()
        }

        // Set up the back button
        backButton.setOnClickListener {
            finish()
        }

        // Set up the generate captions button
        generateCaptionsButton.setOnClickListener {
            val description = descriptionInput.text.toString()
            if (description.isNotBlank() && videoUri != null) {
                lifecycleScope.launch {
                    val keyFrames = extractKeyFrames(videoUri)
                    if (keyFrames.isNotEmpty()) {
                        val prompt = "Generate a caption for the following video description: $description"

                        val caption = generateContentWithFrames(generativeModel, prompt, keyFrames)

                        // Display all captions or the first one, as per your requirement
                        if (caption.isNotEmpty()) {
                            displayCaptions(caption)
                        } else {
                            Toast.makeText(this@FullscreenVideoActivity, "Failed to generate captions", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@FullscreenVideoActivity, "No keyframes extracted", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@FullscreenVideoActivity, "Please enter a description of the video", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private suspend fun generateContentWithFrames(model: GenerativeModel, prompt: String, bitmap: List<Bitmap>): String {
        return withContext(Dispatchers.IO) {
            val response = model.generateContent(
                com.google.ai.client.generativeai.type.content {
                    image(bitmap[0])
                    image(bitmap[1])
                    image(bitmap[2])
                    image(bitmap[3])
                    image(bitmap[4])
                    image(bitmap[5])
                    image(bitmap[6])
                    image(bitmap[7])
                    image(bitmap[8])
                    image(bitmap[9])
                    text(prompt)
                }
            )
            response.text.toString()
        }
    }

    private fun displayCaptions(captions: String) {
        captionsTextView.text = captions
    }

    private suspend fun extractKeyFrames(videoUri: Uri): List<Bitmap> = withContext(Dispatchers.IO) {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(this@FullscreenVideoActivity, videoUri)
        val keyFrames = mutableListOf<Bitmap>()
        val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0L
        val interval = duration / 10 // Divide duration into 10 intervals

        for (i in 0 until 10) {
            val timeUs = (i * interval * 1000)
            val bitmap = retriever.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            if (bitmap != null) {
                keyFrames.add(bitmap)
            }
        }

        retriever.release()
        keyFrames
    }

}
