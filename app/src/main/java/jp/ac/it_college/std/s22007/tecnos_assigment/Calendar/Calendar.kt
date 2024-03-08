package jp.ac.it_college.std.s22007.tecnos_assigment.Calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarHeader(yearMonth: YearMonth) {
    Text(
        text = "${yearMonth.year}年${yearMonth.month.value}月",
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}


@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,

) {
    val firstDayOfMonth = yearMonth.atDay(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val weeksInMonth = yearMonth.lengthOfMonth() / 7 + 1

    Surface(modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            CalendarHeader(yearMonth = yearMonth) // 追加: カレンダーのヘッダー部分に年月を表示
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                val daysOfWeek = listOf("日", "月", "火", "水", "木", "金", "土")
                for (dayOfWeek in daysOfWeek) {
                    Text(
                        text = dayOfWeek,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f)
                    )
                }
            }
            repeat(weeksInMonth) { week ->
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(7) { day ->
                        val currentDay = (week * 7 + day + 1) - firstDayOfWeek
                        val currentDate =
                            if (currentDay > 0 && currentDay <= yearMonth.lengthOfMonth()) {
                                yearMonth.atDay(currentDay)
                            } else {
                                null
                            }
                        CalendarDay(
                            date = currentDate,
                            selectedDate = selectedDate,
                            onDateSelected = { onDateSelected(it) }
                        )
                    }
                }

            }
        }
    }
}


@Composable
fun CalendarDay(
    date: LocalDate?,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val isSelected = date == selectedDate
    val isToday = date == LocalDate.now()

    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val contentColor =
        if (isSelected) MaterialTheme.colorScheme.onPrimary
        else if (isToday) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.onSurface

    val backgroundColorForDayOfWeek = when (date?.dayOfWeek) {
        DayOfWeek.SATURDAY -> Color.Blue
        DayOfWeek.SUNDAY -> Color.Red
        else -> contentColor
    }
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp)
            .background(color = backgroundColor, shape = CircleShape)
            .clickable { date?.let(onDateSelected) },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = date?.dayOfMonth?.toString() ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = backgroundColorForDayOfWeek,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(5.dp),
        )
    }
}

@Composable
fun CalendarManagerLayout(
    modifier: Modifier = Modifier,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    if (dragAmount > 100) {
                        onSwipeLeft()
                        change.consumeAllChanges()
                    } else if (dragAmount < -100) {
                        onSwipeRight()
                        change.consumeAllChanges()
                    }
                }
            }
    ) {
        content()
    }
}





@Preview
@Composable
fun CalendarPreview() {
    Calendar(yearMonth = YearMonth.now(), selectedDate = null) {}
}


