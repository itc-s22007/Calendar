package jp.ac.it_college.std.s22007.tecnos_assigment

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.ac.it_college.std.s22007.tecnos_assigment.Calendar.Calendar
import java.time.LocalDate
import java.time.YearMonth

object Destination {
    const val CALENDAR = "calendar"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
) {
    var calendarText by remember { mutableStateOf("")}
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = calendarText)
            })
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Destination.CALENDAR,
            modifier = Modifier.padding(it)
        ){
            composable(Destination.CALENDAR){
                Calendar(
                    yearMonth = YearMonth.now(),
                    selectedDate = LocalDate.now(),
                    onDateSelected = {}
                )
            }
        }
    }
}