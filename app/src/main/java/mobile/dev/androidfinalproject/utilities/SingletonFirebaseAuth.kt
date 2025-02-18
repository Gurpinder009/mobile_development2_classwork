package mobile.dev.androidfinalproject.utilities


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase


class SingletonFirebaseAuth private constructor(){


    companion object{
        @Volatile private var instance:SingletonFirebaseAuth?= null;


        fun getInstance()=
            instance?: synchronized(this){
                instance?: SingletonFirebaseAuth().also { instance = it}
            }

    }
    fun getFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
}