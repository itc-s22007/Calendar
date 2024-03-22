package jp.ac.it_college.std.s22007.tecnos_assigment

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.ac.it_college.std.s22007.tecnos_assigment.Calendar.CalendarDisplay
import jp.ac.it_college.std.s22007.tecnos_assigment.Schedule.ScheduleScene

object Destination {
    const val CALENDAR = "calendar"
    const val SCHEDULE = "schedule"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "┌(┌＾o＾)┐ﾎﾓｫ…")
            })
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Destination.CALENDAR,
            modifier = Modifier.padding(it)
        ){
            composable(Destination.CALENDAR){
                CalendarDisplay(
                    onClickScheduleButton = {
                        navController.navigate(Destination.SCHEDULE)
                    }
                )
            }
            composable(Destination.SCHEDULE){
                ScheduleScene {
                    navController.navigate(Destination.CALENDAR)
                }
            }
        }
    }
}
