package com.crafted.castle.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.crafted.castle.ui.components.BottomNavItem
import com.crafted.castle.ui.components.CastleBottomNavigationBar
import com.crafted.castle.ui.home.HomeScreen
import com.crafted.castle.ui.movies.MoviesScreen
import com.crafted.castle.ui.livetv.LiveTVScreen
import com.crafted.castle.ui.sports.SportsScreen
import com.crafted.castle.ui.search.SearchScreen
import com.crafted.castle.ui.theme.CastleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            CastleTheme {
                MainNavigation()
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomNavItem.Home.route
    
    val showBottomBar = currentRoute in listOf(
        BottomNavItem.Home.route,
        BottomNavItem.Movies.route,
        BottomNavItem.Live.route,
        BottomNavItem.Sports.route,
        BottomNavItem.Search.route
    )
    
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                CastleBottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    onContentClick = { content ->
                        // Navigate to detail
                    },
                    onSearchClick = {
                        navController.navigate(BottomNavItem.Search.route)
                    }
                )
            }
            
            composable(BottomNavItem.Movies.route) {
                MoviesScreen(
                    onContentClick = { content ->
                        // Navigate to detail
                    }
                )
            }
            
            composable(BottomNavItem.Live.route) {
                LiveTVScreen(
                    onContentClick = { content ->
                        // Navigate to player
                    }
                )
            }
            
            composable(BottomNavItem.Sports.route) {
                SportsScreen(
                    onContentClick = { content ->
                        // Navigate to player
                    }
                )
            }
            
            composable(BottomNavItem.Search.route) {
                SearchScreen(
                    onContentClick = { content ->
                        // Navigate to detail
                    }
                )
            }
        }
    }
}