package com.example.cmp431project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cmp431project1.ui.theme.CMP431Project1Theme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CMP431Project1Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun TopMenu(name: String, modifier: Modifier = Modifier) {
    Column(modifier= modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Row(modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly)
        {
            Column(
                verticalArrangement=Arrangement.SpaceEvenly) {
                Text("Total Workouts\nCompleted:")
                Text("TODO")
            }
            Column(
                verticalArrangement=Arrangement.SpaceEvenly) {
                Text("Total Cumulative\nWorkout Time:")
                Text("TODO")
            }
        }
        Text("space 2")
        Text("space 3")
        Text("space 4")
    }
}
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home" // The initial screen route
    ) {
        composable(route = "home") {

            Scaffold( modifier = Modifier.fillMaxSize() ) {

                innerPadding -> TopMenu(
                    name = "Android",
                    modifier = Modifier.background(Color.Cyan)
                        .padding(innerPadding).fillMaxSize()
                )

            }

        }


    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CMP431Project1Theme {
        TopMenu("Android")
    }
}