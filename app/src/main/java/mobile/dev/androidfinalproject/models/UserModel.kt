package mobile.dev.androidfinalproject.models

import java.time.LocalDateTime

data class UserModel (
    val firstName:String?,

    val id:Int?,
    val lastName:String?,
    val emailAddress:String,
    val password:String,
    val height:Double?,
    val weight:Double?,
    val createdAt:LocalDateTime?,
    val updatedAt:LocalDateTime?,
    val targetCalories:Double?,
    val targetSleepHours:Double?
) {



    public constructor(email:String, password: String):this(null,null,null,emailAddress = email, password = password,null,null,null,null,null,null)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserModel

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (emailAddress != other.emailAddress) return false
        if (password != other.password) return false
        if (height != other.height) return false
        if (weight != other.weight) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        if (targetCalories != other.targetCalories) return false
        if (targetSleepHours != other.targetSleepHours) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result!! + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + emailAddress.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + targetCalories.hashCode()
        result = 31 * result + targetSleepHours.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserModel(id=$id, firstName='$firstName', lastName='$lastName', emailAddress='$emailAddress', password='$password', height=$height, weight=$weight, createdAt=$createdAt, updatedAt=$updatedAt, targetCalories=$targetCalories, targetSleepHours=$targetSleepHours)"
    }


}