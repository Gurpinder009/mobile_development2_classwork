package com.example.classwork

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieRecyclerViewAdaptor (
    private val context:Context,
    private val movieList: List<Movie>
): RecyclerView.Adapter<MovieRecyclerViewAdaptor.ViewHolder>() {








    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieRecyclerViewAdaptor.ViewHolder {
        val v:View = LayoutInflater.from(context).inflate(R.layout.card, parent,false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(holder: MovieRecyclerViewAdaptor.ViewHolder, position: Int) {
        val movie:Movie = movieList[position]
        holder.title.text = movie.title;
        holder.rating.text   = movie.rating;
        holder.overview.text = movie.overview;
        Picasso.get().load(movie.poster).into(holder.poster)
        holder.cardView.setOnClickListener{_->run{
            val i = Intent(context, SecondActivity::class.java)
            val bundle = Bundle()
            bundle.putString("poster",movie.poster)
            bundle.putString("title",movie.title)
            bundle.putString("rating",movie.rating)
            bundle.putString("overview",movie.overview)
            i.putExtras(bundle)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }}
    }

    override fun getItemCount(): Int {
        return movieList.size
    }





    class ViewHolder(
        itemView: View,
        val poster:ImageView = itemView.findViewById(R.id.poster),
        val title:TextView = itemView.findViewById(R.id.title),
        val rating:TextView = itemView.findViewById(R.id.rating),
        val overview:TextView = itemView.findViewById(R.id.overview),
        val cardView:CardView = itemView.findViewById(R.id.cardView)
    ) :RecyclerView.ViewHolder(itemView) {

    }

}