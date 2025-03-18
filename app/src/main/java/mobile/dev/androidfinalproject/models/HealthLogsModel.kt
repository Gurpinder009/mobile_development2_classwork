package mobile.dev.androidfinalproject.models

import com.google.firebase.firestore.DocumentSnapshot
import java.time.LocalDate


data class HealthLogsModel(
    val id: Long?,
    val caloriesConsumed:Double?,
    val sleepDuration:Double?,
    val waterIntake:Double?,
    val exerciseTime:Double?,
    val userId:String,
    val createdAt:String
) {


    constructor(userId: String) : this(0.0, 0.0,0.0,0.0,userId)
    constructor(
        caloriesConsumed: Double?,
        sleepDuration: Double?,
        waterIntake: Double?,
        exerciseTime: Double?,
        userId: String
    ) : this(null,caloriesConsumed,sleepDuration,waterIntake,exerciseTime,userId, LocalDate.now().toString())


    constructor(
        id: Long,
        caloriesConsumed: Double?,
        sleepDuration: Double?,
        waterIntake: Double?,
        exerciseTime: Double?,
        userId: String
    ) : this(id,caloriesConsumed,sleepDuration,waterIntake,exerciseTime,userId,LocalDate.now().toString())



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

        return true
    }

    override fun hashCode(): Int {
        var result = (id ?: 0).toInt()
        result = 31 * result + (caloriesConsumed?.hashCode() ?: 0)
        result = 31 * result + (sleepDuration?.hashCode() ?: 0)
        result = 31 * result + (waterIntake?.hashCode() ?: 0)
        result = 31 * result + (exerciseTime?.hashCode() ?: 0)
        result = (31 * result + userId.hashCode())
        return result
    }

    override fun toString(): String {
        return "HealthLogsModel(id=$id, caloriesConsumed=$caloriesConsumed,  sleepDuration=$sleepDuration, waterIntake=$waterIntake, userId=$userId)"
    }

    companion object {
        fun toHealthLog(result: Map<String,Any>): HealthLogsModel {
            // Use safe calls and handle potential null values
            val id = (result["id"] as? Number)?.toLong() ?: 0 // Default to 0 or handle null as needed
            val waterIntake = result["waterIntake"] as? Double?
            val sleepDuration = result["sleepDuration"] as? Double?
            val exerciseTime = result["exerciseTime"] as? Double?
            val userId = result["userId"] as String
            val caloriesConsumed = result["caloriesConsumed"]  as? Double?

            return HealthLogsModel(
                id = id,
                caloriesConsumed = caloriesConsumed,
                sleepDuration = sleepDuration,
                waterIntake = waterIntake,
                exerciseTime = exerciseTime,
                userId = userId
            )
        }
    }
}