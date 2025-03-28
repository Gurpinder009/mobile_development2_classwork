package mobile.dev.androidfinalproject.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale


@Parcelize
data class HealthLogsModel(
    val id: Long?,
    val caloriesConsumed:Double?,
    val sleepDuration:Double?,
    val waterIntake:Double?,
    val exerciseTime:Double?,
    val userId:String,
    val createdAt:String
):Parcelable {


    constructor(userId: String) : this(0L, 0.0,0.0,0.0,0.0,userId, LocalDate.now().toString())



    fun simpleDate(): Date? {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Match your date format
            format.parse(createdAt) // Convert String to Date
        } catch (e: Exception) {
            null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HealthLogsModel

        if (id != other.id) return false
        if (caloriesConsumed != other.caloriesConsumed) return false
        if (sleepDuration != other.sleepDuration) return false
        if (waterIntake != other.waterIntake) return false
        if (exerciseTime != other.exerciseTime) return false
        if (userId != other.userId) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (caloriesConsumed?.hashCode() ?: 0)
        result = 31 * result + (sleepDuration?.hashCode() ?: 0)
        result = 31 * result + (waterIntake?.hashCode() ?: 0)
        result = 31 * result + (exerciseTime?.hashCode() ?: 0)
        result = 31 * result + userId.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }

    override fun toString(): String {
        return "HealthLogsModel(id=$id, caloriesConsumed=$caloriesConsumed, sleepDuration=$sleepDuration, waterIntake=$waterIntake, exerciseTime=$exerciseTime, userId='$userId', createdAt='$createdAt')"
    }


    companion object {
        fun toHealthLog(result: Map<String,Any>): HealthLogsModel {
            val id = (result["id"] as? Number)?.toLong() ?: 0L
            val waterIntake = (result["waterIntake"] as? Number)?.toDouble() ?: 0.0
            val sleepDuration = (result["sleepDuration"] as? Number)?.toDouble() ?: 0.0
            val exerciseTime = (result["exerciseTime"] as? Number)?.toDouble()
            val userId = result["userId"] as? String ?: "Unknown"
            val caloriesConsumed = (result["caloriesConsumed"] as? Number)?.toDouble() ?: 0.0
            val createdAt = (result["createdAt"]).toString()

            return HealthLogsModel(
                id = id,
                caloriesConsumed = caloriesConsumed,
                sleepDuration = sleepDuration,
                waterIntake = waterIntake,
                exerciseTime = exerciseTime,
                userId = userId,
                createdAt = createdAt
            )
        }
    }
}