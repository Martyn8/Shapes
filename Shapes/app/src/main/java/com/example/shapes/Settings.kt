package com.example.shapes

import android.content.Intent
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

    var end: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

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
            val begg = beg.toDouble()
            val endd = end.toDouble()
            if (begg == endd) {
                Toast.makeText(
                    this,
                    "Start and end of the range cannot be the same!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(FIG_NUMBER, figNumber)
                intent.putExtra(RANGE_BEG, begg)
                intent.putExtra(RANGE_END, endd)
                startActivity(intent)
            }
        }
    }
}