package com.example.teatro

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class SeleccionButacas : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val seleccionLayout = R.layout.seleccion_butacas
        setContentView(seleccionLayout)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        val numberPicker = findViewById<NumberPicker>(R.id.numPicker)
        val seleccion = findViewById<TextView>(R.id.seleccionadas)
        val txtButaca1 = findViewById<TextView>(R.id.butaca1)
        val txtButaca2 = findViewById<TextView>(R.id.butaca2)

        numberPicker.minValue = 0
        numberPicker.maxValue = 2

        val seats = Array(50){
            false
        }
        val asientos = MainActivity.listaButacas
        val mapButacas = mutableMapOf<Int, Butaca>()

        seleccion.text = String.format(getString(R.string.text_sel) + " %s", numberPicker.value)
        numberPicker.setOnValueChangedListener { _, _, newVal ->
            seleccion.text = String.format(getString(R.string.text_sel) + " %s", newVal)

            if(newVal == 0){
                txtButaca1.text = ""
                txtButaca2.text = ""

                txtButaca2.text = ""
                val bt1 = mapButacas[1]
                if(bt1 != null){
                    val tRow = tableLayout.getChildAt(bt1.fila - 1) as TableRow
                    val img = tRow.getChildAt(bt1.columna - 1)
                    img.alpha = 1F
                }
                val bt2 = mapButacas[2]
                if(bt2 != null){
                    val tRow = tableLayout.getChildAt(bt2.fila - 1) as TableRow
                    val img = tRow.getChildAt(bt2.columna - 1)
                    img.alpha = 1F
                }

                mapButacas.remove(1)
                mapButacas.remove(2)
            }else if(newVal == 1){
                txtButaca2.text = ""
                val bt1 = mapButacas[1]
                if(bt1 == null){
                    txtButaca1.text = getString(R.string.text_butaca1)
                }
                val bt2 = mapButacas[2]
                if(bt2 != null){
                    val tRow = tableLayout.getChildAt(bt2.fila - 1) as TableRow
                    val img = tRow.getChildAt(bt2.columna - 1)
                    img.alpha = 1F
                    mapButacas.remove(2)
                }
            }else if(newVal ==2){
                val bt1 = mapButacas[1]
                if(bt1 == null){
                    txtButaca1.text = getString(R.string.text_butaca1)
                }
                val bt2 = mapButacas[2]
                if(bt2 == null){
                    txtButaca2.text = getString(R.string.text_butaca2)

                }
            }

        }

        for(i in 0..4){
            val row = TableRow(this)
            val paramsRow: TableLayout.LayoutParams =
                TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
            row.layoutParams = paramsRow
            for(j in 1..10){
                val id = (10*i) + j

                val imageView = ImageView(this)
                if(asientos[id - 1].Ocupada){
                    imageView.setImageResource(R.drawable.seat_ocupada2)
                }else{
                    imageView.setImageResource(R.drawable.seat_main2)
                }

                imageView.id = id
                val params: TableRow.LayoutParams =
                    TableRow.LayoutParams(95, 95)
                imageView.layoutParams = params
                seats[imageView.id - 1] = false

                imageView.setOnClickListener{

                    if(!seats[imageView.id - 1]){

                        if(mapButacas.size>=numberPicker.value){
                            Toast.makeText(this, getString(R.string.text_no_sel), Toast.LENGTH_SHORT).show()
                        }else {

                            if(numberPicker.value == 2 && mapButacas.isEmpty()){
                                imageView.alpha = 0.6F
                                val imgView2: ImageView?
                                val b1 = asientos[imageView.id - 1]
                                val b2: Butaca?
                                if(imageView.id%10 == 0){
                                    imgView2 = findViewById(imageView.id - 1)
                                    b2 = asientos[imageView.id - 2]
                                    seats[imageView.id - 2] = true
                                }else{
                                    imgView2 = findViewById(imageView.id + 1)
                                    b2 = asientos[imageView.id]
                                    seats[imageView.id] = true
                                }

                                imgView2.alpha = 0.6F
                                seats[imageView.id - 1] = true


                                mapButacas[1] = b1
                                mapButacas[2] = b2

                                txtButaca1.text = String.format(getString(R.string.text_butaca1) + " " + getString(R.string.text_fila) + " %s, " + getString(R.string.text_columna) + " %s", b1.fila, b1.columna)

                                txtButaca2.text = String.format(getString(R.string.text_butaca2) + " " + getString(R.string.text_fila) + " %s " + getString(R.string.text_columna) + " %s", b2.fila,b2.columna)

                            }else{
                                imageView.alpha = 0.6F
                                seats[imageView.id - 1] = true

                                if(mapButacas.containsKey(1)){
                                    val b2 = asientos[imageView.id - 1]

                                    mapButacas[2] = b2
                                    txtButaca2.text = String.format(getString(R.string.text_butaca2) + " " + getString(R.string.text_fila) + " %s, " + getString(R.string.text_columna) + " %s", b2.fila, b2.columna)
                                }else{
                                    val b1 = asientos[imageView.id - 1]
                                    mapButacas[1] = b1
                                    txtButaca1.text = String.format(getString(R.string.text_butaca1) + " " + getString(R.string.text_fila) + " %s, " + getString(R.string.text_columna) + " %s", b1.fila, b1.columna)
                                }

                            }

                        }
                    }else{
                        imageView.alpha = 1F
                        seats[imageView.id - 1] = false

                        val b = asientos[imageView.id - 1]
                        if (mapButacas[1] == b){
                            mapButacas.remove(1, b)
                            txtButaca1.text = getString(R.string.text_butaca1)
                        }else if(mapButacas[2] == b){
                            mapButacas.remove(2, b)
                            txtButaca2.text = getString(R.string.text_butaca2)
                        }
                    }

                }
                row.addView(imageView)
            }
            tableLayout.addView(row)
        }
        val btnComprar = findViewById<Button>(R.id.btnComprarEntrada)
        btnComprar.setOnClickListener {
            val bt1 = mapButacas[1]
            if(bt1!=null){
                val primeraButaca = asientos[bt1.id - 1]
                primeraButaca.Ocupada = true
            }
            val bt2 = mapButacas[2]
            if(bt2 != null){
                val segundaButaca = asientos[bt2.id - 1]
                segundaButaca.Ocupada = true
            }

            Toast.makeText(this, getString(R.string.text_compra), Toast.LENGTH_LONG).show()
        }

    }
}