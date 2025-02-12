package com.example.classwork

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton private constructor(
    context:Context,
    private var requestQueue:RequestQueue = Volley.newRequestQueue(context)

){
    companion object{
        @Volatile private var instance:VolleySingleton?=null;

        fun getInstance(context:Context): VolleySingleton? {
            instance ?: synchronized(this){
                instance ?: VolleySingleton(context).also { instance = it }

            }
            return instance;
        }

    }


    fun getRequestQueue():RequestQueue{
        return requestQueue
    }



}