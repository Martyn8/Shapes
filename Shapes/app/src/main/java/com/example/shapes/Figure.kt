package com.example.shapes

import android.os.Parcelable
import java.io.Serializable

/*
Klasa abstrakcyjna Figura
 */
abstract class Figure (val size: Double) : Serializable {
    abstract val figureArea: Double
    abstract val characteristic: String

    abstract fun calculateArea(): Double

    abstract fun calculateCharacteristic(): Double
}