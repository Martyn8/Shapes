package com.example.shapes

import kotlin.math.sqrt

/*
Klasa Kwadrat
 */
class Square(size: Double) : Figure(size) {
    override val figureArea: Double = calculateArea()

    override val characteristic: String = "diagonal"

    override fun calculateArea(): Double {
        return size * size
    }

    override fun calculateCharacteristic(): Double {
        return size * sqrt(2.0)
    }
}