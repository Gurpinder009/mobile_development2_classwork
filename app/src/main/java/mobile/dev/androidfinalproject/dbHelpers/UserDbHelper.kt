package mobile.dev.androidfinalproject.dbHelpers
import com.google.firebase.firestore.DocumentSnapshot
import mobile.dev.androidfinalproject.models.UserModel

class UserDbHelper {

    companion object{
        private const val COLLECTION_NAME="users"


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

    }

}