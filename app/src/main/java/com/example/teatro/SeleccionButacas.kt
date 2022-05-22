package com.example.teatro

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
        val txtButaca1 = findViewById<TextView>(R.id.butaca1)
        val txtButaca2 = findViewById<TextView>(R.id.butaca2)
        val btnVolver = findViewById<TextView>(R.id.btnVolver)

        val cantidadButacas = MainActivity.cantidadButacas

        when(cantidadButacas){
            1 -> txtButaca1.text = getString(R.string.text_butaca1)
            2 -> {
                txtButaca1.text = getString(R.string.text_butaca1)
                txtButaca2.text = getString(R.string.text_butaca2)
            }
        }

        val seats = Array(50){
            false
        }
        val asientos = MainActivity.listaButacas
        val mapButacas = mutableMapOf<Int, Butaca>()

        for(i in 0..4){
            val row = TableRow(this)
            val paramsRow: TableLayout.LayoutParams =
                TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
            row.layoutParams = paramsRow
            for(j in 1..10){
                val id = (10*i) + j

                val imageView = ImageView(this)
                if(asientos[id - 1].Ocupada){
                    imageView.setImageResource(R.drawable.seat_ocupada4)
                }else{
                    imageView.setImageResource(R.drawable.seat_main5)
                }

                imageView.id = id
                val params: TableRow.LayoutParams =
                    TableRow.LayoutParams(90, 90)
                imageView.layoutParams = params
                seats[imageView.id - 1] = false

                imageView.setOnClickListener{
                    clickButaca(imageView, seats, mapButacas, asientos, txtButaca1, txtButaca2, cantidadButacas)
                }
                row.addView(imageView)
            }
            val numFila = TextView(this)
            val paramsFila: TableRow.LayoutParams =
                TableRow.LayoutParams(90, 90)
            numFila.layoutParams = paramsFila
            val fila = i + 1
            numFila.text = String.format(getString(R.string.text_f) + "%s", fila)
            row.layoutParams = paramsRow
            row.addView(numFila)

            tableLayout.addView(row)
        }

        val row1 = TableRow(this)
        val paramsRow1: TableLayout.LayoutParams =
            TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
        row1.layoutParams = paramsRow1
        for (k in 1..10){
            val numCol = TextView(this)
            val paramsCol: TableRow.LayoutParams =
                TableRow.LayoutParams(90, 90)
            numCol.layoutParams = paramsCol
            val col = k
            numCol.text = String.format(getString(R.string.text_c) + "%s", col)
            row1.layoutParams = paramsRow1
            row1.addView(numCol)
        }
        tableLayout.addView(row1)

        val btnComprar = findViewById<Button>(R.id.btnComprarEntrada)
        btnComprar.setOnClickListener {
            comprar(mapButacas, asientos)
        }
        btnVolver.setOnClickListener{
            volver()
        }

    }
    fun encontrarAsiento(id: Int, butacas: ArrayList<Butaca>): Butaca?{
        var encontrado = false
        val actual = butacas[id]
        var asiento : Butaca? = null
        val izq = actual.columna - 1
        val dch = 10 - actual.columna
        val arriba = actual.fila - 1
        val abajo = 5 - actual.fila
        val list = listOf(izq, dch, arriba, abajo)
        val mayor: Int = list.maxOrNull() ?: 0

        while (!encontrado){
            var i = 1
            while (i <= mayor && !encontrado){

                val i1 = id + i
                val i2 = id - i
                val i3 = id - 10*i
                val i4 = id + 10*i


                if(i1 <= 50 && (i1 % 10) > (id % 10) && !butacas[i1].Ocupada){
                    println(1)
                    encontrado = true
                    asiento = butacas[i1]
                }
                else if(i2 >= 0 && (i2 % 10) < (id % 10) && !butacas[i2].Ocupada){
                    println(2)
                    encontrado = true
                    asiento = butacas[i2]
                }else{
                    var j = 1
                    while(j <= i && !encontrado){
                        var k = 1
                        while(k <= i && !encontrado){
                            val i5 = (id - j*10) - k
                            val i6 = (id - j*10) + k
                            val i7 = (id + j*10) + k
                            val i8 = id + (j*10 - k)
                            if(i5 >= 0 && ((id - k) % 10) < (id % 10) && !butacas[i5].Ocupada){
                                println(5)
                                encontrado = true
                                asiento = butacas[i5]
                            }
                            else if(i6 >= 0 && ((id + k) % 10) > (id % 10) && !butacas[i6].Ocupada){
                                println(6)
                                encontrado = true
                                asiento = butacas[i6]
                            }
                            else if(i7 <= 50 && ((id + k) % 10) > (id % 10) && !butacas[i7].Ocupada){
                                println(7)
                                encontrado = true
                                asiento = butacas[i7]
                            }
                            else if(i8 <= 50 && ((id - k) % 10) < (id % 10) && !butacas[i8].Ocupada){
                                println(8)
                                encontrado = true
                                asiento = butacas[i8]
                            }
                            k++
                        }
                        j++
                    }
                    if(i3 >= 0 && !butacas[i3].Ocupada){
                        println(3)
                        encontrado = true
                        asiento = butacas[i3]
                    }
                    else if(i4 <= 50 && !butacas[i4].Ocupada){
                        println(4)
                        encontrado = true
                        asiento = butacas[i4]
                    }

                }
                i++
            }

        }
        return asiento
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickButaca(imageView: ImageView, seats: Array<Boolean>, mapButacas: MutableMap<Int, Butaca>,
                    asientos: ArrayList<Butaca>, txtButaca1: TextView, txtButaca2: TextView, cantidadButacas: Int){
        if(!seats[imageView.id - 1]){

            if(mapButacas.size>=cantidadButacas){
                Toast.makeText(this, getString(R.string.text_no_sel), Toast.LENGTH_SHORT).show()
            }else if(asientos[imageView.id - 1].Ocupada){
                Toast.makeText(this, getString(R.string.text_ocupada), Toast.LENGTH_SHORT).show()
            }else {

                if(cantidadButacas == 2 && mapButacas.isEmpty()){
                    imageView.alpha = 0.6F
                    var imgView2: ImageView? = null
                    val b1 = asientos[imageView.id - 1]

                    val indice = imageView.id - 1
                    val b2 = encontrarAsiento(indice, asientos)

                    if(b2!=null){
                        seats[b2.id - 1] = true
                        imgView2 = findViewById(b2.id)
                    }

                    txtButaca1.text = String.format(getString(R.string.text_butaca1) + " " + getString(R.string.text_fila) + ": %s, " + getString(R.string.text_columna) + ": %s", b1.fila, b1.columna)
                    if(imgView2!=null && b2!=null){
                        imgView2.alpha = 0.6F
                        seats[imageView.id - 1] = true
                        mapButacas[1] = b1
                        mapButacas[2] = b2
                        txtButaca2.text = String.format(getString(R.string.text_butaca2) + " " + getString(R.string.text_fila) + ": %s " + getString(R.string.text_columna) + ": %s", b2.fila,b2.columna)
                    }

                }else{
                    imageView.alpha = 0.6F
                    seats[imageView.id - 1] = true

                    if(mapButacas.containsKey(1)){
                        val b2 = asientos[imageView.id - 1]

                        mapButacas[2] = b2
                        txtButaca2.text = String.format(getString(R.string.text_butaca2) + " " + getString(R.string.text_fila) + ": %s, " + getString(R.string.text_columna) + ": %s", b2.fila, b2.columna)
                    }else{
                        val b1 = asientos[imageView.id - 1]
                        mapButacas[1] = b1
                        txtButaca1.text = String.format(getString(R.string.text_butaca1) + " " + getString(R.string.text_fila) + ": %s, " + getString(R.string.text_columna) + ": %s", b1.fila, b1.columna)
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
    fun volver(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun comprar(mapButacas: MutableMap<Int, Butaca>, asientos: ArrayList<Butaca>){
        var asientosLibres = MainActivity.asientosLibres
        val bt1 = mapButacas[1]
        if(bt1!=null){
            val primeraButaca = asientos[bt1.id - 1]
            primeraButaca.Ocupada = true
            asientosLibres--
        }
        val bt2 = mapButacas[2]
        if(bt2 != null){
            val segundaButaca = asientos[bt2.id - 1]
            segundaButaca.Ocupada = true
            asientosLibres--
        }
        MainActivity.asientosLibres = asientosLibres
        Toast.makeText(this, getString(R.string.text_compra), Toast.LENGTH_LONG).show()
    }

}