package com.example.classwork

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class RecyclerViewAdaptor : RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder>() {



    private val titles = listOf("Chapter One","Chapter two","Chapter three","Chapter four","Chapter five","Chapter six","Chapter seven","Chapter eight");
    private val details = listOf("Item One","Item two","Item three","Item four","Item five","Item six","Item seven","Item eight")
    private val images = listOf(
        R.drawable.android_image_1,
        R.drawable.android_image_2,
        R.drawable.android_image_3,
        R.drawable.android_image_4,
        R.drawable.android_image_5,
        R.drawable.android_image_6,
        R.drawable.android_image_7,
        R.drawable.android_image_8
    )


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val imageView :ImageView = itemView.findViewById(R.id.imageView)
        val textView1 :TextView = itemView.findViewById(R.id.textView1)
        val textView2: TextView = itemView.findViewById(R.id.textView2)

        init {
            itemView.setOnClickListener { view:View->
                val position =  adapterPosition;
                Snackbar.make(view, "Click detected on item $position",Snackbar.LENGTH_SHORT).setAction("action",null).show()
            }
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdaptor.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdaptor.ViewHolder, position: Int) {

        holder.textView1.text = titles[position]
        holder.textView2.text = details[position]
        holder.imageView.setImageResource(images[position])


    }
    override fun getItemCount(): Int {
        return titles.size
    }
}