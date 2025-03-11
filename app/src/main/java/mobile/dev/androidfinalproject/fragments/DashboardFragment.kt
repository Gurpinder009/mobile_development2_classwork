package mobile.dev.androidfinalproject.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import mobile.dev.androidfinalproject.GetStartedActivity

import mobile.dev.androidfinalproject.databinding.FragmentDashboardBinding
import mobile.dev.androidfinalproject.dbHelpers.HealthLogsDbHelper
import mobile.dev.androidfinalproject.models.HealthLogsModel
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth


class DashboardFragment  constructor(
    private var _binding : FragmentDashboardBinding? = null,
    private var healthLog: HealthLogsModel = HealthLogsModel()
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





        _binding?.signOut?.setOnClickListener{
            _-> run{
                SingletonFirebaseAuth.getInstance().getFirebaseAuth().signOut()
                val intent = Intent(context, GetStartedActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

    }



    private fun initializeLogData(){
        HealthLogsDbHelper.getHealthLog(
            successListener = { result -> run {
                if(result.data !=null){ this.healthLog = HealthLogsModel.toHealthLog(result)
                    setUiValues()
                } } }, failureListener = { error -> run {} },
            id = "gJxoxg6xgfa6HIdHPcSs"
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