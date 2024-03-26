package jp.ac.it_college.std.s22007.tecnos_assigment

import com.kizitonwose.calendar.core.DayPosition
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
import com.kizitonwose.calendar.core.CalendarDay
import jp.ac.it_college.std.s22007.tecnos_assigment.Calendar.CalendarDisplay
import jp.ac.it_college.std.s22007.tecnos_assigment.Schedule.ScheduleScene
import java.time.LocalDate

object Destination {
    const val CALENDAR = "calendar"
    const val SCHEDULE = "schedule"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
) {
    var calendarDay by remember { mutableStateOf(CalendarDay(LocalDate.now(), DayPosition.MonthDate)) } // 選択された日付を保持する

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "カレンダー")
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
                    onDayClick = { day ->
                        calendarDay = day // カレンダーで選択された日付を保持する
                        navController.navigate(Destination.SCHEDULE)
                    },
                )
            }
            composable(Destination.SCHEDULE){
                ScheduleScene(
                    onClickCalendarButton = {
                        navController.navigateUp() // カレンダー画面に戻る
                    },
                    calendarDay = calendarDay // 選択された日付をScheduleSceneに渡す
                )
            }
        }
    }
}
