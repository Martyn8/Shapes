package com.example.shapes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Settings : AppCompatActivity() {

    companion object {
        const val FIG_NUMBER = "NUMBER OF FIGURES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)



        val apply = findViewById<Button>(R.id.applySettings)


        apply.setOnClickListener{
            val figNumber = findViewById<TextView>(R.id.textFigNum).text.toString().toInt()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(FIG_NUMBER, figNumber)
            startActivity(intent)
        }

    }



}