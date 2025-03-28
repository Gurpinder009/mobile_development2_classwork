package mobile.dev.androidfinalproject.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class UserModel(
    val firstName:String?,
    val lastName:String?,
    val emailAddress:String,
    val height:Double?,
    val weight:Double?,
    val createdAt: String,
    val updatedAt:String?,
    val targetCalories:Double?,
    val targetSleepHours:Double?,
    val targetWaterIntake:Double?,
    val targetExerciseTime:Double?,
): Parcelable {


    constructor(firstName: String?,lastName: String?,email: String) : this(firstName,lastName,
        emailAddress = email, null,null,
        LocalDateTime.now().toString(),null,null,null,null,null)



    constructor(email:String):this(null,null, emailAddress = email,null,null,
        LocalDateTime.now().toString(),null,null,null,null,null)



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserModel
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (emailAddress != other.emailAddress) return false
        if (height != other.height) return false
        if (weight != other.weight) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        if (targetCalories != other.targetCalories) return false
        if (targetSleepHours != other.targetSleepHours) return false
        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + emailAddress.hashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + targetCalories.hashCode()
        result = 31 * result + targetSleepHours.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserModel(firstName=$firstName, lastName=$lastName, emailAddress='$emailAddress', height=$height, weight=$weight, createdAt='$createdAt', updatedAt=$updatedAt, targetCalories=$targetCalories, targetSleepHours=$targetSleepHours, targetWaterIntake=$targetWaterIntake, targetExerciseTime=$targetExerciseTime)"
    }


    companion object {
        fun toUser(result: DocumentSnapshot): UserModel {
            return  UserModel(
                firstName = result.getString("firstName"),
                lastName = result.getString("lastName"),
                emailAddress = result.getString("emailAddress")!!,
                height = result.getDouble("height"),
                weight = result.getDouble("weight"),
                createdAt = result.getString("createdAt")!!,
                updatedAt = result.getString("updatedAt"),
                targetCalories = result.getDouble("targetCalories"),
                targetSleepHours = result.getDouble("targetSleepHours"),
                targetWaterIntake = result.getDouble("targetWaterIntake"),
                targetExerciseTime = result.getDouble("targetExerciseTime"),

            )
        }
    }

}