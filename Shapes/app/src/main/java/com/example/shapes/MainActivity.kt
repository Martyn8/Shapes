package com.example.shapes

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.shapes.figures.Circle
import com.example.shapes.figures.Figure
import com.example.shapes.figures.Square
import com.example.shapes.figures.Triangle
import com.example.shapes.sorting.Sorting
import java.io.Serializable
import kotlin.random.Random
import android.widget.LinearLayout




class MainActivity : AppCompatActivity() {

    private var listOfFigures: MutableList<Figure> = mutableListOf()
    private var numberOfFigures = 5
    private var rangeFrom = 0.0
    private var rangeTo = 1.0
    private var resultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentFinish = Intent("finish_activity")
        sendBroadcast(intentFinish)

        val listView: ListView = findViewById(R.id.listView)

        //początkowa lista figur
        listOfFigures =
            randomFigures(numberOfFigures, rangeFrom, rangeTo)

        listAdapter(listView)   //wywołanie funkcji adaptera listy figur

        registerForContextMenu(listView)    //pozwolenie na wyświetlenie menu kontekstowego dla listy figur


        //pobranie zmiennych z aktywności Settings
        resultContract =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data

                    val num = intent!!.getIntExtra(SettingsActivity.FIG_NUMBER, 0)
                    numberOfFigures = num

                    listOfFigures.clear()
                    listOfFigures = randomFigures(numberOfFigures, rangeFrom, rangeTo)

                    listAdapter(listView)   //wywołanie funkcji adaptera listy figur

                } else if (result.resultCode == Activity.RESULT_CANCELED) {
                    println("no options selected")
                }
            }


        /*
        Listener dla każdego nagłówka listy figur w celu sortowania
         */
        val shapeButton = findViewById<TextView>(R.id.headerShape)
        val areaButton = findViewById<TextView>(R.id.headerArea)
        val featureButton = findViewById<TextView>(R.id.headerFeature)

        shapeButton.setOnClickListener {
            listOfFigures = Sorting.sortShape(listOfFigures)
            listAdapter(listView)   //wywołanie funkcji adaptera listy figur
            Toast.makeText(this, "Sorted by shape ${Sorting.shapeOrder}", Toast.LENGTH_SHORT).show()
        }

        areaButton.setOnClickListener {
            listOfFigures = Sorting.sortArea(listOfFigures)
            listAdapter(listView)   //wywołanie funkcji adaptera listy figur
            Toast.makeText(this, "Sorted by area ${Sorting.areaOrder}", Toast.LENGTH_SHORT).show()
        }

        featureButton.setOnClickListener {
            listOfFigures = Sorting.sortFeature(listOfFigures)
            listAdapter(listView)   //wywołanie funkcji adaptera listy figur
            Toast.makeText(this, "Sorted by feature ${Sorting.featureOrder}", Toast.LENGTH_SHORT)
                .show()
        }


        /*
        Listener dla przycisków menu dolnego
        */
        val deleteListButton = findViewById<TextView>(R.id.delete_list)
        val addElementButton = findViewById<TextView>(R.id.add_element)

        deleteListButton.setOnClickListener {

            val alert = AlertDialog.Builder(this)
            alert.setTitle("Warning!")
            alert.setMessage("Do you really want to delete the list and create new one?")

            alert.setPositiveButton("Yes") { dialog, which ->
                listOfFigures.removeAll(listOfFigures)
                listOfFigures =
                    randomFigures(numberOfFigures, rangeFrom, rangeTo)

                listView.invalidateViews()  //odświeżenie listView

                Toast.makeText(this, "New list was created", Toast.LENGTH_SHORT).show()
            }

            alert.setNegativeButton("No", null)

            alert.show()
        }

        addElementButton.setOnClickListener {
            randomFigures(1, rangeFrom, rangeTo)
            listAdapter(listView)   //wywołanie funkcji adaptera listy figur

            Toast.makeText(this, "Added new element", Toast.LENGTH_SHORT).show()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////      Zachowanie stanu aplikacji (pamięci)
    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("lista", listOfFigures as Serializable)
        outState.putInt("numberOfFigures", numberOfFigures)
        outState.putDouble("begRange", rangeFrom)
        outState.putDouble("endRange", rangeTo)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val serializablelist = savedInstanceState.getSerializable("lista")

        listOfFigures = serializablelist as MutableList<Figure>
        numberOfFigures = savedInstanceState.getInt("numberOfFigures")
        rangeFrom = savedInstanceState.getDouble("begRange")
        rangeTo = savedInstanceState.getDouble("endRange")

        val listView: ListView = findViewById(R.id.listView)
        listAdapter(listView) //wywołanie funkcji adaptera listy figur
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////      Menu opcji w AppBar
    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    /*
    Elementy menu
    Przechodzenie do innych aktywności w zależności od wybranej opcji
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                val int = 6
                intent.putExtra("int", int)
                startActivity(intent)
            }
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                //startActivity(intent)
                resultContract.launch(intent)

            }
            R.id.stats -> {
                val intent = Intent(this, StatisticsActivity::class.java)

                val numList: ArrayList<Int> = SubList().figNum(listOfFigures)
                val areaList: ArrayList<Double> = SubList().figArea(listOfFigures)
                val charList: ArrayList<Double> = SubList().figChar(listOfFigures)

                intent.putIntegerArrayListExtra("NUMLIST", numList)
                intent.putExtra("AREALIST", areaList)
                intent.putExtra("CHARLIST", charList)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////      Menu kontekstowe
    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?,
    ) {
        menuInflater.inflate(R.menu.floating_context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    /*
    Elementy menu
    Wybór opcji kopiowania lub usunięcia wybranego elementu
    */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterContextMenuInfo
        val listView: ListView = findViewById(R.id.listView)

        when (item.itemId) {
            R.id.floating_menu_copy -> {
                val figure = listOfFigures[info.id.toInt()]

                listOfFigures.add(figure)

                listAdapter(listView)   //wywołanie funkcji adaptera listy figur

                Toast.makeText(applicationContext, "Element copied", Toast.LENGTH_SHORT).show()
            }
            R.id.floating_menu_edit -> {

                var figure = listOfFigures[info.id.toInt()]

                val view = layoutInflater.inflate(R.layout.edit_dialog, null)
                val width = LinearLayout.LayoutParams.WRAP_CONTENT
                val height = LinearLayout.LayoutParams.WRAP_CONTENT
                val window = PopupWindow(view, width, height, true)

                val shapelistview = view.findViewById<ListView>(R.id.shapesList)

                val array : Array<String> = arrayOf("Triangle", "Circle", "Square")

                val arrayAdapter : ArrayAdapter<String> = ArrayAdapter(
                    this, android.R.layout.simple_list_item_1, array
                )

                shapelistview.adapter = arrayAdapter

                window.showAtLocation(listView, Gravity.CENTER, 0, 0)

                var selectedItem = -1

                shapelistview.setOnItemClickListener{ adapterView, view, i , l ->
                    selectedItem = i
                }

                val editBtn = view.findViewById<Button>(R.id.editAccept)

                editBtn.setOnClickListener {
                    val newSize = view.findViewById<EditText>(R.id.newSize)

                    if(newSize.text.isNotEmpty() && selectedItem!=-1){
                        val size = newSize.text.toString().toDouble()

                        when (selectedItem) {
                            0 -> {
                                figure = Triangle(size)
                            }
                            1 -> {
                                figure = Circle(size)
                            }
                            2 -> {
                                figure = Square(size)
                            }
                        }
                        listOfFigures[info.id.toInt()] = figure

                        window.dismiss()

                        //val listView: ListView = findViewById(R.id.listView)
                        listAdapter(listView)
                    }
                    else{
                        Toast.makeText(applicationContext, "Choose figure and new area!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.floating_menu_delete -> {
                listOfFigures.removeAt(info.id.toInt())

                listAdapter(listView)   //wywołanie funkcji adaptera listy figur

                Toast.makeText(applicationContext, "Element deleted", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onContextItemSelected(item)
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////      Funkcja odpowiedzialna za losowanie figur i ich wymiarów
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private fun randomFigures(numberOfFigures: Int, from: Double, to: Double): ArrayList<Figure> {
        println("Hello world!")
        println("numberOfFigures $numberOfFigures")

        for (i in 0 until numberOfFigures) {

            when ((1..3).random()) {    //losowanie liczby 1 - 3 na podstawie której wybierana jest figura
                1 -> {
                    listOfFigures.add(
                        Circle(
                            Random.nextDouble(from = from, until = to))
                    )   //dodanie Koła do listy o losowym promieniu z zakresu 0 - 1
                }
                2 -> {
                    listOfFigures.add(
                        Square(
                            Random.nextDouble(from = from, until = to))
                    )   //dodanie Kwadratu do listy o losowym boku z zakresu 0 - 1
                }
                3 -> {
                    listOfFigures.add(
                        Triangle(Random.nextDouble(from = from,until = to))
                    )   //dodanie Trójkąta do listy o losowym boku z zakresu 0 - 1
                }
                else -> println("no such number")
            }

            //wyświetlenie każdego elementu listy figur w konsoli
            println(
                "${listOfFigures[i].javaClass.simpleName} " +   //nazwa klasy
                        "size: " + "%.3f".format(listOfFigures[i].size) +   //rozmiar promienia/boku z dokładnością do 3 miejsc po przecinku
                        ", area: " + "%.3f".format(listOfFigures[i].figureArea) +   //pole figury z dokładnością do 3 miejsc po przecinku
                        ", ${listOfFigures[i].characteristic}: " + "%.3f".format(listOfFigures[i].calculateCharacteristic()) //wartość charakteryzująca i jej wymiar z dokładnością do 3 miejsc po przecinku
            )
        }
        return listOfFigures as ArrayList<Figure>
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////      Funkcja odpowiedzialna za adapter dla listy figur
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private fun listAdapter(listView: ListView){

        val adapter = Adapter(
            this,
            listOfFigures as ArrayList<Figure>
        )  //adapter dla ListView, otrzymuje listę figur do wyświetlenia
        listView.adapter = adapter
    }
}

