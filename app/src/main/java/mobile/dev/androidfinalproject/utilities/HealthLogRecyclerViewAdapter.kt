package mobile.dev.androidfinalproject.utilities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mobile.dev.androidfinalproject.databinding.HealthLogDailyReportCardBinding
import mobile.dev.androidfinalproject.models.HealthLogsModel

class HealthLogRecyclerViewAdapter(
    private val healthLogs:List<HealthLogsModel>,

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

        holder.binding.dayTitle.text = "Day: ${this.itemCount-position}  (${healthLogs[position].createdAt})"
        holder.binding.progressBar.progress = 34;
        holder.binding.progressValue.text = "34%"
    }
    inner  class HealthLogViewHolder(val binding: HealthLogDailyReportCardBinding) : RecyclerView.ViewHolder(binding.root)
}