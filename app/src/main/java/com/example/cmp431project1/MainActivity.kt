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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

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
var work=25

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home" // The initial screen route
    ) {
        composable(route = "home") {

            Column(modifier= Modifier.background(Color.Cyan).padding(16.dp).fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                //Counters
                Row(modifier= Modifier.weight(1f).padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Column(modifier= Modifier.weight(1f),
                        verticalArrangement=Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Total Workouts\nCompleted:", textAlign = TextAlign.Center
                            , modifier= Modifier.weight(1f))
                        Text(work.toString(), modifier= Modifier.weight(1f))
                    }
                    Column(modifier= Modifier.weight(1f),
                        verticalArrangement=Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Total Cumulative\nWorkout Time:", textAlign = TextAlign.Center
                            , modifier= Modifier.weight(1f))
                        Text("TODO", modifier= Modifier.weight(1f))
                    }
                }

                //Picker
                Row(modifier= Modifier.weight(1f).padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceAround)
                {
                    Column(modifier= Modifier.weight(1f),
                        verticalArrangement=Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Strength", modifier= Modifier.weight(1f))
                        Text("Cardio", modifier= Modifier.weight(1f))
                    }
                    Column(modifier= Modifier.weight(1f),
                        verticalArrangement=Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Flexibility", modifier= Modifier.weight(1f))
                        Text("HIIT", modifier= Modifier.weight(1f))
                    }
                }

                //History log
                Text("space 3", textAlign = TextAlign.Center, modifier= Modifier.weight(1f))

                //Clear log
                Text("space 4", textAlign = TextAlign.Center, modifier= Modifier.weight(1f))
            }

        }


    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CMP431Project1Theme {

    }
}