package jp.ac.it_college.std.s22007.tecnos_assigment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import jp.ac.it_college.std.s22007.tecnos_assigment.Calendar.CalendarDisplay
import jp.ac.it_college.std.s22007.tecnos_assigment.ui.theme.Tecnos_AssigmentTheme
import java.time.LocalDate
import java.time.YearMonth


class MainActivity : ComponentActivity() {
    private val selectedDate = mutableStateOf(LocalDate.now()) // ここでselectedDateを定義する

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tecnos_AssigmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Navigation()
                }
            }
        }
    }
}
