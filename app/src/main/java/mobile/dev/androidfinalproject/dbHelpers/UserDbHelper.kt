package mobile.dev.androidfinalproject.dbHelpers
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import mobile.dev.androidfinalproject.models.UserModel

class UserDbHelper {

    companion object{
        private const val COLLECTION_NAME="users"
        // for getting entire collection
        fun  getUsers(successListener:(result:QuerySnapshot)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection(COLLECTION_NAME).get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
        }


        //for getting a single document from collection
        fun getUser(id:String,successListener:(result:DocumentSnapshot)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection(COLLECTION_NAME).document(id).get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener (failureListener)
        }


        //for saving a document in a collection
        fun postUser(user:UserModel,successListener:(Void?)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection(COLLECTION_NAME).document(user.emailAddress).set(user)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
        }


        fun updateDetails(email:String,data:Map<String,Any>,successListener:(Void?)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection(COLLECTION_NAME).document(email).update(data)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener)
        }




        //for deleting a document from the collection
        fun deleteUser(id:String,successListener:(Void)->Unit, failureListener:(exception:Exception)->Unit){
            val db = SingletonFirebaseDb.getInstance().getFirestoreDb()
            db.collection(COLLECTION_NAME).document(id).delete()
                .addOnSuccessListener(successListener)
                .addOnFailureListener (failureListener)
        }

    }

}