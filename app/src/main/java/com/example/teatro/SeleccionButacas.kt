package com.example.teatro



import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class SeleccionButacas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val seleccionLayout = R.layout.seleccion_butacas
        setContentView(seleccionLayout)
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        val numberPicker = findViewById<NumberPicker>(R.id.numPicker)
        val seleccion = findViewById<TextView>(R.id.seleccionadas)
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)

        val llh = LinearLayout(this)
        llh.setOrientation(LinearLayout.HORIZONTAL)


        numberPicker.minValue = 0
        numberPicker.maxValue = 2
        seleccion.text = String.format(getString(R.string.text_sel) + " %s", numberPicker.value)
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal -> seleccion.text = String.format(getString(R.string.text_sel) + " %s", newVal) }
        var seatsSet = 0
        var seats = Array(50){
            false
        }
        var asientos = arrayListOf<Butaca>()
        var butacasEscogidas = arrayListOf<Butaca>()
        for(i in 0..4){
            val row = TableRow(this)
            val paramsRow: TableLayout.LayoutParams =
                TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
            row.layoutParams = paramsRow
            for(j in 1..10){
                val id = (10*i) + j

                var asiento = Butaca(id,i+1, j, false)
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
                                butacasEscogidas.add(asientos.get(imageView.id - 1))
                                butacasEscogidas.add(asientos.get(imageView.id))
                                val paramsTexto: LinearLayout.LayoutParams =
                                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

                                val textoPrimerAsiento = TextView(this)
                                textoPrimerAsiento.layoutParams = paramsTexto
                                textoPrimerAsiento.text = String.format(getString(R.string.text_fila) + " %s, " + getString(R.string.text_columna) + " %s", asientos.get(imageView.id - 1).fila, asientos.get(imageView.id - 1).columna)
                                llh.addView(textoPrimerAsiento)

                                val textoSegundoAsiento = TextView(this)
                                textoSegundoAsiento.layoutParams = paramsTexto
                                textoSegundoAsiento.text = String.format(getString(R.string.text_fila) + " %s, " + getString(R.string.text_columna) + " %s", asientos.get(imageView.id).fila, asientos.get(imageView.id).columna)
                                llh.addView(textoSegundoAsiento)

                                linearLayout.addView(llh)

                            }else{
                                imageView.alpha = 0.6F
                                seats[imageView.id - 1] = true
                                seatsSet++
                                butacasEscogidas.add(asientos.get(imageView.id - 1))
                            }

                        }
                    }else{
                        imageView.alpha = 1F
                        seats[imageView.id - 1] = false
                        seatsSet--
                        butacasEscogidas.remove(asientos.get(imageView.id - 1))
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