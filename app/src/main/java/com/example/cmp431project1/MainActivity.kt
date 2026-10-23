package com.example.cmp431project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavBackStackEntry

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
class exercise(val name:String, val descrpt: String, val difficulty: String, val c: Color)
class workout(val name:String, val mins:Int)
class workoutHold{
    var sum=0
    var L = mutableListOf<workout>()
    fun add(w: workout){
        sum+=w.mins
        L.add(w)
    }
    fun reset(){
        sum=0
        L=mutableListOf<workout>()
    }
}
var curExercise = exercise("dummy", "this shouldn't be seen",
    "dummy", Color.Black)
var curCat = emptyList<exercise>()
var catName="dummy"
val str=listOf<exercise>(
    exercise("1", "one",
        "Beginner", Color.Green),
    exercise("2", "two",
        "Intermediate", Color.Yellow),
    exercise("3", "three",
        "Advanced", Color.Red)
)
var wHold= workoutHold()


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home" // The initial screen route
    ) {
        composable(route = "home") {

            Column(modifier= Modifier.background(Color.Cyan).padding(32.dp).fillMaxSize(),
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
                        Text(wHold.sum.toString(), modifier= Modifier.weight(1f))
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
                        FilledIconButton(modifier= Modifier.weight(1f).padding(8.dp).fillMaxWidth(),
                            onClick={
                                curCat=str
                                catName="Strength"
                                navController.navigate("exercise_list")
                            })
                        {
                            Text("Strength")
                        }
                        FilledIconButton(modifier= Modifier.weight(1f).padding(8.dp).fillMaxWidth(),
                            onClick={
                                navController.navigate("exercise_list")
                            })
                        {
                            Text("Cardio", modifier= Modifier.weight(1f))
                        }
                    }
                    Column(modifier= Modifier.weight(1f),
                        verticalArrangement=Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        FilledIconButton(modifier= Modifier.weight(1f).padding(8.dp).fillMaxWidth(),
                            onClick={
                                navController.navigate("exercise_list")
                            })
                        {
                            Text("Flexibility", modifier= Modifier.weight(1f))
                        }
                        FilledIconButton(modifier= Modifier.weight(1f).padding(8.dp).fillMaxWidth(),
                            onClick={
                                navController.navigate("exercise_list")
                            })
                        {
                            Text("HIIT", modifier= Modifier.weight(1f))
                        }
                    }
                }

                //History log
                //Text("space 3", textAlign = TextAlign.Center, modifier= Modifier.weight(1f))
                LazyColumn(modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    items(wHold.L){
                        Row(modifier= Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround){
                            Text(it.name)
                            Text(it.mins.toString())
                        }
                    }
                }

                //Clear log
                FilledIconButton(modifier= Modifier.weight(1f).padding(8.dp).fillMaxWidth(),
                    onClick={
                        wHold.reset()
                    })
                {
                    Text("Clear Workout History", modifier= Modifier.weight(1f))
                }
            }

        }


        // Page 2
        composable(route = "exercise_list") {
            LazyColumn(modifier= Modifier.background(Color.Cyan).padding(32.dp).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                item{
                    Text(catName, textAlign = TextAlign.Center)
                }
                item{
                    FilledIconButton(modifier= Modifier.padding(8.dp).fillMaxWidth(),
                        onClick={
                            navController.popBackStack()
                        })
                    {
                        Text("BACK")
                    }
                }
                items(items=curCat){
                    FilledIconButton(modifier= Modifier.padding(8.dp).fillMaxWidth(),
                        onClick={
                            curExercise=it
                            navController.navigate("exercise_specific")
                        })
                    {
                        Text(it.name)
                    }
                    Text(it.difficulty)
                    Text(it.descrpt)
                }
            }
        }


        // Page 3
        composable(route = "exercise_specific") {
            var text by rememberSaveable() { mutableStateOf("") }
            Column(modifier= Modifier.background(Color.Cyan).padding(32.dp).fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                FilledIconButton(modifier= Modifier.padding(8.dp).fillMaxWidth(),
                    onClick={
                        navController.popBackStack()
                    })
                {
                    Text("BACK")
                }
                Text(curExercise.name)
                Text(curExercise.difficulty)
                Text(curExercise.descrpt)
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter guess") }
                )
                FilledIconButton(modifier= Modifier.padding(8.dp).fillMaxWidth(),
                    onClick={
                        wHold.add(workout(curExercise.name, text.toInt()))
                        text=""
                    })
                {
                    Text("Do Exercise for selected time")
                }

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