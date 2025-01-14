package com.example.classwork

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }






    fun convertCurrency(view: View){
        val dollarInput = findViewById<EditText>(R.id.dollar_input)
        val outputText = findViewById<TextView>(R.id.output_txt)

        if(dollarInput.text.isNotEmpty()){
            val dollarValue:Float = dollarInput.text.toString().toFloat();
            outputText.text = (dollarValue * 0.85f).toString();
        }else{
            outputText.text = getString(R.string.no_value_msg)
        }




    }



}