package com.example.shapes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var listOfFigures: MutableList<Figure> = mutableListOf()
    private var shapeAscending = true
    private var areaAscending = true
    private var featureAscending = true
    private var shapeOrder = ""
    private var areaOrder = ""
    private var featureOrder = ""
    var numberOfFigures = 5
    var rangeFrom = 0.0
    var rangeTo = 1.0
    var resultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val intentfinish = Intent("finish_activity")
        sendBroadcast(intentfinish)

        val listView: ListView = findViewById(R.id.listView)

        //początkowa lista figur
        listOfFigures =
            randomFigures(numberOfFigures, rangeFrom, rangeTo)  //lista figur


        val adapter = Adapter(
            this,
            listOfFigures as ArrayList<Figure>
        )  //adapter dla ListView, otrzymuje listę figur do wyświetlenia
        listView.adapter = adapter



        //pobranie zmiennych z aktywności Settings
        resultContract =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    // Handle the Intent
                    val num = intent!!.getIntExtra(Settings.FIG_NUMBER, 0)
                    numberOfFigures = num

                    Toast.makeText(this, "result", Toast.LENGTH_SHORT).show()
                    listOfFigures.clear()
                    listOfFigures =
                        randomFigures(numberOfFigures, rangeFrom, rangeTo)  //lista figur


                    val adapter = Adapter(
                        this,
                        listOfFigures as ArrayList<Figure>
                    )  //adapter dla ListView, otrzymuje listę figur do wyświetlenia
                    listView.adapter = adapter

                } else if (result.resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "no result", Toast.LENGTH_SHORT).show()
                }
            }


        /*
        Listener dla każdego nagłówka listy figur w celu sortowania
         */
        val shapeButton = findViewById<TextView>(R.id.headerShape)
        val areaButton = findViewById<TextView>(R.id.headerArea)
        val featureButton = findViewById<TextView>(R.id.headerFeature)

        shapeButton.setOnClickListener {
            listOfFigures = sortShape()
            val adapter = Adapter(this, listOfFigures as java.util.ArrayList<Figure>)
            listView.adapter = adapter

            Toast.makeText(this, "Sorted by shape $shapeOrder", Toast.LENGTH_SHORT).show()
        }

/*        shapeButton.setOnClickListener {
            listOfFigures = Sorting(listOfFigures).sortShape()
            val adapter = Adapter(this, listOfFigures as java.util.ArrayList<Figure>)
            listView.adapter = adapter

            Toast.makeText(this, "Sorted by shape ${Sorting(listOfFigures).shapeOrder}", Toast.LENGTH_SHORT).show()
        }*/


        areaButton.setOnClickListener {
            listOfFigures = sortArea()
            val adapter = Adapter(this, listOfFigures as ArrayList<Figure>)
            listView.adapter = adapter
            Toast.makeText(this, "Sorted by area $areaOrder", Toast.LENGTH_SHORT).show()
        }

        featureButton.setOnClickListener {
            listOfFigures = sortFeature()
            val adapter = Adapter(this, listOfFigures as ArrayList<Figure>)
            listView.adapter = adapter
            Toast.makeText(this, "Sorted by feature $featureOrder", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putSerializable("lista", listOfFigures as Serializable)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {


        super.onRestoreInstanceState(savedInstanceState)

        val serializablelist = savedInstanceState.getSerializable("lista")

        listOfFigures = serializablelist as MutableList<Figure>

        val listView: ListView = findViewById(R.id.listView)

        val adapter = Adapter(this, listOfFigures as ArrayList<Figure>)
        listView.adapter = adapter

    }


    /*
    Menu opcji w AppBar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /*
    Elementy menu
    Przechodzenie do innych aktywności w zależności od wybranej opcji
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.about -> {
                val intent = Intent(this, About::class.java)
                val int = 6
                intent.putExtra("int", int)
                startActivity(intent)
            }
            R.id.settings -> {
                val intent = Intent(this, Settings::class.java)
                //startActivity(intent)
                resultContract.launch(intent)

            }
            R.id.stats -> {
                val intent = Intent(this, Statistics::class.java)
/*                val list : ArrayList<Any> = listOfFigures as ArrayList<Any>
                val bundle = Bundle()
                bundle.putSerializable("LIST", list as Serializable)
                intent.putExtra("BUNDLE", bundle)*/

                val numList: ArrayList<Int> = figNum()
                val areaList: ArrayList<Double> = figArea()
                val charList: ArrayList<Double> = figChar()

                intent.putIntegerArrayListExtra("NUMLIST", numList)

                intent.putExtra("AREALIST", areaList)

                intent.putExtra("CHARLIST", charList)

                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    /*
    Funkcja odpowiedzialna za losowanie figur i ich wymiarów
     */
    private fun randomFigures(numberOfFigures: Int, from: Double, to: Double): ArrayList<Figure> {
        println("Hello world!")
        println("numberOfFigures $numberOfFigures")

        for (i in 0 until numberOfFigures) {

            when ((1..3).random()) {    //losowanie liczby 1 - 3 na podstawie której wybierana jest figura
                1 -> {
                    listOfFigures.add(
                        Circle(
                            Random.nextDouble(
                                from = from,
                                until = to
                            )
                        )
                    )   //dodanie Koła do listy o losowym promieniu z zakresu 0 - 1
                }
                2 -> {
                    listOfFigures.add(
                        Square(
                            Random.nextDouble(
                                from = from,
                                until = to
                            )
                        )
                    )   //dodanie Kwadratu do listy o losowym boku z zakresu 0 - 1
                }
                3 -> {
                    listOfFigures.add(
                        Triangle(
                            Random.nextDouble(
                                from = from,
                                until = to
                            )
                        )
                    )   //dodanie Trójkąta do listy o losowym boku z zakresu 0 - 1
                }
                else -> println("no such number")
            }

            //wyświetlenie każdego elementu listy figur
            println(
                "${listOfFigures[i].javaClass.simpleName} " +   //nazwa klasy
                        "size: " + "%.3f".format(listOfFigures[i].size) +   //rozmiar promienia/boku z dokładnością do 3 miejsc po przecinku
                        ", area: " + "%.3f".format(listOfFigures[i].figureArea) +   //pole figury z dokładnością do 3 miejsc po przecinku
                        ", ${listOfFigures[i].characteristic}: " + "%.3f".format(listOfFigures[i].calculateCharacteristic()) //wartość charakteryzująca i jej wymiar z dokładnością do 3 miejsc po przecinku
            )
        }

        return listOfFigures as ArrayList<Figure>
    }

    private fun figNum(): ArrayList<Int> {

        val (triangle, rest) = listOfFigures.partition { it.javaClass.simpleName == "Triangle" }

        val numtriangle = triangle.count()

        val (square, circle) = rest.partition { it.javaClass.simpleName == "Square" }

        val numsquare = square.count()

        val numcircle = circle.count()

        val numList = arrayListOf<Int>()

        numList.addAll(listOf(numtriangle, numcircle, numsquare))

        return numList
    }

    private fun figArea(): ArrayList<Double> {
        val (triangle, rest) = listOfFigures.partition { it.javaClass.simpleName == "Triangle" }

        val (square, circle) = rest.partition { it.javaClass.simpleName == "Square" }

        var areatriangle = 0.0
        var areacircle = 0.0
        var areasquare = 0.0

        triangle.forEach {
            areatriangle += it.figureArea
        }

        circle.forEach {
            areacircle += it.figureArea
        }

        square.forEach {
            areasquare += it.figureArea
        }

        val areaList = arrayListOf<Double>()

        areaList.addAll(listOf(areatriangle, areacircle, areasquare))

        return areaList
    }

    private fun figChar(): ArrayList<Double> {
        val (triangle, rest) = listOfFigures.partition { it.javaClass.simpleName == "Triangle" }

        val (square, circle) = rest.partition { it.javaClass.simpleName == "Square" }


        var chartriangle = 0.0
        var charcircle = 0.0
        var charsquare = 0.0

        triangle.forEach {
            chartriangle += it.calculateCharacteristic()
        }

        circle.forEach {
            charcircle += it.calculateCharacteristic()
        }

        square.forEach {
            charsquare += it.calculateCharacteristic()
        }

        val areaList = arrayListOf<Double>()

        areaList.addAll(listOf(chartriangle, charcircle, charsquare))

        return areaList
    }


    fun sortShape(/*shapeAscending: Boolean, shapeOrder: String,*/): MutableList<Figure> {

/*        var shapeAscending = shapeAscending
        var shapeOrder = shapeOrder*/

        println(listOfFigures)
        if (shapeAscending) {
            listOfFigures = listOfFigures.sortedBy { it.javaClass.simpleName }.toMutableList()
            shapeOrder = "ascending"
        } else {
            listOfFigures =
                listOfFigures.sortedByDescending { it.javaClass.simpleName }.toMutableList()
            shapeOrder = "descending"
        }

        shapeAscending = !shapeAscending
        println(listOfFigures)
        return listOfFigures
    }

    fun sortArea(/*areaAscending: Boolean, areaOrder: String*/): MutableList<Figure> {

/*        var areaAscending = areaAscending
        var areaOrder = areaOrder*/

        if (areaAscending) {
            listOfFigures = listOfFigures.sortedBy { it.figureArea }.toMutableList()
            areaOrder = "ascending"
        } else {
            listOfFigures = listOfFigures.sortedByDescending { it.figureArea }.toMutableList()
            areaOrder = "descending"
        }
        areaAscending = !areaAscending
        return listOfFigures
    }

    fun sortFeature(/*featureAscending: Boolean, featureOrder: String*/): MutableList<Figure> {

/*        var featureAscending = featureAscending
        var featureOrder = featureOrder*/

        if (featureAscending) {
            listOfFigures = listOfFigures.sortedBy { it.calculateCharacteristic() }.toMutableList()
            featureOrder = "ascending"
        } else {
            listOfFigures =
                listOfFigures.sortedByDescending { it.calculateCharacteristic() }.toMutableList()
            featureOrder = "descending"
        }
        featureAscending = !featureAscending
        return listOfFigures
    }
}
