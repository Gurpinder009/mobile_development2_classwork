package mobile.dev.androidfinalproject.models

import java.time.LocalDateTime

data class HealthLogsModel(
    val id:Int?,
    val caloriesConsumed:Double?,
    val dateTime: LocalDateTime?,
    val sleepDuration:Double?,
    val waterIntake:Double?,
    val userId:Int?
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HealthLogsModel

        if (id != other.id) return false
        if (caloriesConsumed != other.caloriesConsumed) return false
        if (dateTime != other.dateTime) return false
        if (sleepDuration != other.sleepDuration) return false
        if (waterIntake != other.waterIntake) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (caloriesConsumed?.hashCode() ?: 0)
        result = 31 * result + (dateTime?.hashCode() ?: 0)
        result = 31 * result + (sleepDuration?.hashCode() ?: 0)
        result = 31 * result + (waterIntake?.hashCode() ?: 0)
        result = 31 * result + (userId ?: 0)
        return result
    }

    override fun toString(): String {
        return "HealthLogsModel(id=$id, caloriesConsumed=$caloriesConsumed, dateTime=$dateTime, sleepDuration=$sleepDuration, waterIntake=$waterIntake, userId=$userId)"
    }
}