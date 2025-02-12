package com.example.classwork

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val movieList:MutableList<Movie> = mutableListOf()
    private lateinit var recyclerView:RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

         recyclerView= findViewById(R.id.recycler_view)
         recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = MovieRecyclerViewAdaptor(baseContext,movieList)
//
         fetchMovies()












        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun fetchMovies(){
        val url =
        "https://api.themoviedb.org/3/movie/popular?api_key=f216aa28291dcb2195fd3473f9515674&language=en-US&page=1"
        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
              try {
                  val jsonData: JSONArray = response.getJSONArray("results");
                  for (i in 0..jsonData.length()-1) {
                      val jsonObject: JSONObject = jsonData.getJSONObject(i);
                      val title = jsonObject.getString("title")
                      val rating = jsonObject.getDouble("vote_average")
                      val overview = jsonObject.getString("overview")
                      val poster = "https://image.tmdb.org/t/p/w500" + jsonObject.getString("poster_path")

                      this.movieList.add(Movie(title,poster,rating.toString(),overview))


                  }
                  recyclerView.adapter = MovieRecyclerViewAdaptor(baseContext,this.movieList)



              }catch (ex:JSONException){
                  Log.i("adf", "fetchMovies: " + ex.message)

                    ex.printStackTrace()
              }
            },
            { error ->
                Toast.makeText(baseContext,error.message,Toast.LENGTH_SHORT).show()
            })


         VolleySingleton.getInstance(this)?.getRequestQueue()?.add(jsonRequest)


    }









}