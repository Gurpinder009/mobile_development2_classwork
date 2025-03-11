package mobile.dev.androidfinalproject.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.DocumentSnapshot
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.ZoneId

data class HealthLogsModel(
    val id: Long?,
    val caloriesConsumed:Double?,
    val dateTime: LocalDateTime?,
    val sleepDuration:Double?,
    val waterIntake:Double?,
    val exerciseTime:Double?,
    val userId:Long?
) {


    constructor() : this(0.0, now(),0.0,0.0,0.0,0)
    constructor(
        caloriesConsumed: Double?,
        dateTime: LocalDateTime?,
        sleepDuration: Double?,
        waterIntake: Double?,
        exerciseTime: Double?,
        userId: Long?
    ) : this(null,caloriesConsumed,dateTime,sleepDuration,waterIntake,exerciseTime,userId)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HealthLogsModel

        if (id != other.id) return false
        if (caloriesConsumed != other.caloriesConsumed) return false
        if (dateTime != other.dateTime) return false
        if (sleepDuration != other.sleepDuration) return false
        if (waterIntake != other.waterIntake) return false
        if (exerciseTime != other.exerciseTime) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = (id ?: 0).toInt()
        result = 31 * result + (caloriesConsumed?.hashCode() ?: 0)
        result = 31 * result + (dateTime?.hashCode() ?: 0)
        result = 31 * result + (sleepDuration?.hashCode() ?: 0)
        result = 31 * result + (waterIntake?.hashCode() ?: 0)
        result = 31 * result + (exerciseTime?.hashCode() ?: 0)
        result = (31 * result + (userId ?: 0)).toInt()
        return result
    }

    override fun toString(): String {
        return "HealthLogsModel(id=$id, caloriesConsumed=$caloriesConsumed, dateTime=$dateTime, sleepDuration=$sleepDuration, waterIntake=$waterIntake, userId=$userId)"
    }

    companion object {
        fun toHealthLog(result: DocumentSnapshot): HealthLogsModel {
            val id = result.getLong("id")
//                val bmi = result.getDouble("bmi")
            val waterIntake = result.getDouble("water_intake")
            val sleepDuration:Double? = result.getDouble("sleep_duration")
            val exerciseTime = result.getDouble("exercise_time")
            val userId = result.getLong("user_id")
            val caloriesConsumed:Double? = result.getDouble("calories_consumed")
            val dateTime = LocalDateTime.ofInstant(result.getDate("date")!!.toInstant(), ZoneId.systemDefault())
            return HealthLogsModel(id,caloriesConsumed,dateTime,sleepDuration,waterIntake,exerciseTime,userId)
        }
    }
}