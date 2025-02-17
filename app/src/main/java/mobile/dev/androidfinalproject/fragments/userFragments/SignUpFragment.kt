package mobile.dev.androidfinalproject.fragments.userFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}