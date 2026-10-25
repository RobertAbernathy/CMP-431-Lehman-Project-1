package com.example.cmp431project1

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar

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
class Exercise(val name:String, val preview: String,
               val steps:String, val recDur:Int,
               val difficulty: String, val c: Color, val id:Int)
class Workout(val name:String, val mins:Int, val id:Int)

data class WorkUIState(
    var sum:Int=0,
    var wList: List<Workout> = emptyList(),
    val curExercise: Exercise = Exercise("dummy", "this shouldn't be seen",
        "dummy 1", 5,"dummy", Color.Black, 0),
    val curCat: List<Exercise> = emptyList(),
    val catName: String ="dummy",
    val inText:String="",
    val errorText:String="Please Input Number",
    val buttonOn:Boolean=false,
    val errorColor:Color=Color.Black,
    val showAlert: Boolean=false,
    val lastTime:Int=0,

    val str: List<Exercise> =listOf(
        Exercise("Push-up", "Classic push-up",
            "1. Get on all fours face-down with your legs straight fully stretched out\n" +
                    "2. Put your hands by your shoulders and have them flat on the ground\n" +
                    "3. Lift yourself until your arms are outstretched", 2,
            "Beginner", Color.Green, 0),

        Exercise("Sit-up", "Lift your body upward",
            "1. Lie on your back with your hands at your chest and your knees slightly bent\n" +
                    "2. Lift your torso up using your abs without moving your legs\n" +
                    "3. Go as far forward as you can without moving your legs", 2,
            "Beginner", Color.Green, 1),

        Exercise("Curtsy Lunge", "Bend your Leg back around your other leg",
            "1. Start from a standing position\n" +
                    "2. Step back with your left foot backward and crossing your right leg\n" +
                    "3. Bend both knees until your left knee touches the ground\n" +
                    "4. Return to the starting position by pushing off with your left foot\n" +
                    "5. Repeat same foot until you are ready to switch to the other", 2,
            "Intermediate", Color.Yellow, 2)
    ),

    val cardio: List<Exercise> =listOf(
        Exercise("High Knees", "Lift your knees up",
            "1. Start in a standing position and hold hands slightly out at your waist\n" +
                    "2. Lift one knee to your stationary hand and touch it\n" +
                    "3. Bring it back down and do the other knee and repeat", 2,
            "Beginner", Color.Green, 0),

        Exercise("Bear Crawl", "Knees slightly above the ground",
            "1. Get on all fours with you arms stretched down and knees below you\n" +
                    "2. Lift your knees slightly above the ground, but still under you\n" +
                    "3. Move left foot/right hand forward\n" +
                    "4. Move right foot/left hand forward\n" +
                    "5. Keep your back straight as you can", 1,
            "Intermediate", Color.Yellow, 1),

        Exercise("Burpee", "Go up and down",
            "1. Start in standing position with hands at side\n" +
                    "2. Jump upward\n3. When you land squat down with your hands on the ground\n" +
                    "4. Move your legs back as far as you can quickly\n" +
                    "5. Move them back under yourself\n" +
                    "6. Go back to standing and repeat", 1,
            "Advanced", Color.Red, 2)
    ),

    val flex: List<Exercise> =listOf(
        Exercise("Quadriceps Stretch", "Pull your foot",
            "1. Stand straight up\n2. Lift and pull your foot back behind your leg\n" +
                    "3. Pull gently and keep your back straight and knees together\n" +
                    "4. Repeat with your other leg", 2,
            "Beginner", Color.Green, 0),

        Exercise("Lunge with Twist", "Bring left foot and right hand forward",
            "1. Start from standing position\n" +
                    "2. Bring your left foot and keep you other right knee straight\n" +
                    "3. Put your right hand on the ground next to the forward foot\n" +
                    "4. Stretch your left hand upward while you keep" +
                    " your right hand on the ground\n" +
                    "5. Hold for a period of time\n" +
                    "6. Repeat with your other side", 2,
            "Intermediate", Color.Yellow, 1),

        Exercise("Frog Stretch", "Slide your knees back",
            "1. Get on all fours with your knees on the ground\n" +
                    "2. Slide your knees outward until they are further" +
                    " apart than your shoulders\n" +
                    "3. Turn your feet so that you are resting on the inner sides of them\n" +
                    "4. Move your hip backwards between your feet\n" +
                    "5. Move your hands beneath your shoulders\n" +
                    "6. Hold this pose for a period of time", 1,
            "Intermediate", Color.Yellow, 2)
    ),

    val hiit: List<Exercise> =listOf(
        Exercise("Jumping Jacks", "A jumping based exercise",
            "1. Start with feet together standing with hands at sides\n" +
                    "2. Jump and moves feet apart sideways while clapping hands above your head\n" +
                    "3. Jump again but now move feet together and hands back to waist\n" +
                    "4. Repeat steps 2 and 3", 1,
            "Beginner", Color.Green, 0),

        Exercise("Toe Touch Crunch", "Lie on your back and stretch",
            "1. Lie on you back\n2. Lift your legs up\n" +
                    "3. Reach your arms towards your toes as best you can", 5,
            "Intermediate", Color.Yellow, 1),

        Exercise("Inchworm", "Get on all fours",
            "1. Get on all fours\n" +
                    "2. Move your hands as if walking as far forward as you can go\n" +
                    "3. Move your feet as when walking, keeping you hands on the ground," +
                    "until you reach as close as you can to your hands", 1,
            "Intermediate", Color.Yellow, 2)
    )
)
class MainViewModel: ViewModel(){
    private val _uiState= MutableStateFlow(WorkUIState())
    val uiState: StateFlow<WorkUIState> = _uiState.asStateFlow()
    fun add(w: Workout){
        _uiState.update{
            currentState -> currentState.copy(
                wList=currentState.wList+w,
                sum=currentState.sum+w.mins,
                inText = "",
                buttonOn = false,
                errorText = "Accepted",
                errorColor=Color.Green,
                showAlert = true,
                lastTime = w.mins
            )
        }
    }
    fun reset(){
        _uiState.update{
            currentState -> currentState.copy(
                wList=emptyList(),
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

    fun alertOff(){
        _uiState.update{
            currentState -> currentState.copy(
                showAlert = false
            )
        }
    }
    fun setText(inS:String){
        if(inS.toDoubleOrNull()!=null){
            if(inS.toDouble()<=0){
                _uiState.update{
                    currentState -> currentState.copy(
                        inText = inS,
                        errorText = "Number cannot be 0 or less",
                        buttonOn = false,
                        errorColor = Color.Red
                    )
                }
            }
            else if(inS.toIntOrNull()==null){
                _uiState.update{
                    currentState -> currentState.copy(
                        inText = inS,
                        errorText = "Number must be whole number\nwith No White Space",
                        buttonOn = false,
                        errorColor = Color.Red
                    )
                }
            }
            else {
                _uiState.update{
                    currentState -> currentState.copy(
                        inText = inS,
                        errorText = "",
                        buttonOn = true,
                        errorColor = Color.Green
                    )
                }
            }
        }
        else if(inS==""){
            _uiState.update{
                currentState -> currentState.copy(
                    inText = inS,
                    errorText = "Please Input Number",
                    buttonOn = false,
                    errorColor = Color.Black
                )
            }
        }
        else{
            _uiState.update{
                currentState -> currentState.copy(
                    inText = inS,
                    errorText = "Please Valid Number\nwith No White Space",
                    buttonOn = false,
                    errorColor = Color.Red
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(viewModel: MainViewModel=viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home" // The initial screen route
    ) {
        composable(route = "home") {
            val listState = rememberLazyListState()
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("Workout Tracker",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }
            ) {
                innerPadding -> Column(
                    modifier = Modifier
                        .background(Color.Cyan)
                        .padding(innerPadding)
                        .padding(32.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    //Counters
                    Row(
                        modifier = Modifier.height(200.dp)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Card(
                            modifier = Modifier
                                //.weight(1f)
                                .padding(4.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Total Workouts\nCompleted:", textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(20.dp)
                                )
                                Text(
                                    uiState.wList.size.toString(),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(16.dp)
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                //.weight(1f)
                                .padding(4.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Total Cumulative\nWorkout Time:", textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(16.dp)
                                )
                                Text(
                                    uiState.sum.toString(),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(16.dp)
                                )
                            }
                        }
                    }

                    //Picker
                    Row(
                        modifier = Modifier.height(200.dp)
                            //.weight(1f)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    )
                    {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FilledIconButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    viewModel.setCat(uiState.str, "Strength")
                                    navController.navigate("exercise_list"){
                                        launchSingleTop=true
                                    }
                                })
                            {
                                Text("Strength")
                            }
                            FilledIconButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    viewModel.setCat(uiState.cardio, "Cardio")
                                    navController.navigate("exercise_list"){
                                        launchSingleTop=true
                                    }
                                })
                            {
                                Text("Cardio", modifier = Modifier.weight(1f))
                            }
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FilledIconButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    viewModel.setCat(uiState.flex, "Flexibility")
                                    navController.navigate("exercise_list"){
                                        launchSingleTop=true
                                    }
                                })
                            {
                                Text("Flexibility", modifier = Modifier.weight(1f))
                            }
                            FilledIconButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    viewModel.setCat(uiState.hiit, "HIIT")
                                    navController.navigate("exercise_list"){
                                        launchSingleTop=true
                                    }
                                })
                            {
                                Text("HIIT", modifier = Modifier.weight(1f))
                            }
                        }
                    }

                    //History log

                    Card(
                        //modifier = Modifier.weight(1f)
                        ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text("Exercise Name:")
                            Text("Time (mins):")
                        }
                        if (uiState.wList.isEmpty()) {
                            Text(
                                "No workouts logged yet. Select a category above to get started!",
                                textAlign = TextAlign.Center
                            )
                        } else {
                            LazyColumn(
                                state = listState,
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            )
                            {
                                items(
                                    uiState.wList,
                                    key = { it.id }
                                ) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.LightGray,
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceAround
                                        ) {
                                            Text(it.name)
                                            Text(it.mins.toString())
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //Clear log
                    FilledIconButton(
                        modifier = Modifier
                            //.weight(1f)
                            .padding(8.dp)
                            .fillMaxWidth(),
                        onClick = {
                            viewModel.reset()
                        })
                    {
                        Text("Clear Workout History", modifier = Modifier.weight(1f))
                    }
                }
            }

        }


        // Page 2
        composable(route = "exercise_list") {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(uiState.catName,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        navigationIcon={
                            FilledIconButton(
                                modifier = Modifier
                                    .padding(8.dp),
                                onClick = {
                                    navController.navigate("home"){
                                        popUpTo("home"){
                                            inclusive=true
                                        }
                                    }
                                })
                            {
                                Text("<-")
                            }
                        }
                    )
                }
            ) {
                innerPadding -> LazyColumn(
                    modifier = Modifier
                        .background(Color.Cyan)
                        .padding(innerPadding)
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    items(
                        items = uiState.curCat,
                        key = { it.id }
                    ) {
                        Card {
                            Column {
                                FilledIconButton(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .fillMaxWidth(),
                                    onClick = {
                                        viewModel.setEx(it)
                                        navController.navigate("exercise_specific"){
                                            launchSingleTop=true
                                        }
                                    })
                                {
                                    Text(it.name)
                                }
                                Card(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = it.c
                                    )
                                ) {
                                    Text(
                                        it.difficulty, textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .fillMaxWidth()
                                    )
                                }
                                Text(
                                    it.preview, textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }


        // Page 3
        composable(route = "exercise_specific") {
            if(uiState.showAlert){
                BasicAlertDialog(
                    onDismissRequest = {},
                    content = {
                        Column {
                            Card(modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()){
                                Text("Added: "+uiState.curExercise.name+" for "+
                                        uiState.lastTime+" min(s)",
                                    modifier = Modifier.padding(4.dp).fillMaxWidth(),
                                    textAlign = TextAlign.Center)
                            }
                            FilledIconButton(modifier = Modifier.fillMaxWidth(),
                                onClick={
                                    viewModel.alertOff()
                                }
                            ) {
                                Text("Dismiss")
                            }
                        }

                    }
                )
            }
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(uiState.curExercise.name,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        navigationIcon={
                            FilledIconButton(
                                modifier = Modifier
                                    .padding(8.dp),
                                onClick = {
                                    navController.popBackStack()
                                })
                            {
                                Text("<-")
                            }
                        }
                    )
                }
            ) {
                innerPadding -> Column(
                    modifier = Modifier
                        .background(Color.Cyan)
                        .padding(innerPadding)
                        .padding(32.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Card(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(2.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = uiState.curExercise.c
                        )
                    ) {
                        Text(
                            uiState.curExercise.difficulty, textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()
                        )
                    }
                    Card(
                        modifier = Modifier
                            .height(300.dp)
                            .padding(2.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            uiState.curExercise.steps, textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()
                        )
                    }
                    Card(
                            modifier = Modifier
                                .height(50.dp)
                                .padding(2.dp)
                                .fillMaxWidth()
                            ) {
                        Text(
                            "Recommended Duration: "+uiState.curExercise.recDur.toString()
                            +" Minutes",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()
                        )
                    }
                    Card(
                        modifier = Modifier
                            .height(100.dp)
                            .padding(2.dp)
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth(),
                            value = uiState.inText,
                            onValueChange = { viewModel.setText(it) },
                            label = { Text("Minutes Completed") }
                        )
                    }
                    FilledIconButton(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(8.dp)
                            .fillMaxWidth(),
                        enabled = uiState.buttonOn,
                        //colors = ButtonDefaults.buttonColors(uiState.buttonColor),
                        onClick = {
                            viewModel.add(
                                Workout(
                                    uiState.curExercise.name,
                                    uiState.inText.toInt(),
                                    uiState.wList.size
                                )
                            )
                        }
                    )
                    {
                        Text("Log Completed Workout")
                    }
                    Card(
                        modifier = Modifier
                            .height(150.dp)
                            .padding(4.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            uiState.errorText,
                            modifier = Modifier.padding(32.dp),
                            color = uiState.errorColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CMP431Project1Theme {
        AppNavigation()
    }
}