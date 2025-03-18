package mobile.dev.androidfinalproject.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import mobile.dev.androidfinalproject.GetStartedActivity

import mobile.dev.androidfinalproject.databinding.FragmentDashboardBinding
import mobile.dev.androidfinalproject.dbHelpers.HealthLogsDbHelper
import mobile.dev.androidfinalproject.models.HealthLogsModel
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth


class DashboardFragment  constructor(
    private var _binding : FragmentDashboardBinding? = null,
    private var healthLog: HealthLogsModel = HealthLogsModel(SingletonFirebaseAuth.getInstance().getCurrentUser().email!!)
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater,container,false);
        return _binding?.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeLogData()

    }



    private fun initializeLogData(){
        HealthLogsDbHelper.getHealthLog(
            successListener = { result -> run {
                if(result.documents.isEmpty()){
                    HealthLogsDbHelper.postHealthLog(
                        this.healthLog,
                        successListener = { result ->
                            Toast.makeText(context,"New Entry Created",Toast.LENGTH_SHORT).show()
                        },
                        failureListener = {}
                    )
                }
                else {

                    val data = result.documents.first().data
                    this.healthLog = HealthLogsModel.toHealthLog(data!!)
                    Toast.makeText(context,"Data loaded successfully",Toast.LENGTH_SHORT).show()


                }
                setUiValues()
            } }, failureListener = { error -> run {} },

        )
    }




    @SuppressLint("SetTextI18n")
    private fun setUiValues(){
        _binding!!.totalProgress.text = "32%";
        _binding!!.caloriesPercentage.text =calPercentage(healthLog.caloriesConsumed!!,100.0).toString() +"%"
        _binding!!.caloriesConsumed.text = healthLog.caloriesConsumed.toString();
        _binding!!.sleepPercentage.text =calPercentage(healthLog.sleepDuration!!,90.0).toString() + "%"
        _binding!!.sleepIntake.text = healthLog.sleepDuration.toString()
        _binding!!.waterPercentage.text = calPercentage(healthLog.waterIntake!!,100.0).toString() + "%"
        _binding!!.waterIntake.text = healthLog.waterIntake.toString()
        _binding!!.exerciseTimePercentage.text = calPercentage(healthLog.exerciseTime!!, 80.0).toString() + "%"
        _binding!!.exerciseTime.text = healthLog.exerciseTime.toString()
    }


    private fun calPercentage(value:Double,total:Double): Double {
        return (value / total )*100
    }







    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}