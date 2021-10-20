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

fun main(){

    println("Hello world!")


    val numberOfFigures = (1..10).random()

    println("numberOfFigures $numberOfFigures")


    val listOfFigures: MutableList<Figure> = mutableListOf()


    for (i in 0 until numberOfFigures){

        when ((1..3).random()){
            1->{listOfFigures.add(Circle(Random.nextDouble(from = 0.0, until = 1.0)))}
            2->{listOfFigures.add(Square(Random.nextDouble(from = 0.0, until = 1.0)))}
            3->{listOfFigures.add(Triangle(Random.nextDouble(from = 0.0, until = 1.0)))}
            else -> println("no such number")
        }

        println("${listOfFigures[i].javaClass.simpleName} " +
                "size: " + "%.3f".format(listOfFigures[i].size) +
                ", area: " + "%.3f".format(listOfFigures[i].figureArea) +
                ", ${listOfFigures[i].characteristic}: " + "%.3f".format(listOfFigures[i].calculateCharacteristic()))
    }

}
