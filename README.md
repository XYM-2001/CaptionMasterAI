# CaptionMasterAI

## Problem Statement

Creating engaging and relevant captions for TikTok videos can be a challenging and time-consuming task for content creators. Captions play a crucial role in capturing viewers' attention and improving the discoverability of videos through hashtags and keywords. However, not all users have the creative writing skills or the time to craft perfect captions consistently. There is a need for a tool that can assist users in generating captivating and contextually appropriate captions quickly and efficiently.

## Objective

Develop an Android mobile app that helps TikTok users generate engaging captions for their videos using the Google Gemini LLM, thereby enhancing their creativity and productivity. This app aims to streamline the process of caption creation, making it easier for users to produce high-quality content and improve their content management on TikTok.

## Features and Functionality

### Page 1: Login
- **User Authentication**: Users log in using the TikTok API.
- **Fetch User Media**: Retrieve the user's media list (names, URLs, etc.) from the TikTok display API and store it in a local SQLite database.
- **Navigation**: After successful login and media fetch, navigate to the media gallery page.

### Page 2: Media Gallery
- **Display Media**: Show thumbnails for the user's videos and pictures.
- **Media Interaction**:
  - **Download Media**: On clicking a thumbnail, upload the media to local storage.
  - **Upload Media**: Option to upload a new video (for demo purposes).
- **Navigation**: On selecting media, navigate to the media editor page.

### Page 3: Media Editor
- **Media Display**: Show the selected video.
- **Caption Generation**:
  - **Input Fields**: Enter description, select moods for the hashtag and caption.
  - **Generate Caption**: Use Google Gemini LLM to generate a caption and a hashtag for TikTok posting.
  - **Optional**: Provide reasoning and statistics for the chosen hashtags.
- **Navigation**: 
  - **Post Media**: Upload the media with the generated caption, then navigate to the upload page.
  - **Back**: Return to the media gallery page.

### Page 4: Upload Media
- **Upload Functionality**: Upload the selected media and generated captions to TikTok using the TikTok content posting API.
- **Navigation**: 
  - **Back**: Return to the media editor page.

## Development Tools and Technologies

### Development Environment
- **Android Studio**: Main development IDE for building the Android app.

### Backend and APIs
- **TikTok API**: For user authentication, fetching media, and posting content.
- **Google Gemini LLM API**: For generating video captions.
- **SQLite**: Local database for storing user media details.

### Libraries and Frameworks
- **Firebase Authentication**: `libs.firebase.auth` for managing user authentication.
- **Generative AI Client**: `libs.generativeai` for integrating Google Gemini LLM.
- **Core KTX**: `libs.androidx.core.ktx` for core Android Kotlin extensions.
- **AppCompat**: `libs.androidx.appcompat` for backward compatibility.
- **Activity**: `libs.androidx.activity` for activity management.
- **ConstraintLayout**: `libs.androidx.constraintlayout` for flexible UI design.
- **Lifecycle Runtime KTX**: `libs.androidx.lifecycle.runtime.ktx` for lifecycle-aware components.
- **AndroidX JUnit**: `libs.androidx.junit` for testing.
- **Material Components**: `libs.material` for material design components.
- **Kotlinx Coroutines Android**: `libs.kotlinx.coroutines.android` for coroutine support.
- **Android Application Plugin**: `libs.plugins.android.application` for Android application configuration.
- **Kotlin Android Plugin**: `libs.plugins.jetbrains.kotlin.android` for Kotlin support.
- **Google Services Plugin**: `libs.plugins.google.gms.google.services` for Google services integration.
- **Secrets Gradle Plugin**: `libs.plugins.secrets.gradle.plugin` for managing sensitive data.

### Additional Tools
- **Postman**: For testing API endpoints.
- **Git/GitHub**: For version control and project collaboration.

## Project Outline

1. **Login and Media Fetch**
   - Integrate TikTok API for authentication.
   - Fetch user media and store in SQLite.

2. **Media Gallery Implementation**
   - Display media thumbnails.
   - Implement download and upload functionality.

3. **Media Editor Development**
   - Show video playback.
   - Implement caption generation using Google Gemini LLM.
   - Add reasoning and statistics for hashtags.

4. **Media Upload Feature**
   - Integrate TikTok content posting API for uploading media with captions.

5. **Testing and Refinement**
   - Test the app thoroughly for bugs and usability issues.
   - Refine the user interface for a seamless experience.

6. **Presentation Preparation**
   - Create a demo showcasing the app's features.
   - Prepare a presentation highlighting the project’s objectives, functionality, and technical details.

By addressing the problem of generating engaging captions, this project aims to provide TikTok users with an intuitive tool that leverages the power of generative AI, enhancing their video content creation process.
