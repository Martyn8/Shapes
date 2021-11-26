package com.example.shapes.sorting

import com.example.shapes.figures.Figure

object Sorting{

     var shapeAscending : Boolean = true
     var areaAscending : Boolean = true
     var featureAscending : Boolean = true
     var shapeOrder : String = "ascending"
     var areaOrder : String ="ascending"
     var featureOrder : String = "ascending"

    fun sortShape(list: MutableList<Figure>): MutableList<Figure> {
        var listOfFigures = list

        println(list)
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

    fun sortArea(list: MutableList<Figure>): MutableList<Figure> {
        var listOfFigures = list

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

    fun sortFeature(list: MutableList<Figure>): MutableList<Figure> {
        var listOfFigures = list

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