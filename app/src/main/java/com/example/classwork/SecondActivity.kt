package com.example.classwork

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class SecondActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)


        val poster = findViewById<ImageView>(R.id.poster)
        val title = findViewById<TextView>(R.id.title)
        val rating = findViewById<TextView>(R.id.rating)
        val overview = findViewById<TextView>(R.id.overview)

        val bundle:Bundle= intent.extras!!

        title.text = bundle.getString("title")
        rating.text= bundle.getString("rating")
        overview.text = bundle.getString("overview")
        Picasso.get().load(bundle.getString("poster")).into(poster)












        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}