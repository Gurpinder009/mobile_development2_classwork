package mobile.dev.androidfinalproject.fragments.userFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import mobile.dev.androidfinalproject.MainActivity
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.FragmentLoginInBinding
import mobile.dev.androidfinalproject.databinding.FragmentSignUpBinding


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




        _binding?.signupPageBtn?.setOnClickListener{
            _ -> run{
                val intent = Intent(context,MainActivity::class.java)
                startActivity(intent)
                activity?.finish()

        }
        }
        _binding?.signupToLoginTextView?.setOnClickListener{
            _ -> run{
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment2_to_loginInFragment2)
        }
        }
    }






    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}