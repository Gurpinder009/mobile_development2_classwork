package mobile.dev.androidfinalproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.FragmentGetStartedBinding
import mobile.dev.androidfinalproject.databinding.FragmentHealthLogReportBinding
import mobile.dev.androidfinalproject.utilities.HealthLogRecyclerViewAdapter


class HealthLogReportFragment(
    private var _binding: FragmentHealthLogReportBinding?=null
)
    : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHealthLogReportBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.recycler?.layoutManager = LinearLayoutManager(context)
        _binding?.recycler?.adapter = HealthLogRecyclerViewAdapter()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}