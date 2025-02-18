package mobile.dev.androidfinalproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.FragmentGetStartedBinding

class GetStartedFragment(
    private var _binding: FragmentGetStartedBinding?=null
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGetStartedBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.loginBtn?.setOnClickListener(this::toLoginPage)
        _binding?.signUpBtn?.setOnClickListener(this::toSignUpPage)
    }

    fun toLoginPage(view: View){
            Navigation.findNavController(view).navigate(R.id.action_getStartedFragment_to_loginInFragment2)
    }


    fun toSignUpPage(view:View){
            Navigation.findNavController(view).navigate(R.id.action_getStartedFragment_to_signUpFragment2)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}