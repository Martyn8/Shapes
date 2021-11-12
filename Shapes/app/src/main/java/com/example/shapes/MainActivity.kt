package com.example.shapes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //pobranie zmiennych z innej aktywności
        val bundle : Bundle? = intent.extras

        val numberOfFigures : Int
        val rangeFrom : Double
        val rangeTo : Double

        if (bundle == null){
            numberOfFigures = (1..25).random()

            rangeFrom = 0.0

            rangeTo = 1.0

        } else{
            numberOfFigures = bundle.getInt(Settings.FIG_NUMBER)
            rangeFrom = bundle.getDouble(Settings.RANGE_BEG)
            rangeTo = bundle.getDouble(Settings.RANGE_END)
        }


        val listOfFigures: ArrayList<Figure> = randomFigures(numberOfFigures, rangeFrom, rangeTo)  //lista figur


        val listView: ListView = findViewById(R.id.listView)

        val adapter = Adapter(
            this,
            listOfFigures
        )  //adapter dla ListView, otrzymuje listę figur do wyświetlenia
        listView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.about -> {
                val intent = Intent(this, About::class.java)
                startActivity(intent)
            }
            R.id.settings -> {
                val intent = Intent(this, Settings::class.java)
                startActivity(intent)
            }
            R.id.stats -> {
                val intent = Intent(this, Statistics::class.java)
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


        val listOfFigures: MutableList<Figure> = mutableListOf()    //lista figur


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

}
