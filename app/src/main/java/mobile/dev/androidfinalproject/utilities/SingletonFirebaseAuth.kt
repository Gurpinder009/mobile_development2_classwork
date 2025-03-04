package mobile.dev.androidfinalproject.utilities


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase


class SingletonFirebaseAuth private constructor(
    private val auth:FirebaseAuth = Firebase.auth
){

    companion object{
        @Volatile private var instance:SingletonFirebaseAuth?= null;


        fun getInstance()=
            instance?: synchronized(this){
                instance?: SingletonFirebaseAuth().also { instance = it}
            }

    }
    fun getFirebaseAuth(): FirebaseAuth {
        return auth
    }

    fun isLoggedIn():Boolean{
        return auth.currentUser !== null
    }






}