package jp.ac.it_college.std.s22007.tecnos_assigment.Firebase

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

fun addScheduleToFireStore(date: String, time: String, schedule: String) {
    val db = Firebase.firestore

    val scheduleData = hashMapOf(
        "date" to date,
        "time" to time,
        "schedule" to schedule
    )

    db.collection("schedules")
        .add(scheduleData)
        .addOnSuccessListener { documentReference ->
            println("DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            println("Error adding document: $e")
        }
}

val db = FirebaseFirestore.getInstance()

suspend fun getScheduleData(): List<Map<String, Any>> {
    return try {
        val querySnapshot = db.collection("schedules").get().await()
        querySnapshot.documents.map { document -> document.data!! }
    } catch (e: Exception) {
        emptyList()
    }
}