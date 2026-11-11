package com.crafted.castle.ui.video

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.Player
import com.crafted.castle.data.model.Content
import com.crafted.castle.ui.components.CastleVideoPlayer
import com.crafted.castle.ui.theme.CastleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Keep screen on during playback
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        val content = intent.getParcelableExtra<Content>("content")
        val videoUrl = intent.getStringExtra("videoUrl") ?: content?.videoUrl
        
        setContent {
            CastleTheme {
                VideoPlayerScreen(
                    videoUrl = videoUrl,
                    content = content,
                    onBackPress = { finish() }
                )
            }
        }
    }
}

@Composable
fun VideoPlayerScreen(
    videoUrl: String?,
    content: Content?,
    onBackPress: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(true) }
    var currentPosition by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }
    var showControls by remember { mutableStateOf(true) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (videoUrl != null) {
            CastleVideoPlayer(
                videoUrl = videoUrl,
                onPlayerReady = { player ->
                    player.addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(state: Int) {
                            isPlaying = state == Player.STATE_READY
                        }
                        
                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                            this@VideoPlayerScreen.isPlaying = isPlaying
                        }
                    })
                    duration = player.duration
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        
        // Back button
        IconButton(
            onClick = onBackPress,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                androidx.compose.material.icons.filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        
        // Video title
        if (showControls && content != null) {
            Text(
                text = content.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            )
        }
        
        // Controls
        if (showControls) {
            VideoControls(
                isPlaying = isPlaying,
                currentPosition = currentPosition,
                duration = duration,
                onPlayPause = { /* Toggle play/pause */ },
                onSeek = { /* Seek to position */ },
                onFullscreen = { /* Toggle fullscreen */ },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}