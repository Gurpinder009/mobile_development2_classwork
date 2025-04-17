package mobile.dev.androidfinalproject.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.FragmentDashboardBinding
import mobile.dev.androidfinalproject.databinding.FragmentDayHistoryBinding
import mobile.dev.androidfinalproject.models.HealthLogsModel
import mobile.dev.androidfinalproject.models.UserModel
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth
import mobile.dev.androidfinalproject.viewModels.HealthLogViewModel
import mobile.dev.androidfinalproject.viewModels.UserViewModel
import kotlin.math.roundToInt

class DayHistoryFragment(
    private var userDetails : UserModel? = null,
    private var healthLog: HealthLogsModel = HealthLogsModel(SingletonFirebaseAuth.getInstance().getCurrentUser().email!!) ,
    private var _binding : FragmentDayHistoryBinding? = null,

    ) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayHistoryBinding.inflate(inflater,container,false);
        return _binding?.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val userViewModel= ViewModelProvider(requireActivity())[UserViewModel::class.java]



        val args : DayHistoryFragmentArgs by navArgs()
        this.healthLog = args.healthLog

        userViewModel.getUser().observe(requireActivity()) { data ->
            this.userDetails = data
            initializeData(data!!)
        }


    }




    @SuppressLint("SetTextI18n")
    fun initializeData(userDetails: UserModel) {


        val caloriesConsumed = calPercentage(healthLog.caloriesConsumed?:0.0, userDetails.targetCalories?:0.0).toDouble()
        val sleepDuration = calPercentage(healthLog.sleepDuration?:0.0, userDetails.targetSleepHours?:0.0).toDouble()
        val waterIntake = calPercentage(healthLog.waterIntake?:0.0, userDetails.targetWaterIntake?:0.0).toDouble()
        val exerciseTime=calPercentage(healthLog.exerciseTime?:0.0, userDetails.targetExerciseTime?:0.0).toDouble()
        val totalProgress = calculateTotalProgress(caloriesConsumed,sleepDuration,waterIntake,exerciseTime)

        _binding?.caloriesPercentage?.text = "${caloriesConsumed}%"
        _binding!!.caloriesConsumed.text = "${(healthLog.caloriesConsumed?:0.0)} / ${userDetails.targetCalories} cal"
        _binding?.sleepPercentage?.text = "${sleepDuration}%"
        _binding!!.sleepDuration.text = "${(healthLog.sleepDuration?:0.0)} / ${userDetails.targetSleepHours} hours";
        _binding?.waterPercentage?.text = "${waterIntake}%"
        _binding!!.waterIntake.text = "${(healthLog.waterIntake?:0.0)} / ${userDetails.targetWaterIntake} ml";
        _binding?.exerciseTimePercentage?.text = "${exerciseTime}%"
        _binding!!.exerciseTime.text = "${(healthLog.exerciseTime?:0.0)} / ${userDetails.targetExerciseTime} hours"
        _binding?.bmiValue?.text = calculateBMI(userDetails.weight?:0.0,userDetails.height?:0.0)



        _binding?.totalProgress?.text = "${totalProgress}%"
        _binding?.totalProgressBar?.progress = totalProgress


    }






    private fun calculateTotalProgress(calories:Double,sleepDuration:Double, waterIntake:Double,exerciseTime:Double):Int{
        val result = (calories + sleepDuration + exerciseTime + waterIntake) / 4
        return result.roundToInt()
    }



    private fun calculateBMI(weightKg: Double, heightFeet: Double): String {
        val heightMeters = heightFeet * 0.3048
        val result = weightKg / (heightMeters * heightMeters)
        return "%.1f".format(result)
    }


    private fun calPercentage(value:Double,total:Double): String {
        val result = ((value / total) * 100)
        if(result > 100){
            return "100"
        }
        return "%.1f".format(result)
    }







    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}