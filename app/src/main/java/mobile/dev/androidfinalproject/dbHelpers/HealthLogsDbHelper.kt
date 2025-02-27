package mobile.dev.androidfinalproject.dbHelpers

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import mobile.dev.androidfinalproject.models.HealthLogsModel

class HealthLogsDbHelper {

    companion object{
        private const val COLLECTION_NAME= "health_logs";

        // for getting entire collection
        fun  getHealthLog(successListener:(result: QuerySnapshot)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection(COLLECTION_NAME).get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
        }


        //for getting a single document from collection
        fun getHealthLog(id:String, successListener:(result: DocumentSnapshot)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection(COLLECTION_NAME).document(id).get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener (failureListener)
        }


        //for saving a document in a collection
        fun postHealthLog(healthLog: HealthLogsModel, successListener:(ref: DocumentReference)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection(COLLECTION_NAME).add(healthLog)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
        }


        //for deleting a document from the collection
        fun deleteHealthLog(id:String,successListener:(Void)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection(COLLECTION_NAME).document(id).delete()
                .addOnSuccessListener(successListener)
                .addOnFailureListener (failureListener)
        }

    }
}