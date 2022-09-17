package com.jogogojco.inlupp1


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.text.DecimalFormat

enum class CalculatorModes{
    None,Addition,Subtraction,Multiplication,Division
}
class MainActivity : AppCompatActivity() {
    var lastButtonWasMode = false //keep track if the last button pressed was +,-,/,* or =
    var currentMode = CalculatorModes.None //stores the current mode from CalculatorModes enum
    var labelString = "" //a string that stores the total entered
    var savedNum = 0 //holds the value entered from user


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCalculator()

    }
    //method to initialize all buttons, make them clickable and connect what will happen when clicked
    fun setupCalculator(){
        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val buttonPlus = findViewById<Button>(R.id.buttonPlus)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonClear = findViewById<Button>(R.id.buttonC)

        val allButtons = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9)
        for (i in allButtons.indices){
            allButtons[i].setOnClickListener { didPressNumber(i) }
        }
        buttonPlus.setOnClickListener { changeMode(CalculatorModes.Addition) }
        buttonMinus.setOnClickListener { changeMode(CalculatorModes.Subtraction) }
        buttonMultiply.setOnClickListener { changeMode(CalculatorModes.Multiplication) }
        buttonDivide.setOnClickListener { changeMode(CalculatorModes.Division) }
        buttonEquals.setOnClickListener { didPressEquals() }
        buttonClear.setOnClickListener { didPressClear() }
    }

    //method to store a pressed number to labelString and update the display text
    private fun didPressNumber(num:Int){
        val strVal = num.toString()

        if (lastButtonWasMode){
            lastButtonWasMode = false
            labelString = "0"
        }

        labelString = "$labelString$strVal"
        updateText()

        Log.i("labelString =: ", labelString)
    }

    //method to update the displayed text based on users input.
    private fun updateText(){
        var textDisplay = findViewById<TextView>(R.id.display)

        if (labelString.length > 8){
            didPressClear()
            textDisplay.text = "Too Big"
            return
        }

        //these two lines are used so that the user can't type in multiple zeros on the display
        //before anything else has been touched.
        val labelInt = labelString.toInt()
        labelString = labelInt.toString()

        if (currentMode == CalculatorModes.None){
            savedNum = labelInt //store current input so savedNum if no mode button has been pressed
        }
        val df = DecimalFormat("#,###") //separate long numbers with comma for readability

        textDisplay.text = df.format(labelInt)
    }
    private fun changeMode(modes: CalculatorModes){
        if (savedNum == 0){
            return //stop running method
        }
        currentMode = modes
        lastButtonWasMode = true
    }
    //method to perform calculations based on numbers and modes from user input
    private fun didPressEquals(){
        if (lastButtonWasMode){
            return
        }

        val labelInt = labelString.toInt() //convert to Int to be able to perform calculation

        when(currentMode){
            CalculatorModes.Addition -> savedNum += labelInt
            CalculatorModes.Subtraction -> savedNum -= labelInt
            CalculatorModes.Multiplication -> savedNum *= labelInt
            CalculatorModes.Division -> savedNum /= labelInt
            CalculatorModes.None -> return
        }

        currentMode = CalculatorModes.None
        labelString = "$savedNum"
        updateText()
        lastButtonWasMode = true

    }
    private fun didPressClear(){
        lastButtonWasMode = false
        currentMode = CalculatorModes.None
        labelString = ""
        savedNum = 0
        var textDisplay = findViewById<TextView>(R.id.display)
        textDisplay.text = "0"
    }
}