package com.crafted.castle.ui.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crafted.castle.ui.components.CastleTopAppBar
import com.crafted.castle.ui.theme.CastleTheme
import com.crafted.castle.ui.theme.Primary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            CastleTheme {
                SettingsScreen(
                    onBackPress = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackPress: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var downloadQuality by remember { mutableStateOf("1080p") }
    var playbackQuality by remember { mutableStateOf("Auto") }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CastleTopAppBar(
            title = "Settings",
            showBackButton = true,
            onBackClick = onBackPress
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            // Account Section
            item {
                SettingsSectionTitle(title = "Account")
            }
            item {
                SettingsItem(
                    title = "Profile",
                    subtitle = "Manage your profile",
                    onClick = { }
                )
            }
            item {
                SettingsItem(
                    title = "Notifications",
                    subtitle = "Manage notification preferences"
                ) {
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Primary,
                            checkedTrackColor = Primary.copy(alpha = 0.5f)
                        )
                    )
                }
            }
            
            // Playback Section
            item {
                SettingsSectionTitle(title = "Playback")
            }
            item {
                SettingsItem(
                    title = "Default Video Quality",
                    subtitle = playbackQuality,
                    onClick = { /* Show quality selector */ }
                )
            }
            item {
                SettingsItem(
                    title = "Download Quality",
                    subtitle = downloadQuality,
                    onClick = { /* Show quality selector */ }
                )
            }
            
            // App Section
            item {
                SettingsSectionTitle(title = "App")
            }
            item {
                SettingsItem(
                    title = "Language",
                    subtitle = "English",
                    onClick = { }
                )
            }
            item {
                SettingsItem(
                    title = "About",
                    subtitle = "Version 1.0.0",
                    onClick = { }
                )
            }
            item {
                SettingsItem(
                    title = "Privacy Policy",
                    onClick = { }
                )
            }
            item {
                SettingsItem(
                    title = "Terms of Service",
                    onClick = { }
                )
            }
            
            // Logout Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                SettingsItem(
                    title = "Logout",
                    titleColor = MaterialTheme.colorScheme.error,
                    onClick = { }
                )
            }
            item {
                SettingsItem(
                    title = "Delete Account",
                    titleColor = MaterialTheme.colorScheme.error,
                    onClick = { }
                )
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun SettingsSectionTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String? = null,
    titleColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = titleColor
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}