package com.example.shapes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class Adapter(private val context: Context,
                    private val dataSource: ArrayList<Figure>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.row, parent, false)


// Get thumbnail element
        val thumbnailImageView = rowView.findViewById(R.id.rowImage) as ImageView

        // Get area element
        val areaTextView = rowView.findViewById(R.id.rowArea) as TextView

// Get detail element
        val detailTextView = rowView.findViewById(R.id.rowDetail) as TextView

        val detailTextView2 = rowView.findViewById(R.id.rowDetail2) as TextView


        // 1
        val figure = getItem(position) as Figure

// 2

        areaTextView.text = "%.3f".format(figure.figureArea)

        detailTextView.text = figure.characteristic
        detailTextView2.text = "%.3f".format(figure.calculateCharacteristic())

        //"%.3f".format(listOfFigures[i].calculateCharacteristic())

// 3
        /*Picasso.with(context).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)

        ImageView image =
        image.setImageResource*/

        if(figure.javaClass.simpleName == "Square"){
            thumbnailImageView.setImageResource(R.drawable.square)
        }
        else if(figure.javaClass.simpleName == "Triangle"){
            thumbnailImageView.setImageResource(R.drawable.triangle)
        }
        else if(figure.javaClass.simpleName == "Circle"){
            thumbnailImageView.setImageResource(R.drawable.checkbox_blank_circle)
        }
        else thumbnailImageView.setImageResource(R.drawable.ic_launcher_foreground)




        return rowView
    }

}
