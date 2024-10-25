package com.example.loppuhomma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loppuhomma.ui.theme.TestiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestiTheme {
            AppNavHost()
        }
    }
}
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "randomItem") {
        composable("randomItem") {
            val viewModel: RandomItemViewModel = viewModel() // Instantiate ViewModel
            RandomItemScreen(navController, viewModel) // Pass ViewModel to screen
        }
        composable("info") { InfoScreen(navController) }
    }
}
