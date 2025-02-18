package mobile.dev.androidfinalproject.fragments.userFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.FragmentDashboardBinding
import mobile.dev.androidfinalproject.databinding.FragmentLoginInBinding


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
        _binding?.loginPageToSignUpTextView?.setOnClickListener{
                _ -> run{
            Navigation.findNavController(view).navigate(R.id.action_loginInFragment2_to_signUpFragment2)
        }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}