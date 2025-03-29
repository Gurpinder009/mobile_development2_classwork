package mobile.dev.androidfinalproject.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobile.dev.androidfinalproject.dbHelpers.UserDbHelper
import mobile.dev.androidfinalproject.models.UserModel

class UserViewModel : ViewModel() {

    private val _user = MutableLiveData<UserModel>()


    fun setUser(user: UserModel) {
        _user.value = user
    }

    fun getUser(): LiveData<UserModel> {
        return _user
    }


    fun fetchUser(email: String) {
        UserDbHelper.getUser(email, successListener = { result ->
            _user.value = UserModel.toUser(result)
        }, failureListener = { error ->
        })
    }


//    fun updateUser(user: UserModel) {
//        UserDbHelper.updateUser(user, successListener = { result ->
//            _user.value = UserModel.toUser(result)
//        }, failureListener = { error ->
//        })
//    }
}