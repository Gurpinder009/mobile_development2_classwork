package mobile.dev.androidfinalproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.FragmentDashboardBinding


class DashboardFragment (
    private var _binding : FragmentDashboardBinding? = null
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater,container,false);
        return _binding?.root;
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}