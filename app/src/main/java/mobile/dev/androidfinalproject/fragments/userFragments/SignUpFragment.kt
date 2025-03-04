package mobile.dev.androidfinalproject.fragments.userFragments

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.auth.User
import mobile.dev.androidfinalproject.MainActivity
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.FragmentLoginInBinding
import mobile.dev.androidfinalproject.databinding.FragmentSignUpBinding
import mobile.dev.androidfinalproject.dbHelpers.UserDbHelper
import mobile.dev.androidfinalproject.models.UserModel
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth


class SignUpFragment (
    private var _binding : FragmentSignUpBinding?=null
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater,container,false);
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.signupPageBtn?.setOnClickListener(this::handleSignUp)
        _binding?.signupToLoginTextView?.setOnClickListener(this::handleSignUpToLogin)
    }


    private fun validateData(firstName:String, lastName:String, email: String, password:String, confirmPassword:String):String{
        var message=""
        if(firstName.isEmpty()) {
            message = "First name can't be empty"
        }else if(lastName.isEmpty()){
            message = "Last name can't be empty"
        }else if(email.isEmpty()){
            message = "Email address can't be empty"
        }else if(password.isEmpty()){
            message = "Password can't be empty"
        }else if(password != confirmPassword){
            message = "Password must match"
        }
        return message;
    }









    fun handleSignUp(view:View){
        val auth = SingletonFirebaseAuth.getInstance().getFirebaseAuth();
        val email = _binding?.emailAddressEditText?.text.toString()
        val password = _binding?.passwordEditText?.text.toString()
        val firstName = _binding?.firstNameEditText?.text.toString()
        val lastName = _binding?.lastNameEditText?.text.toString()
        val confirmPassword = _binding?.confirmPasswordEditText?.text.toString()
        val message = validateData(firstName,lastName,email,password,confirmPassword);



        if(message !=""){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        } else{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { _ ->
                    run {
                        val user = UserModel(firstName,lastName,email, password)
                        UserDbHelper.postUser(user, successListener = { ref ->
                            run {
                                Toast.makeText(
                                    context,
                                    "Successfully Logged In",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(context, MainActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }
                        }, failureListener = { error ->
                            run {
                                Log.i("log1", "handleSignUp: $error" )

                                Toast.makeText(
                                    context,
                                    "message $error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })


                    }
                }
                .addOnFailureListener { error ->
                    run {
                        Log.i("log1", "handleSignUp: " + error.message)

                        Toast.makeText(context, error.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }


        }



    }

    fun handleSignUpToLogin(view:View){
        Navigation.findNavController(view).navigate(R.id.action_signUpFragment2_to_loginInFragment2)
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}