package com.example.classwork

import android.annotation.SuppressLint
import android.icu.util.CurrencyAmount
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.jetbrains.annotations.NotNull
import java.text.NumberFormat

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener {

    //declaring properties
    private lateinit var billAmountEditText:EditText
    private lateinit var buttonUp: Button
    private lateinit var buttonDown: Button
    private lateinit var tipPercentage:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //initializing the properties
         billAmountEditText = findViewById(R.id.bill_amount_value)
         buttonUp = findViewById(R.id.percent_up_btn)
         buttonDown = findViewById(R.id.percent_down_btn)
            tipPercentage = findViewById(R.id.tip_percentage_value)



        // setting listeners to properties
        buttonUp.setOnClickListener(this::increasePercentage)
        buttonDown.setOnClickListener(this::decreasePercentage)
        billAmountEditText.setOnEditorActionListener(this)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("tipPercentage", tipPercentage.text.toString() )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tipPercentage.text = savedInstanceState.getString("tipPercentage",tipPercentage.text.toString())
        calTipAndTotal()
    }


    //to handle increase percentage button
    @SuppressLint("SetTextI18n")
    fun increasePercentage(view:View){
        var percentage = tipPercentage.text.toString().removeSuffix("%").toInt()
        percentage += 1;
        tipPercentage.text = "$percentage%"
        calTipAndTotal()
    }

    //to handle decrease percentage button
    @SuppressLint("SetTextI18n")
    fun decreasePercentage(view:View){
        var percentage = tipPercentage.text.toString().removeSuffix("%").toInt()
        percentage -= 1;
        tipPercentage.text = "$percentage%"
        calTipAndTotal()
    }



    // for calculating tip and total values
    private fun calTipAndTotal(){
        var billAmount= 0.0;

        // for checking whether given value is valid or not
        if(billAmountEditText.text.toString() != "") {
            billAmount=billAmountEditText.text.toString().toDouble();

        }

        val percentage = tipPercentage.text.toString().removeSuffix("%").toInt()


        //calculating values
        val tipAmount:Double = billAmount * (percentage.toDouble() / 100 )
        val totalAmount:Double = billAmount + tipAmount



        // displaying the calculated values
        val currencyAmount:NumberFormat = NumberFormat.getCurrencyInstance()
        val formattedTipValue = currencyAmount.format(tipAmount)
        val formattedTotalValue = currencyAmount.format(totalAmount)
        findViewById<TextView>(R.id.cal_tip_value).text = formattedTipValue
        findViewById<TextView>(R.id.cal_total_value).text = formattedTotalValue
        }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        calTipAndTotal()
        return true
    }

}






