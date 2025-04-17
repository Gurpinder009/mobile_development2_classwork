package mobile.dev.androidfinalproject.fragments.userFragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import mobile.dev.androidfinalproject.databinding.FragmentProfileBinding
import mobile.dev.androidfinalproject.models.UserModel
import mobile.dev.androidfinalproject.viewModels.UserViewModel

class ProfileFragment(
    private var _binding:FragmentProfileBinding?=null,
    private var userDetails:UserModel?=null
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        val userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        userViewModel.getUser().observe(requireActivity()){ data->
            this.userDetails = data
            initializeData(userDetails!!)
        }


        _binding?.profileUpdateButton?.setOnClickListener(this::handleProfileUpdate)
    }

    private fun initializeData(data: UserModel){
        setLabels()
        setValues(data)
    }

    @SuppressLint("SetTextI18n")
    private fun setValues(data: UserModel){
        _binding?.profileDetailName?.profilePageValueTextView?.text = data.firstName +" " + data.lastName
        _binding?.profileDetailEmail?.profilePageValueTextView?.text = data.emailAddress
        _binding?.profileDetailHeight?.profilePageValueTextView?.text = data.height.toString()
        _binding?.profileDetailWeight?.profilePageValueTextView?.text = data.weight.toString()
        _binding?.profileDetailTargetCalories?.profilePageValueTextView?.text = data.targetCalories.toString()
        _binding?.profileDetailTargetSleepCalories?.profilePageValueTextView?.text = data.targetSleepHours.toString()
        _binding?.profileDetailTargetExerciseTime?.profilePageValueTextView?.text = data.targetExerciseTime.toString()
        _binding?.profileDetailTargetWaterIntake?.profilePageValueTextView?.text = data.targetWaterIntake.toString()
    }

    private fun setLabels(){
        _binding?.profileDetailName?.profilePageLabelTextView?.text = "Name: "
        _binding?.profileDetailEmail?.profilePageLabelTextView?.text = "Email:"
        _binding?.profileDetailHeight?.profilePageLabelTextView?.text = "Height: "
        _binding?.profileDetailWeight?.profilePageLabelTextView?.text = "Weight: "
        _binding?.profileDetailTargetCalories?.profilePageLabelTextView?.text = "Target Calories: "
        _binding?.profileDetailTargetSleepCalories?.profilePageLabelTextView?.text = "Target Sleep Hours: "
        _binding?.profileDetailTargetExerciseTime?.profilePageLabelTextView?.text = "Target Exercise Time:"
        _binding?.profileDetailTargetWaterIntake?.profilePageLabelTextView?.text = " Target Water Intake: "
    }




    fun handleProfileUpdate(view:View){
        val action = ProfileFragmentDirections.actionProfileFragmentToGetDetailsFragment2(userDetails!!)
       Navigation.findNavController(view).navigate(action)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}