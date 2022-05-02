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

        var asientosLibres = MainActivity.asientosLibres
        Toast.makeText(this, asientosLibres.toString(), Toast.LENGTH_LONG).show()
        numberPicker.minValue = 0
        numberPicker.maxValue = if (asientosLibres >= 2) 2 else asientosLibres

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
                        }else if(asientos[imageView.id - 1].Ocupada){
                            Toast.makeText(this, getString(R.string.text_ocupada), Toast.LENGTH_SHORT).show()
                        }else {

                            if(numberPicker.value == 2 && mapButacas.isEmpty()){
                                imageView.alpha = 0.6F
                                var imgView2: ImageView? = null
                                val b1 = asientos[imageView.id - 1]
                                val b2: Butaca?
                                if(imageView.id%10 == 0){
                                    println("Al revés")
                                    val indice = imageView.id - 1
                                    val asiento = encontrarAsientoIzq(indice, asientos)
                                    b2 = asiento
                                    if(asiento!=null){
                                        seats[asiento.id - 1] = true
                                        imgView2 = findViewById(asiento.id)
                                    }
                                }else{
                                    val indice = imageView.id - 1
                                    val asiento = encontrarAsiento(indice, asientos)
                                    b2 = asiento
                                    if(asiento!=null){
                                        seats[asiento.id - 1] = true
                                        imgView2 = findViewById(asiento.id)
                                    }
                                }
                                txtButaca1.text = String.format(getString(R.string.text_butaca1) + " " + getString(R.string.text_fila) + " %s, " + getString(R.string.text_columna) + " %s", b1.fila, b1.columna)
                                if(imgView2!=null && b2!=null){
                                    imgView2.alpha = 0.6F
                                    seats[imageView.id - 1] = true
                                    mapButacas[1] = b1
                                    mapButacas[2] = b2
                                    txtButaca2.text = String.format(getString(R.string.text_butaca2) + " " + getString(R.string.text_fila) + " %s " + getString(R.string.text_columna) + " %s", b2.fila,b2.columna)
                                }

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
                asientosLibres--
            }
            val bt2 = mapButacas[2]
            if(bt2 != null){
                val segundaButaca = asientos[bt2.id - 1]
                segundaButaca.Ocupada = true
                asientosLibres--
            }
            MainActivity.asientosLibres = asientosLibres
            Toast.makeText(this, asientosLibres.toString(), Toast.LENGTH_LONG).show()
            Toast.makeText(this, getString(R.string.text_compra), Toast.LENGTH_LONG).show()
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
                val i5 = (id - i*10) - i
                val i6 = (id - i*10) + i
                val i7 = (id + i*10) + i
                val i8 = id + (i*10 - i)

                if(i1 <= 50 && (i1 % 10) > (id % 10) && !butacas[i1].Ocupada){
                    println(1)
                    encontrado = true
                    asiento = butacas[i1]
                }
                else if(i2 >= 0 && (i2 % 10) < (id % 10) && !butacas[i2].Ocupada){
                    println(2)
                    encontrado = true
                    asiento = butacas[i2]
                }else if(i3 >= 0 && !butacas[i3].Ocupada){
                    println(3)
                    encontrado = true
                    asiento = butacas[i3]
                }
                else if(i4 <= 50 && !butacas[i4].Ocupada){
                    println(4)
                    encontrado = true
                    asiento = butacas[i4]
                }
                else if(i5 >= 0 && (i2 % 10) < (id % 10) && !butacas[i5].Ocupada){
                    println(5)
                    encontrado = true
                    asiento = butacas[i5]
                }
                else if(i6 >= 0 && (i1 % 10) > (id % 10) && !butacas[i6].Ocupada){
                    println(6)
                    encontrado = true
                    asiento = butacas[i6]
                }
                else if(i7 <= 50 && (i1 % 10) > (id % 10) && !butacas[i7].Ocupada){
                    println(7)
                    encontrado = true
                    asiento = butacas[i7]
                }
                else if(i8 <= 50 && (i2 - i % 10) < (id % 10) && !butacas[i8].Ocupada){
                    println(8)
                    encontrado = true
                    asiento = butacas[i8]
                }
                i++
            }

        }
        return asiento
    }
    fun encontrarAsientoIzq(id: Int, butacas: ArrayList<Butaca>): Butaca?{
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

                val i1 = id - i
                val i2 = id + i
                val i3 = id - 10*i
                val i4 = id + 10*i
                val i5 = (id - i*10) - i
                val i6 = (id - i*10) + i
                val i7 = (id + i*10) + i
                val i8 = id + (i*10 - i)

                if(i1 >= 0 && (i1 % 10) < (id % 10) && !butacas[i1].Ocupada){
                    println(1)
                    encontrado = true
                    asiento = butacas[i1]
                }
                else if(i2 <= 50 && (i2 % 10) > (id % 10) && !butacas[i2].Ocupada){
                    println(2)
                    encontrado = true
                    asiento = butacas[i2]
                }
                else if(i3 >= 0 && !butacas[i3].Ocupada){
                    println(3)
                    encontrado = true
                    asiento = butacas[i3]
                }
                else if(i4 <= 50 && !butacas[i4].Ocupada){
                    println(4)
                    encontrado = true
                    asiento = butacas[i4]
                }
                else if(i5 >= 0 && (i2 % 10) < (id % 10) && !butacas[i5].Ocupada){
                    println(5)
                    encontrado = true
                    asiento = butacas[i5]
                }
                else if(i6 >= 0 && (i1 % 10) > (id % 10) && !butacas[i6].Ocupada){
                    println(6)
                    encontrado = true
                    asiento = butacas[i6]
                }
                else if(i7 <= 50 && (i1 % 10) > (id % 10) && !butacas[i7].Ocupada){
                    println(7)
                    encontrado = true
                    asiento = butacas[i7]
                }
                else if(i8 <= 50 && (i2 % 10) < (id % 10) && !butacas[i8].Ocupada){
                    println(8)
                    encontrado = true
                    asiento = butacas[i8]
                }
                i++
            }

        }
        return asiento
    }
}