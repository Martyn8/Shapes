package com.example.shapes

import kotlin.math.PI

class Circle(size: Double) : Figure(size) {
    override val figureArea: Double = calculateArea()

    override val characteristic: String = "diameter"

    override fun calculateArea():Double {
        return PI*size*size
    }

    override fun calculateCharacteristic(): Double {
        return 2*size
    }
}