package com.example.shapes

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.slider.RangeSlider

class Settings : AppCompatActivity() {

    companion object {
        const val FIG_NUMBER = "NUMBER OF FIGURES"
        const val RANGE_BEG = "START VALUE"
        const val RANGE_END = "END VALUE"
    }

    /*
    inicjalizacja zmiennych
     */
    var beg: Float = 0.0f

    var end: Float = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val broadcast_reciever = object : BroadcastReceiver() {

            override fun onReceive(arg0: Context, intent: Intent) {
                val action = intent.action
                if (action == "finish_activity") {
                    finish()
                    // DO WHATEVER YOU WANT.
                }
            }
        }
        registerReceiver(broadcast_reciever, IntentFilter("finish_activity"))



        val apply = findViewById<Button>(R.id.applySettings)

        val rangeSlider: RangeSlider = findViewById(R.id.rangeSlider)

        //czytanie wartości ze slidera
        rangeSlider.addOnChangeListener { slider, value, fromUser ->
            val values = slider.values //lista zawierająca wartość początkową i końcową
            rangeSlider.setMinSeparationValue(100.0f)
            beg = values[0]
            end = values[1]

        }

        //przycisk akceptowania zmian
        apply.setOnClickListener {
            val figNumber = findViewById<TextView>(R.id.textFigNum).text.toString().toInt()
            val begVal = beg.toDouble()
            val endVal = end.toDouble()
            if (begVal == endVal) {
                Toast.makeText(
                    this,
                    "Start and end of the range cannot be the same!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(FIG_NUMBER, figNumber)
                intent.putExtra(RANGE_BEG, begVal)
                intent.putExtra(RANGE_END, endVal)
                //startActivity(intent)
                setResult(Activity.RESULT_OK, intent)

                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)

        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}