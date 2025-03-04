package com.example.classwork

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.classwork.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var _binding:ActivityMainBinding
    private lateinit var receiver: BroadcastReceiver
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        configureReceiver()


        _binding.button.setOnClickListener(this::handleClick)




        ViewCompat.setOnApplyWindowInsetsListener(_binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun configureReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.classwork")
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED")
        receiver = MyReceiver()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
            }
    }

    private fun handleClick(view: View){
        val i = Intent()
        i.action = "com.example.classwork"
        i.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        sendBroadcast(i)

    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }









}