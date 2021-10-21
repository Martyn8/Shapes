package com.example.shapes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

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

}
