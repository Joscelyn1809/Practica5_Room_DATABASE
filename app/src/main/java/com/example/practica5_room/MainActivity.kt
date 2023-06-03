package com.example.practica5_room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.practica5_room.ui.CountryAppNavigation
import com.example.practica5_room.ui.home.HomeScreen
import com.example.practica5_room.ui.theme.Practica5_RoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practica5_RoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CountryDataBaseApp()
                }
            }
        }
    }

    @Composable
    fun CountryDataBaseApp() {
        CountryAppNavigation()
    }
}