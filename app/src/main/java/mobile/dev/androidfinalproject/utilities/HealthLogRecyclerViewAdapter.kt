package mobile.dev.androidfinalproject.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mobile.dev.androidfinalproject.databinding.HealthLogDailyReportCardBinding

class HealthLogRecyclerViewAdapter(
) :
    RecyclerView.Adapter<HealthLogRecyclerViewAdapter.HealthLogViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthLogViewHolder {
        val _binding = HealthLogDailyReportCardBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return HealthLogViewHolder(_binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HealthLogViewHolder, position: Int) {
        TODO("Not yet implemented")
    }







    inner  class HealthLogViewHolder(private val _binding : HealthLogDailyReportCardBinding) : RecyclerView.ViewHolder(_binding.root) {

    }

}