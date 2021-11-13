package com.example.shapes

/*
Klasa abstrakcyjna Figura
 */
abstract class Figure (val size: Double)   {
    abstract val figureArea: Double
    abstract val characteristic: String

    abstract fun calculateArea(): Double

    abstract fun calculateCharacteristic(): Double
}