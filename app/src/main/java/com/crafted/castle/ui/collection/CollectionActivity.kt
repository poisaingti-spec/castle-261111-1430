package com.crafted.castle.ui.collection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.crafted.castle.ui.components.*
import com.crafted.castle.ui.theme.CastleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            CastleTheme {
                CollectionScreen(
                    onBackPress = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(
    onBackPress: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CastleTopAppBar(
            title = "My Collections",
            showBackButton = true,
            onBackClick = onBackPress
        )
        
        EmptyState(
            message = "No collections created",
            icon = "📁"
        )
    }
}