package mobile.dev.androidfinalproject.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.FragmentGetStartedBinding
import mobile.dev.androidfinalproject.databinding.FragmentHealthLogReportBinding
import mobile.dev.androidfinalproject.dbHelpers.HealthLogsDbHelper
import mobile.dev.androidfinalproject.dbHelpers.SingletonFirebaseDb
import mobile.dev.androidfinalproject.models.HealthLogsModel
import mobile.dev.androidfinalproject.models.UserModel
import mobile.dev.androidfinalproject.utilities.HealthLogRecyclerViewAdapter
import mobile.dev.androidfinalproject.viewModels.UserViewModel
import kotlin.math.roundToInt


class HealthLogReportFragment(
    private var _binding: FragmentHealthLogReportBinding?=null,
    private var _listOfLogs: MutableList<HealthLogsModel> = mutableListOf(),
    private var _userDetails: UserModel? = null,
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
        val userViewModel= ViewModelProvider(requireActivity())[UserViewModel::class.java]

        userViewModel.getUser().observe(requireActivity()) { data ->
            this._userDetails = data
        }






        HealthLogsDbHelper.getHealthLogs(
            successListener = { result ->
                _listOfLogs.clear()


                if (result.documents.isEmpty()) {
                    Log.i("log1", "No health logs found.")
                } else {
                    for (docs in result.documents) {
                        docs.data?.let { data ->
                            val ele = HealthLogsModel.toHealthLog(data)
                            _listOfLogs.add(ele)
                        }
                    }
                }
                _listOfLogs.sortByDescending { it.simpleDate() }
                // Update adapter correctly
                if (_binding?.recycler?.adapter == null) {
                    _binding?.recycler?.layoutManager = LinearLayoutManager(context)

                    _binding?.recycler?.adapter = HealthLogRecyclerViewAdapter(_listOfLogs,_userDetails!!)
                } else {
                    _binding?.recycler?.adapter?.notifyDataSetChanged() // Notify changes properly
                }
            },
            failureListener = { error ->
                Log.e("HealthLogsError", "Error fetching logs", error)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }





}