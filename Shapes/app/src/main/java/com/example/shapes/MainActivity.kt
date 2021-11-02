package com.example.shapes

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random




class MainActivity : AppCompatActivity() {

    //private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listOfFigures: ArrayList<Figure> = randomFigures()



        val listView: ListView = findViewById(R.id.listView)

        //Adapter addList = new Adapter(this, listOfFigures)

        val adapter = Adapter(this, listOfFigures)
        listView.adapter = adapter

        //adapter.addAll(listOfFigures)

        //addList()
    }

    //var row: TableRow = findViewById<View>(android.R.id.display_row) as TableRow


    fun addList() {

    }

    fun  randomFigures(): ArrayList<Figure> {
        println("Hello world!")


        val numberOfFigures = (1..10).random()  //ilość N figur do wylosowania

        println("numberOfFigures $numberOfFigures")


        val listOfFigures: MutableList<Figure> = mutableListOf()    //lista figur


        for (i in 0 until numberOfFigures) {

            when ((1..3).random()) {    //losowanie liczby 1 - 3 na podstawie której wybierana jest figura
                1 -> {
                    listOfFigures.add(Circle(Random.nextDouble(from = 0.0, until = 1.0)))   //dodanie Koła do listy o losowym promieniu z zakresu 0 - 1
                }
                2 -> {
                    listOfFigures.add(Square(Random.nextDouble(from = 0.0, until = 1.0)))   //dodanie Kwadratu do listy o losowym boku z zakresu 0 - 1
                }
                3 -> {
                    listOfFigures.add(Triangle(Random.nextDouble(from = 0.0, until = 1.0)))   //dodanie Trójkąta do listy o losowym boku z zakresu 0 - 1
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

        return listOfFigures
    }


}
/*
fun main() {

    println("Hello world!")


    val numberOfFigures = (1..10).random()  //ilość N figur do wylosowania

    println("numberOfFigures $numberOfFigures")


    val listOfFigures: MutableList<Figure> = mutableListOf()    //lista figur


    for (i in 0 until numberOfFigures) {

        when ((1..3).random()) {    //losowanie liczby 1 - 3 na podstawie której wybierana jest figura
            1 -> {
                listOfFigures.add(Circle(Random.nextDouble(from = 0.0, until = 1.0)))   //dodanie Koła do listy o losowym promieniu z zakresu 0 - 1
            }
            2 -> {
                listOfFigures.add(Square(Random.nextDouble(from = 0.0, until = 1.0)))   //dodanie Kwadratu do listy o losowym boku z zakresu 0 - 1
            }
            3 -> {
                listOfFigures.add(Triangle(Random.nextDouble(from = 0.0, until = 1.0)))   //dodanie Trójkąta do listy o losowym boku z zakresu 0 - 1
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

}*/
