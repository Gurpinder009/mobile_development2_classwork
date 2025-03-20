package mobile.dev.androidfinalproject.utilities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mobile.dev.androidfinalproject.databinding.HealthLogDailyReportCardBinding

class HealthLogRecyclerViewAdapter(
) :
    RecyclerView.Adapter<HealthLogRecyclerViewAdapter.HealthLogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthLogViewHolder {
        val binding = HealthLogDailyReportCardBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return HealthLogViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return 5
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HealthLogViewHolder, position: Int) {
        holder.binding.dayTitle.text = "this si working"
        holder.binding.progressBar.progress = 34;
        holder.binding.progressValue.text = "34%"
    }
    inner  class HealthLogViewHolder(val binding: HealthLogDailyReportCardBinding) : RecyclerView.ViewHolder(binding.root)
}