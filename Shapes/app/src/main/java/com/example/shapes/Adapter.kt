package com.example.shapes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.shapes.figures.Figure
import java.util.*

/*
Adapter
Zwraca gotowy wiersz dla listy figur
 */
class Adapter(
    context: Context,
    private val dataSource: ArrayList<Figure>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /*
    Nadpisanie funkcji
     */
    //Pobiera ilośc elementów do wyświetlenia
    override fun getCount(): Int {
        return dataSource.size
    }

    //zwraca element do wyświetlenia w danym miejscu
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //zwraca unikalne ID dla każdego wiersza listy
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //tworzy wiersz listy
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = inflater.inflate(R.layout.row, parent, false)

        //Pobranie referencji obrazu
        val thumbnailImageView = rowView.findViewById(R.id.rowImage) as ImageView

        //pobranie referencji na pole figury
        val areaTextView = rowView.findViewById(R.id.rowArea) as TextView

        //pobranie danej charakterystycznej
        //nazwa
        val detailTextView = rowView.findViewById(R.id.rowDetail) as TextView

        //wartość danej
        val detailTextView2 = rowView.findViewById(R.id.rowDetail2) as TextView

        //pobranie danej figury
        val figure = getItem(position) as Figure

        /*
        wypełnienie wiersza odpowiendnimi danymi o figurze
        */
        areaTextView.text = "%.3f".format(figure.figureArea)

        detailTextView.text = figure.characteristic
        detailTextView2.text = "%.3f".format(figure.calculateCharacteristic())

        if (figure.javaClass.simpleName == "Square") {
            thumbnailImageView.setImageResource(R.drawable.square)
        } else if (figure.javaClass.simpleName == "Triangle") {
            thumbnailImageView.setImageResource(R.drawable.triangle)
        } else if (figure.javaClass.simpleName == "Circle") {
            thumbnailImageView.setImageResource(R.drawable.checkbox_blank_circle)
        } else thumbnailImageView.setImageResource(R.drawable.ic_launcher_foreground)

        return rowView
    }

}
