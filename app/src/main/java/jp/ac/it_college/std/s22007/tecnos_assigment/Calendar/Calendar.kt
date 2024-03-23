package jp.ac.it_college.std.s22007.tecnos_assigment.Calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import jp.ac.it_college.std.s22007.tecnos_assigment.holidayAPI.GetHoliday
import jp.ac.it_college.std.s22007.tecnos_assigment.holidayAPI.Holiday
import jp.ac.it_college.std.s22007.tecnos_assigment.ui.theme.Tecnos_AssigmentTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CalendarDisplay(
    modifier: Modifier = Modifier,
    onDayClick: (LocalDate) -> Unit = {},
    onClickScheduleButton: () -> Unit = {},
) {
    // 現在の年月
    val currentMonth = remember { YearMonth.now() }
    // 現在より前の年月
    val startMonth = remember { currentMonth.minusMonths(24) }
    // 現在より後の年月
    val endMonth = remember { currentMonth.plusMonths(24) }
    // 曜日
    val daysOfWeek = remember { daysOfWeek() }

    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    val scheduleList = remember { mutableStateOf<List<String>>(emptyList()) }

    // カレンダーの状態を持つ
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfGrid
    )

    Surface {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${state.firstVisibleMonth.yearMonth}",
                        modifier = Modifier.padding(10.dp),
                        fontSize = 40.sp,
                    )
                }
            }
            DaysOfWeekTitle(daysOfWeek = daysOfWeek)

            HorizontalCalendar(
                state = state,
                dayContent = { date ->
                    Day(
                        date,
                        onDayClick,
                        onScheduleAdded = { schedule -> updateScheduleList(schedule, scheduleList) },
                        onClickScheduleButton = onClickScheduleButton,
                        holidays = GetHoliday()
                    )
                },
            )

            selectedDate.value?.let { date ->
                Text(
                    text = "選択した日付: ${date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))}",
                    modifier = Modifier.padding(8.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(scheduleList.value) { schedule ->
                        Text(
                            text = schedule,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Day(
    day: CalendarDay,
    onDayClick: (LocalDate) -> Unit,
    onScheduleAdded: (String) -> Unit,
    onClickScheduleButton: () -> Unit,
    holidays: List<Holiday>
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable {
                onDayClick(day.date) // 日付が選択されたことを通知
                onClickScheduleButton() // 画面遷移を行う
            },
        contentAlignment = Alignment.Center
    ) {
        val textColor = when (day.date.dayOfWeek) {
            DayOfWeek.SATURDAY -> Color.Blue // 土曜日の場合の色
            DayOfWeek.SUNDAY -> Color.Red // 日曜日の場合の色
            else -> if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray

        }
        val isHoliday = holidays.any { holiday ->
            holiday.date == day.date.toString()
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            // ここで今月でないものの日付をグレーアウトさせている
            color = if (isHoliday) Color.Red else textColor // 祝日の場合は赤色にする
        )
    }
}
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

        Row {
            val daysOfWeek = listOf("日", "月", "火", "水", "木", "金", "土")
            for (dayOfWeek in daysOfWeek) {
                Text(
                    text = dayOfWeek,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun CalendarDay(day: CalendarDay, holidays: List<Holiday>, textColor: Color) { // 祝日表示変えた
    val isHoliday = holidays.any { holiday ->
        holiday.date == day.date.toString()
    }

    Text(
        modifier = Modifier
            .background(color = if (isHoliday) Color.Yellow else Color.Transparent)
            .padding(top = 3.dp, start = 4.dp),
        text = day.date.dayOfMonth.toString(),
        color = textColor
    )
}

fun updateScheduleList(schedule: String, scheduleList: MutableState<List<String>>) {
    scheduleList.value = scheduleList.value + schedule
}

@Preview(showBackground = true)
@Composable
fun StartScenePreview() {
    Tecnos_AssigmentTheme {
        CalendarDisplay()
    }
}
