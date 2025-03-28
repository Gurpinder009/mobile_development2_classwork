package mobile.dev.androidfinalproject.dbHelpers

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import mobile.dev.androidfinalproject.models.HealthLogsModel
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth
import java.time.LocalDate

class HealthLogsDbHelper {

    companion object{
        private const val COLLECTION_NAME= "health_logs";

        // for getting entire collection
        fun  getHealthLogs(successListener:(result: QuerySnapshot)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            val email = SingletonFirebaseAuth.getInstance().getCurrentUser().email
            db.collection(COLLECTION_NAME).whereEqualTo("userId",email).get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
        }


        //for getting a single document from collection
        fun getHealthLog(successListener:(result: QuerySnapshot)->Unit, failureListener:(exception:Exception)->Unit){
            val email = SingletonFirebaseAuth.getInstance().getCurrentUser().email
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection(COLLECTION_NAME).whereEqualTo("userId",email).whereEqualTo("createdAt",
                LocalDate.now().toString()).get()
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

        //for updating a document in a collection
     fun updateHealthLog(
            updatedHealthLog: HealthLogsModel,
            successListener: () -> Unit,
            failureListener: (exception: Exception) -> Unit
        ) {
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()

            getHealthLog(successListener={result ->
                val docs = result.documents
                for (ele in docs) {
                    db.collection(COLLECTION_NAME).document(ele.id).set(updatedHealthLog)
                        .addOnSuccessListener {successListener()}
                        .addOnFailureListener (failureListener)
                }
            },failureListener)
        }




    }
}