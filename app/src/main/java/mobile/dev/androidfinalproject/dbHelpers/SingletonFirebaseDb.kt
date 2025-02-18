package mobile.dev.androidfinalproject.dbHelpers

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class SingletonFirebaseDb private constructor(){
    companion object{
        @Volatile private var instance:SingletonFirebaseDb?= null;


        fun getInstance()=
            instance?: synchronized(this){
                instance?: SingletonFirebaseDb().also { instance = it}
            }


        fun getFirestoreDb():FirebaseFirestore{
            return Firebase.firestore
        }



    }
}