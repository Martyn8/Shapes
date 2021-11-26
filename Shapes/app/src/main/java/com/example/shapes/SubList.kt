package com.example.shapes

import com.example.shapes.figures.Figure

/*
Klasa z metodami dzielącymi listę figur
 */
class SubList {

    //tworzy listę zawierającą ilość figur danego typu
     fun figNum(listOfFigures: MutableList<Figure>): ArrayList<Int> {
        //podział listy figur na 3 listy składające się z danego typu figury
        val (triangle, rest) = listOfFigures.partition { it.javaClass.simpleName == "Triangle" }
        val (square, circle) = rest.partition { it.javaClass.simpleName == "Square" }

        val numtriangle = triangle.count()
        val numsquare = square.count()
        val numcircle = circle.count()

        val numList = arrayListOf<Int>()

        numList.addAll(listOf(numtriangle, numcircle, numsquare))

        return numList
    }

    //tworzy listę zawierającą sumę pól danego typu figur
     fun figArea(listOfFigures: MutableList<Figure>): ArrayList<Double> {
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

    //tworzy listę zawierającą sumę cech charatkerystycznych danego typu figur
     fun figChar(listOfFigures: MutableList<Figure>): ArrayList<Double> {
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