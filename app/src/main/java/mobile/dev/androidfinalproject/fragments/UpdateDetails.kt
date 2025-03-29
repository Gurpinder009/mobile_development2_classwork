package mobile.dev.androidfinalproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.FragmentUpdateDetailsBinding
import mobile.dev.androidfinalproject.dbHelpers.HealthLogsDbHelper
import mobile.dev.androidfinalproject.models.HealthLogsModel
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth
import mobile.dev.androidfinalproject.viewModels.HealthLogViewModel
import java.time.LocalDate


class UpdateDetails(
    private var binding:FragmentUpdateDetailsBinding?= null,

) : Fragment() {

        private var healthLogViewModel:HealthLogViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateDetailsBinding.inflate(inflater, container,false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       this.healthLogViewModel = ViewModelProvider(requireActivity())[HealthLogViewModel::class.java]



        this.healthLogViewModel?.getHealthLog()?.observe(requireActivity()) { result ->
            initializeValues(result)
        }


        this.binding?.updateDetailsSavingBtn?.setOnClickListener(this::handleUpdate)
    }



    private fun initializeValues(healthLog: HealthLogsModel){

        binding?.caloriesInputEditView?.setText(healthLog.caloriesConsumed.toString())
        binding?.sleepInputEditText?.setText(healthLog.sleepDuration.toString())
        binding?.waterInputEditText?.setText(healthLog.waterIntake.toString())
        binding?.exerciseInputEditText?.setText(healthLog.exerciseTime.toString())


    }


   fun handleUpdate(view:View) {
       val calories = binding?.caloriesInputEditView?.text.toString().toDouble()
       val sleep = binding?.sleepInputEditText?.text.toString().toDouble()
       val water = binding?.waterInputEditText?.text.toString().toDouble()
       val exercise = binding?.exerciseInputEditText?.text.toString().toDouble()





    val email= SingletonFirebaseAuth.getEmail()
        val updatedHealthLog = HealthLogsModel(0L,calories,sleep,water,exercise,email,LocalDate.now().toString())
        healthLogViewModel?.updateHealthLog(updatedHealthLog,
            successListener = {
                Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).popBackStack()
            },
            failureListener = { error ->
                Toast.makeText(requireContext(), "Failed to update: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

    }







    override fun onDestroy() {
        super.onDestroy()
        binding = null;
    }

}