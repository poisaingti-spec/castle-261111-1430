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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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
            model = content.backdropUrl,
            contentDescription = content.title,
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
                Spacer(modifier = Modifier.height(300.dp))
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
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        RatingBadge(rating = content.rating)
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
                            text = formatDuration(content.duration),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // Genres
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        content.genres.forEach { genre ->
                            AssistChip(
                                onClick = { },
                                label = { Text(genre) }
                            )
                        }
                    }
                    
                    // Action buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = onPlayClick,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary
                            )
                        ) {
                            Icon(
                                androidx.compose.material.icons.filled.PlayArrow,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Watch Now")
                        }
                        
                        OutlinedButton(
                            onClick = { /* Add to list */ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                androidx.compose.material.icons.filled.Add,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("My List")
                        }
                    }
                    
                    // Description
                    Text(
                        text = content.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    // Cast
                    if (content.cast.isNotEmpty()) {
                        Text(
                            text = "Cast",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(content.cast) { castMember ->
                                CastMemberCard(castMember = castMember)
                            }
                        }
                    }
                    
                    // Recommended
                    Text(
                        text = "You May Also Like",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        // Back button
        IconButton(
            onClick = onBackPress,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                androidx.compose.material.icons.filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CastMemberCard(
    castMember: com.crafted.castle.data.model.CastMember
) {
    Column(
        modifier = Modifier.width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = castMember.profileUrl,
            contentDescription = castMember.name,
            modifier = Modifier
                .size(80.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                )
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = castMember.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1
        )
        
        Text(
            text = castMember.character,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}

private fun formatDuration(minutes: Int): String {
    return if (minutes >= 60) {
        val hours = minutes / 60
        val mins = minutes % 60
        "${hours}h ${mins}m"
    } else {
        "${minutes}m"
    }
}