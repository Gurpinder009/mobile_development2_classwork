package mobile.dev.androidfinalproject.fragments.userFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import mobile.dev.androidfinalproject.MainActivity
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.FragmentDashboardBinding
import mobile.dev.androidfinalproject.databinding.FragmentLoginInBinding
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth


class LoginInFragment
    (
    private var _binding : FragmentLoginInBinding? = null
            ): Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginInBinding.inflate(inflater,container,false);
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.loginPageToSignUpTextView?.setOnClickListener(this::handleLoginToSignUp)
            _binding?.loginBtn?.setOnClickListener(this::handleLogin)
    }

    fun handleLoginToSignUp(view:View){
        Navigation.findNavController(view).navigate(R.id.action_loginInFragment2_to_signUpFragment2)
    }

    fun handleLogin(view:View){
        val auth = SingletonFirebaseAuth.getInstance().getFirebaseAuth();
        val email = _binding?.emailAddressEditText?.text.toString()
        val password = _binding?.passwordEditText?.text.toString();

        if(email.isNotEmpty() and  password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener{_->run{
                Toast.makeText(context,"Successfully logged in",Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()

            }}
            .addOnFailureListener{result->run{
                Log.i("log1", "handleLogin: "+ result.message)
                Toast.makeText(context,"Something gone wrong",Toast.LENGTH_SHORT).show()

            }}
        }else{
            Toast.makeText(context,"email and password can't be empty!!",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}