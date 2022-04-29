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

        seleccion.text = String.format(getString(R.string.text_sel) + " %s", numberPicker.value)
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            seleccion.text = String.format(getString(R.string.text_sel) + " %s", newVal)

            if(newVal == 0){
                txtButaca1.text = ""
                txtButaca2.text = ""
            }else if(newVal == 1){
                txtButaca1.text = getString(R.string.text_butaca1)
            }else if(newVal ==2){
                txtButaca1.text = getString(R.string.text_butaca1)
                txtButaca2.text = getString(R.string.text_butaca2)
            }
        }

        var seatsSet = 0


        val seats = Array(50){
            false
        }


        val asientos = arrayListOf<Butaca>()

        val btc = mutableMapOf<Int, Butaca>()

        for(i in 0..4){
            val row = TableRow(this)
            val paramsRow: TableLayout.LayoutParams =
                TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
            row.layoutParams = paramsRow
            for(j in 1..10){
                val id = (10*i) + j

                val asiento = Butaca(id,i+1, j, false)
                asientos.add(asiento)
                val imageView = ImageView(this)
                imageView.setImageResource(R.drawable.seat)

                imageView.id = id
                val params: TableRow.LayoutParams =
                    TableRow.LayoutParams(95, 95)
                imageView.layoutParams = params
                seats[imageView.id - 1] = false

                imageView.setOnClickListener{

                    if(!seats[imageView.id - 1]){

                        if(seatsSet>=numberPicker.value){
                            Toast.makeText(this, getString(R.string.text_no_sel), Toast.LENGTH_SHORT).show()
                        }else {

                            if(numberPicker.value == 2 && seatsSet == 0){
                                imageView.alpha = 0.6F
                                val imgView2 = findViewById<ImageView>(imageView.id + 1)
                                imgView2.alpha = 0.6F
                                seats[imageView.id - 1] = true
                                seats[imageView.id] = true
                                seatsSet+=2

                                val b1 = asientos.get(imageView.id - 1)
                                val b2 = asientos.get(imageView.id)

                                btc.put(1, b1)
                                btc.put(2, b2)

                                txtButaca1.text = String.format(getString(R.string.text_butaca1) + " " + getString(R.string.text_fila) + " %s, " + getString(R.string.text_columna) + " %s", b1.fila, b1.columna)

                                txtButaca2.text = String.format(getString(R.string.text_butaca2) + " " + getString(R.string.text_fila) + " %s " + getString(R.string.text_columna) + " %s", b2.fila,b2.columna)


                            }else{
                                imageView.alpha = 0.6F
                                seats[imageView.id - 1] = true
                                seatsSet++


                                if(btc.containsKey(1)){
                                    val b2 = asientos.get(imageView.id - 1)

                                    btc.put(2, b2)
                                    txtButaca2.text = String.format(getString(R.string.text_butaca2) + " " + getString(R.string.text_fila) + " %s, " + getString(R.string.text_columna) + " %s", b2.fila, b2.columna)
                                }else{
                                    val b1 = asientos.get(imageView.id - 1)
                                    btc.put(1, b1)
                                    txtButaca1.text = String.format(getString(R.string.text_butaca1) + " " + getString(R.string.text_fila) + " %s, " + getString(R.string.text_columna) + " %s", b1.fila, b1.columna)
                                }

                            }

                        }
                    }else{
                        imageView.alpha = 1F
                        seats[imageView.id - 1] = false
                        seatsSet--


                        val b = asientos.get(imageView.id - 1)
                        if (btc.get(1) == b){
                            btc.remove(1, b)
                            txtButaca1.text = getString(R.string.text_butaca1)
                        }else if(btc.get(2) == b){
                            btc.remove(2, b)
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
            Toast.makeText(this, getString(R.string.text_compra), Toast.LENGTH_LONG).show()
        }

    }
}