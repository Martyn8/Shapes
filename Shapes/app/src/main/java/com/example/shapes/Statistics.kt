package com.example.shapes

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Statistics : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        /*    var args = intent.getBundleExtra("BUNDLE")
        var list = args!!.getSerializable("LIST")*/

        //var numListIntent = intent.getIntegerArrayListExtra("NUMLIST")
        //var areaListIntent = intent.getDoubleArrayExtra("AREALIST")
        //var charListIntent = intent.getDoubleArrayExtra("CHARLIST")


        /*
        pobranie zmiennych z innej aktywności
         */
        val bundle: Bundle = intent.extras!!

        val numListIntent = bundle.getIntegerArrayList("NUMLIST")
        val areaListIntent = bundle.getIntegerArrayList("AREALIST")
        val charListIntent = bundle.getIntegerArrayList("CHARLIST")


        /*
        Number of figures
         */
        val numTriangle = findViewById<TextView>(R.id.numTriangle)
        val numCircle = findViewById<TextView>(R.id.numCircle)
        val numSquare = findViewById<TextView>(R.id.numSquare)


        /*
        Total area
        */
        val areaTriangle = findViewById<TextView>(R.id.areaTriangle)
        val areaCircle = findViewById<TextView>(R.id.areaCircle)
        val areaSquare = findViewById<TextView>(R.id.areaSquare)


        /*
        Total characteristics value
        */
        val charTriangle = findViewById<TextView>(R.id.charTriangle)
        val charCircle = findViewById<TextView>(R.id.charCircle)
        val charSquare = findViewById<TextView>(R.id.charSquare)


        /*
        Wypełnienie pól tekstwowych wartościami
        W zależności od tego czy ilość danej figury wynosi 0 czu nie
         */
        when (numListIntent?.get(0)) {
            0 -> {
                numTriangle.text = "0"
                areaTriangle.text = "0"
                charTriangle.text = "0"
            }
            else -> {
                numTriangle.text = numListIntent?.get(0).toString()
                areaTriangle.text = "%.3f".format(areaListIntent!![0])
                charTriangle.text = "%.3f".format(charListIntent!![0])
            }
        }


        when (numListIntent?.get(1)) {
            0 -> {
                numCircle.text = "0"
                areaCircle.text = "0"
                charCircle.text = "0"
            }
            else -> {
                numCircle.text = numListIntent?.get(1).toString()
                areaCircle.text = "%.3f".format(areaListIntent!![1])
                charCircle.text = "%.3f".format(charListIntent!![1])
            }
        }

        when (numListIntent?.get(2)) {
            0 -> {
                numSquare.text = "0"
                areaSquare.text = "0"
                charSquare.text = "0"
            }
            else -> {
                numSquare.text = numListIntent?.get(2).toString()
                areaSquare.text = "%.3f".format(areaListIntent!![2])
                charSquare.text = "%.3f".format(charListIntent!![2])
            }
        }


        //to nie działa
        // areaSquare.text = areaListIntent?.get(2).toString()

        //nope
//        charTriangle.text = charListIntent?.get(0).toString()
//        charCircle.text = charListIntent?.get(1).toString()
//        charSquare.text = charListIntent?.get(2).toString()

    }

}