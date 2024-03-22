package jp.ac.it_college.std.s22007.tecnos_assigment.Schedule

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import jp.ac.it_college.std.s22007.tecnos_assigment.Firebase.addScheduleToFireStore
import jp.ac.it_college.std.s22007.tecnos_assigment.Firebase.getScheduleData
import jp.ac.it_college.std.s22007.tecnos_assigment.ui.theme.Tecnos_AssigmentTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun ScheduleScene(
    onClickCalendarButton: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }
    val selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var scheduleData by remember { mutableStateOf<Map<LocalDate, List<Pair<LocalTime, String>>>>(emptyMap()) }

    // Firestore からデータを取得する
    LaunchedEffect(Unit) {
        val allScheduleData = getScheduleData()
        // 日付ごとに整理する
        scheduleData = allScheduleData
            .groupBy { LocalDate.parse(it["date"].toString()) }
            .mapValues { entry ->
                entry.value.map {
                    val time = LocalTime.parse(it["time"].toString())
                    val schedule = it["schedule"].toString()
                    time to schedule
                }
            }
    }

    Surface {
        Column {
            Row {
                Button(
                    onClick = { onClickCalendarButton() },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                ) {
                    Text("＜",
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {
                        showDialog = true
                    }
                ) {
                    Text("予定を追加する")
                }
                if (showDialog) {
                    ScheduleDialog(
                        date = selectedDate,
                        onDialogDismiss = { showDialog = false },
                        onScheduleAdded = { schedule ->
                            // 新しいスケジュールが追加されたときにFirestoreにも登録する
                            addScheduleToFireStore(
                                date = selectedDate.toString(),
                                time = schedule.first.toString(),
                                schedule = schedule.second
                            )
                        }
                    )
                }
            }
            // 選択した日付の日に関連する予定のみを表示する
            scheduleData[selectedDate]?.forEach { (time, schedule) ->
                Text(
                    "$time - $schedule",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun ScheduleDialog(
    date: LocalDate,
    onDialogDismiss: () -> Unit, // ダイアログが閉じられたときに呼び出されるコールバック
    onScheduleAdded: (Pair<LocalTime, String>) -> Unit
) {
    var time by remember { mutableStateOf<LocalTime?>(null) }
    var schedule by remember { mutableStateOf("") }
    val context = LocalContext.current
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    AlertDialog(
        onDismissRequest = onDialogDismiss, // ダイアログが閉じられたときに onDialogDismiss を呼び出す
        title = { Text("スケジュールを追加") },
        text = {
            Column {
                Button(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        val hour = calendar.get(Calendar.HOUR_OF_DAY)
                        val minute = calendar.get(Calendar.MINUTE)
                        TimePickerDialog(
                            context,
                            { _: TimePicker, hourOfDay: Int, minuteOfHour: Int ->
                                time = LocalTime.of(hourOfDay, minuteOfHour)
                            },
                            hour,
                            minute,
                            true
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (time == null) "時間を選択" else time!!.format(timeFormatter))
                }
                TextField(
                    value = schedule,
                    onValueChange = { schedule = it },
                    label = { Text("予定") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val timeNotNull = requireNotNull(time) { "Time must not be null" }
                    val schedulePair = timeNotNull to schedule
                    onScheduleAdded(schedulePair)
                    onDialogDismiss() // ダイアログを閉じる
                },
                enabled = time != null
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDialogDismiss) {
                Text("キャンセル")
            }
        }
    )
}

@Preview
@Composable
fun ScheduleScenePreview() {
    Tecnos_AssigmentTheme {
        ScheduleScene()
    }
}

@Preview
@Composable
fun ScheduleDialogPreview() {
    Tecnos_AssigmentTheme {
        ScheduleDialog(
            date = LocalDate.now(),
            onDialogDismiss = { },
            onScheduleAdded = { }
        )
    }
}
