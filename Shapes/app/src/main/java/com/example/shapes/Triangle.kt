package com.example.shapes

import kotlin.math.sqrt


class Triangle(size:Double) : Figure(size)
{
    override val figureArea: Double = calculateArea()

    override val characteristic: String = "height"

    override fun calculateArea():Double {
        return (size*size* sqrt(3.0))/4
    }

    override fun calculateCharacteristic(): Double {
        return (size* sqrt(3.0))/2
    }
}