package com.crafted.castle.ui.filmdetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.crafted.castle.data.model.Content
import com.crafted.castle.ui.components.*
import com.crafted.castle.ui.theme.CastleTheme
import com.crafted.castle.ui.theme.Primary
import com.crafted.castle.ui.video.VideoPlayerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmDetailActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val content = intent.getParcelableExtra<Content>("content")
        
        setContent {
            CastleTheme {
                FilmDetailScreen(
                    content = content,
                    onBackPress = { finish() },
                    onPlayClick = {
                        val intent = Intent(this, VideoPlayerActivity::class.java).apply {
                            putExtra("content", content)
                            putExtra("videoUrl", content?.videoUrl)
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailScreen(
    content: Content?,
    onBackPress: () -> Unit,
    onPlayClick: () -> Unit
) {
    if (content == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Content not found")
        }
        return
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Backdrop image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(content.backdropUrl)
                .crossfade(true)
                .build(),
            contentDescription = content.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
        
        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top app bar area
            item {
                Spacer(modifier = Modifier.height(56.dp))
            }
            
            // Back button
            item {
                IconButton(
                    onClick = onBackPress,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        androidx.compose.material.icons.filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
            
            // Spacer for backdrop
            item {
                Spacer(modifier = Modifier.height(250.dp))
            }
            
            // Content info
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Title and metadata
                    Text(
                        text = content.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${content.releaseYear}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Text(
                            text = "${content.duration} min",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Text(
                            text = "★ ${content.rating}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    // Genres
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(content.genres) { genre ->
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Primary.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = genre,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Primary,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                    
                    // Description
                    Text(
                        text = content.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.4
                    )
                    
                    // Cast
                    if (content.cast.isNotEmpty()) {
                        Text(
                            text = "Cast",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(content.cast) { castMember ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.width(80.dp)
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(castMember.profileUrl)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = castMember.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(50))
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = castMember.name,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }
                    
                    // Play button
                    Button(
                        onClick = onPlayClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            androidx.compose.material.icons.filled.PlayArrow,
                            contentDescription = "Play",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Play Now",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    // Bottom spacer
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
