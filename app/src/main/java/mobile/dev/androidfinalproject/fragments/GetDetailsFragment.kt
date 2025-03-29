package mobile.dev.androidfinalproject.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import mobile.dev.androidfinalproject.MainActivity
import mobile.dev.androidfinalproject.databinding.FragmentGetDetailsBinding
import mobile.dev.androidfinalproject.dbHelpers.UserDbHelper
import mobile.dev.androidfinalproject.models.UserModel
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth


class GetDetailsFragment(
    private var _binding: FragmentGetDetailsBinding? = null
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGetDetailsBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.moreDetailsSaveBtn?.setOnClickListener(this::handleSave)

        val args = GetDetailsFragmentArgs.fromBundle(arguments?: Bundle())
        val user:UserModel? = args.userDetails
        if(user!= null) {
            initialize(user)
        }


    }

        private fun initialize(user:UserModel){
        _binding?.heightEditText?.setText(user.height.toString())
        _binding?.weightEditText?.setText(user.weight.toString())
        _binding?.targetCaloriesEditText?.setText(user.targetCalories.toString())
        _binding?.targetSleepHoursEditText?.setText(user.targetSleepHours.toString())
        _binding?.targetWaterIntakeEditText?.setText(user.targetWaterIntake.toString())
        _binding?.targetExerciseTimeEditText?.setText(user.targetExerciseTime.toString())
    }




    @SuppressLint("UseValueOf")
    fun handleSave(view:View){

        val data = mapOf(
            "height" to java.lang.Double(_binding?.heightEditText?.text.toString()),
            "weight" to java.lang.Double(_binding?.weightEditText?.text.toString()),
            "targetCalories" to java.lang.Double(_binding?.targetCaloriesEditText?.text.toString()),
            "targetSleepHours" to java.lang.Double(_binding?.targetSleepHoursEditText?.text.toString()),
            "targetWaterIntake" to java.lang.Double(_binding?.targetWaterIntakeEditText?.text.toString()),
            "targetExerciseTime" to java.lang.Double(_binding?.targetExerciseTimeEditText?.text.toString()),
        )
        val email = SingletonFirebaseAuth.getInstance().getCurrentUser().email!!
        UserDbHelper.updateDetails(email,data, successListener = { _ ->
                Toast.makeText(context, "Data saved Successfully", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
        },
            failureListener = { error ->
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}