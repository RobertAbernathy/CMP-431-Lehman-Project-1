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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
class Exercise(val name:String, val descrpt: String, val difficulty: String, val c: Color)
class Workout(val name:String, val mins:Int)




data class WorkUIState(
    var sum:Int=0,
    var wList: List<Workout> = emptyList<Workout>(),
    val curExercise: Exercise = Exercise("dummy", "this shouldn't be seen",
        "dummy", Color.Black),
    val curCat: List<Exercise> = emptyList<Exercise>(),
    val catName: String ="dummy",
    val str: List<Exercise> =listOf<Exercise>(
        Exercise("1", "one",
            "Beginner", Color.Green),
        Exercise("2", "two",
            "Intermediate", Color.Yellow),
        Exercise("3", "three",
            "Advanced", Color.Red)
    ),
    val cardio: List<Exercise> =listOf<Exercise>(
        Exercise("1", "one",
            "Beginner", Color.Green),
        Exercise("2", "two",
            "Intermediate", Color.Yellow),
        Exercise("3", "three",
            "Advanced", Color.Red)
    ),
    val flex: List<Exercise> =listOf<Exercise>(
        Exercise("1", "one",
            "Beginner", Color.Green),
        Exercise("2", "two",
            "Intermediate", Color.Yellow),
        Exercise("3", "three",
            "Advanced", Color.Red)
    ),
    val hiit: List<Exercise> =listOf<Exercise>(
        Exercise("1", "one",
            "Beginner", Color.Green),
        Exercise("2", "two",
            "Intermediate", Color.Yellow),
        Exercise("3", "three",
            "Advanced", Color.Red)
    )
)
class MainViewModel: ViewModel(){
    private val _uiState= MutableStateFlow(WorkUIState())
    val uiState: StateFlow<WorkUIState> = _uiState.asStateFlow()
    fun add(w: Workout){
        _uiState.update{
            currentState -> currentState.copy(
                wList=currentState.wList+w,
                sum=currentState.sum+w.mins
            )
        }
    }
    fun reset(){
        _uiState.update{
            currentState -> currentState.copy(
                wList=emptyList<Workout>(),
                sum=0
            )
        }
    }

    fun setCat(l: List<Exercise>, s:String){
        _uiState.update{
            currentState -> currentState.copy(
                curCat = l,
                catName = s
            )
        }
    }

    fun setEx(e: Exercise){
        _uiState.update{
            currentState -> currentState.copy(
                curExercise = e
            )
        }
    }
}

@Composable
fun AppNavigation(viewModel: MainViewModel=viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
                        Text(uiState.wList.size.toString(), modifier= Modifier.weight(1f))
                    }
                    Column(modifier= Modifier.weight(1f),
                        verticalArrangement=Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Total Cumulative\nWorkout Time:", textAlign = TextAlign.Center
                            , modifier= Modifier.weight(1f))
                        Text(uiState.sum.toString(), modifier= Modifier.weight(1f))
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
                                viewModel.setCat(uiState.str,"Strength")
                                navController.navigate("exercise_list")
                            })
                        {
                            Text("Strength")
                        }
                        FilledIconButton(modifier= Modifier.weight(1f).padding(8.dp).fillMaxWidth(),
                            onClick={
                                viewModel.setCat(uiState.cardio,"Cardio")
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
                                viewModel.setCat(uiState.flex,"Flexibility")
                                navController.navigate("exercise_list")
                            })
                        {
                            Text("Flexibility", modifier= Modifier.weight(1f))
                        }
                        FilledIconButton(modifier= Modifier.weight(1f).padding(8.dp).fillMaxWidth(),
                            onClick={
                                viewModel.setCat(uiState.hiit,"HIIT")
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
                    items(uiState.wList){
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
                        viewModel.reset()
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
                    Text(uiState.catName, textAlign = TextAlign.Center)
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
                items(items=uiState.curCat){
                    FilledIconButton(modifier= Modifier.padding(8.dp).fillMaxWidth(),
                        onClick={
                            viewModel.setEx(it)
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
            var errorText by rememberSaveable() { mutableStateOf("") }
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
                Text(uiState.curExercise.name)
                Text(uiState.curExercise.difficulty)
                Text(uiState.curExercise.descrpt)
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter guess") }
                )
                FilledIconButton(modifier= Modifier.padding(8.dp).fillMaxWidth(),
                    onClick={
                        if(text.toDoubleOrNull()!=null){
                            if(text.toDouble()<=0){
                                errorText="Number cannot be 0 or less"
                            }
                            else if(text.toIntOrNull()==null){
                                errorText="Number must be whole number\nwith No White Space"
                            }
                            else {
                                viewModel.add(Workout(uiState.curExercise.name,
                                    text.toInt()))
                                errorText=""
                                text = ""
                            }
                        }
                        else if(text==""){
                            errorText="Please Input Number"
                        }
                        else{
                            errorText="Please Valid Number\nwith No White Space"
                        }
                    }
                )
                {
                    Text("Log Completed Workout")
                }
                Text(errorText, color=Color.Red,  textAlign = TextAlign.Center)
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