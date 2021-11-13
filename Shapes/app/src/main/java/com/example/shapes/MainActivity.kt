package com.example.shapes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    val listOfFigures: MutableList<Figure> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numberOfFigures: Int
        val rangeFrom: Double
        val rangeTo: Double

        //pobranie zmiennych z innej aktywności
        val bundle: Bundle? = intent.extras

        /*
        Sprawdzenie czy bundle istnieje
        w celu zainicjalizowania zmiennych w zalezności od tego czy
        jest to pierwsze uruchomienie aktywości czy kolejne po wybraniu ustawień
         */
        if (bundle == null) {
            numberOfFigures = (1..25).random()
            rangeFrom = 0.0
            rangeTo = 1.0
        } else {
            numberOfFigures = bundle.getInt(Settings.FIG_NUMBER)
            rangeFrom = bundle.getDouble(Settings.RANGE_BEG)
            rangeTo = bundle.getDouble(Settings.RANGE_END)
        }

        val listOfFigures: ArrayList<Figure> =
            randomFigures(numberOfFigures, rangeFrom, rangeTo)  //lista figur

        val listView: ListView = findViewById(R.id.listView)

        val adapter = Adapter(
            this,
            listOfFigures
        )  //adapter dla ListView, otrzymuje listę figur do wyświetlenia
        listView.adapter = adapter

    }

    /*
    Menu opcji w AppBar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
                startActivity(intent)
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


        //val  = int//(1..25).random()  //ilość N figur do wylosowania

        println("numberOfFigures $numberOfFigures")


        //val listOfFigures: MutableList<Figure> = mutableListOf()    //lista figur


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
}
