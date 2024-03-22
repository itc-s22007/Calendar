package jp.ac.it_college.std.s22007.tecnos_assigment.holidayAPI

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


data class Holiday(
    val date: String,
    val name: String
)

fun parseHolidays(json: String?): List<Holiday>? {
    return json?.let {
        val type = object : TypeToken<Map<String, String>>() {}.type
        val holidaysMap: Map<String, String> = Gson().fromJson(it, type)

        holidaysMap.map { entry ->
            Holiday(date = entry.key, name = entry.value)
        }
    }
}

suspend fun fetchHolidays(): String? {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://holidays-jp.github.io/api/v1/date.json")
        .build()

    return withContext(Dispatchers.IO) {
        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    response.body?.string() // 成功した場合、レスポンスボディを文字列で返す
                } else {
                    null // リクエストが失敗した場合はnullを返す
                }
            }
        } catch (e: Exception) {
            null // 例外が発生した場合はnullを返す
        }
    }
}

@Composable
fun GetHoliday(): List<Holiday> {
    val scope = rememberCoroutineScope()
    var holidays by remember {
        mutableStateOf<List<Holiday>?>(listOf(Holiday(date = "2023-01-01", name = "元日")))
    }

    val coroutineScope = rememberCoroutineScope()
    val holidayMap = remember { mutableStateOf<Map<String, String>?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            // APIから祝日データを非同期で取得
            val json = fetchHolidays()
            // 取得したJSONデータを解析して祝日リストに変換
            val parsedHolidays = parseHolidays(json)
            // 結果をholidaysにセット
            holidays = parsedHolidays
        }
    }
    return holidays!!
}

@Composable
fun HolidayItem(holiday: Holiday): String {
    Text(text = holiday.name)
    return holiday.name
}
