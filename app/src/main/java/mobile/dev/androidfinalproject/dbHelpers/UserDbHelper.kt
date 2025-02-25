package mobile.dev.androidfinalproject.dbHelpers
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import mobile.dev.androidfinalproject.models.UserModel

class UserDbHelper {

    companion object{

        // for getting entire collection
        fun  getUsers(successListener:(result:QuerySnapshot)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection("users").get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
        }


        //for getting a single document from collection
        fun getUser(id:String,successListener:(result:DocumentSnapshot)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection("users").document(id).get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener (failureListener)
        }


        //for saving a document in a collection
        fun postUser(user:UserModel,successListener:(ref:DocumentReference)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection("users").add(user)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
        }


        //for deleting a document from the collection
        fun deleteUser(id:String,successListener:(Void)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection("users").document(id).delete()
                .addOnSuccessListener(successListener)
                .addOnFailureListener (failureListener)
        }

    }

}