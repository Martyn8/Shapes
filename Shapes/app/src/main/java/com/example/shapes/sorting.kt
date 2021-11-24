package com.example.shapes

 class Sorting (var listOfFigures: MutableList<Figure>) {

      var shapeAscending = true
      var areaAscending = true
     var featureAscending = true
     lateinit var shapeOrder : String
     lateinit var areaOrder : String
     lateinit var featureOrder : String

    /*
    Funkcje sortujące po wartościach nagłówków listy figur
     */
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