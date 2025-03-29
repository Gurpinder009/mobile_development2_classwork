package mobile.dev.androidfinalproject.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import mobile.dev.androidfinalproject.databinding.FragmentDashboardBinding
import mobile.dev.androidfinalproject.models.HealthLogsModel
import mobile.dev.androidfinalproject.models.UserModel
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth
import mobile.dev.androidfinalproject.viewModels.HealthLogViewModel
import mobile.dev.androidfinalproject.viewModels.UserViewModel
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider

import kotlin.math.roundToInt


class DashboardFragment  constructor(

    private var _binding : FragmentDashboardBinding? = null,
    private var userDetails : UserModel? = null,
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
        _binding?.updateDetailsBtn?.setOnClickListener(this::updateDetails)

    }

    fun updateDetails(view:View){
        val action = DashboardFragmentDirections.actionDashboardFragmentToUpdateDetails(healthLog)
        Navigation.findNavController(view).navigate(action)
    }



    private fun initializeLogData(){
        val userViewModel= ViewModelProvider(requireActivity())[UserViewModel::class.java]
        val healthLogViewModel=ViewModelProvider(requireActivity())[HealthLogViewModel::class.java]


        healthLogViewModel.getHealthLog().observe(requireActivity()) { result ->
            healthLog = result
            userViewModel.getUser().observe(requireActivity()) { data ->
                this.userDetails = data
                initializeData(data!!)

            }
        }


    }





    @SuppressLint("SetTextI18n")
    fun initializeData(userDetails: UserModel) {
        _binding?.totalProgress?.text = "32%";


        val caloriesConsumed = calPercentage(healthLog.caloriesConsumed?:0.0, userDetails.targetCalories?:0.0).toDouble()
        val sleepDuration = calPercentage(healthLog.sleepDuration?:0.0, userDetails.targetSleepHours?:0.0).toDouble()
        val waterIntake = calPercentage(healthLog.waterIntake?:0.0, userDetails.targetWaterIntake?:0.0).toDouble()
        val exerciseTime=calPercentage(healthLog.exerciseTime?:0.0, userDetails.targetExerciseTime?:0.0).toDouble()
        val totalProgress = calculateTotalProgress(caloriesConsumed,sleepDuration,waterIntake,exerciseTime)

        _binding?.caloriesPercentage?.text = "${caloriesConsumed}%"
        _binding!!.caloriesConsumed.text = "${(healthLog.caloriesConsumed?:0.0)} / ${userDetails.targetCalories}"
        _binding?.sleepPercentage?.text = "${sleepDuration}%"
        _binding!!.sleepDuration.text = "${(healthLog.sleepDuration?:0.0)} / ${userDetails.targetSleepHours}";
        _binding?.waterPercentage?.text = "${waterIntake}%"
        _binding!!.waterIntake.text = "${(healthLog.waterIntake?:0.0)} / ${userDetails.targetWaterIntake}";
          _binding?.exerciseTimePercentage?.text = "${exerciseTime}%"
        _binding!!.exerciseTime.text = "${(healthLog.exerciseTime?:0.0)} / ${userDetails.targetExerciseTime}"
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
        return "%.1f".format(result)
    }







    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

















//        HealthLogsDbHelper.getHealthLog(
//            successListener = { result -> run {
//                if(result.documents.isEmpty()){
//                    HealthLogsDbHelper.postHealthLog(
//                        this.healthLog,
//                        successListener = {
//                            Toast.makeText(context,"New Entry Created",Toast.LENGTH_SHORT).show()
//                        },
//                        failureListener = {}
//                    )
//                }
//                else {
//
//                    val data = result.documents.first().data
//                    this.healthLog = HealthLogsModel.toHealthLog(data!!)
//                    Toast.makeText(context,"Data loaded successfully",Toast.LENGTH_SHORT).show()
//
//
//                }
//                setUiValues()
//            } }, failureListener = { error -> run {} },

//        )