package mobile.dev.androidfinalproject.utilities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import mobile.dev.androidfinalproject.R
import mobile.dev.androidfinalproject.databinding.HealthLogDailyReportCardBinding
import mobile.dev.androidfinalproject.fragments.HealthLogReportFragmentDirections
import mobile.dev.androidfinalproject.models.HealthLogsModel
import mobile.dev.androidfinalproject.models.UserModel
import kotlin.math.roundToInt

class HealthLogRecyclerViewAdapter(
    private val healthLogs:List<HealthLogsModel>,
    private val userDetails: UserModel,

) :
    RecyclerView.Adapter<HealthLogRecyclerViewAdapter.HealthLogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthLogViewHolder {
        val binding = HealthLogDailyReportCardBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return HealthLogViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return healthLogs.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HealthLogViewHolder, position: Int) {
        val totalProgress = calWeeklyProgress(userDetails, healthLogs[position])
        holder.binding.dayTitle.text = "Day: ${this.itemCount-position}  (${healthLogs[position].createdAt})"
        holder.binding.progressBar.progress = totalProgress;
        holder.binding.progressValue.text = "$totalProgress%"


        val action =HealthLogReportFragmentDirections.actionHealthLogReportFragmentToDayHistoryFragment(healthLogs[position])

        holder.binding.linearLayout.setOnClickListener {
           Navigation.findNavController(holder.binding.root).navigate(action)
        }
    }
    inner  class HealthLogViewHolder(val binding: HealthLogDailyReportCardBinding) : RecyclerView.ViewHolder(binding.root)





    private fun calWeeklyProgress(userDetails: UserModel, healthLog: HealthLogsModel):Int {


        val caloriesConsumed = calPercentage(healthLog.caloriesConsumed?:0.0, userDetails.targetCalories?:0.0).toDouble()
        val sleepDuration = calPercentage(healthLog.sleepDuration?:0.0, userDetails.targetSleepHours?:0.0).toDouble()
        val waterIntake = calPercentage(healthLog.waterIntake?:0.0, userDetails.targetWaterIntake?:0.0).toDouble()
        val exerciseTime=calPercentage(healthLog.exerciseTime?:0.0, userDetails.targetExerciseTime?:0.0).toDouble()
        val totalProgress = calculateTotalProgress(caloriesConsumed,sleepDuration,waterIntake,exerciseTime)

        return totalProgress;

    }



    private fun calculateTotalProgress(calories:Double,sleepDuration:Double, waterIntake:Double,exerciseTime:Double):Int{
        val result = (calories + sleepDuration + exerciseTime + waterIntake) / 4
        return result.roundToInt()
    }



    private fun calPercentage(value:Double,total:Double): String {
        val result = ((value / total) * 100)
        if(result > 100){
            return "100"
        }
        return "%.1f".format(result)
    }


}