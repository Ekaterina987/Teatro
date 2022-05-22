package com.example.teatro

import Filosofo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.coroutines.sync.Mutex


class MainActivity : AppCompatActivity() {
    companion object {
        var cantidadButacas = 0
        val listaButacas = arrayListOf<Butaca>()
            get() = field
        var asientosLibres = 50
            get() = field
            set(value) {
                field = value
            }
        var entradasAgotadas : Boolean = false
            get() = field
            set(value) {
                field = value
            }
        val filosofos = arrayListOf<Filosofo>()
        val teclados = arrayListOf<Teclado>()
        val ordenadores = arrayListOf<Ordenador>()
        fun crearListas(){
            for(i in 1..5){
                filosofos.add(Filosofo("Filósofo $i"))
            }

            for(i in 1..3){
                teclados.add(Teclado("teclado$i"))
            }

            for(i in 1..3){
                ordenadores.add(Ordenador("ordenador$i"))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for(i in 0..4) {
            for (j in 1..10) {
                val id = (10 * i) + j
                val asiento = Butaca(id, i + 1, j, false)
                listaButacas.add(asiento)
            }
        }
        val buttonClick = findViewById<Button>(R.id.btnComprar)
        val numberPicker = findViewById<NumberPicker>(R.id.numPicker)
        val textCantidad = findViewById<TextView>(R.id.textPrecio)
        numberPicker.minValue = 0
        numberPicker.maxValue = if (asientosLibres >= 2) 2 else asientosLibres
        val seleccion = findViewById<TextView>(R.id.seleccionadas)


        seleccion.text = String.format(getString(R.string.text_sel) + " %s", cantidadButacas)
        btnDisable(buttonClick)
        numberPicker.setOnValueChangedListener { _, _, newVal ->
            seleccion.text = String.format(getString(R.string.text_sel) + " %s", newVal)
            if(newVal == 0){
                btnDisable(buttonClick)
                textCantidad.text = String.format("%s", 0)
            }else{
                btnEnable(buttonClick)
                val precio = newVal*8.50
                textCantidad.text = String.format("%s", precio)
            }
        }

        buttonClick.setOnClickListener {
            cantidadButacas = numberPicker.value
            val intent = Intent(this, SeleccionButacas::class.java)
            startActivity(intent)
        }

    }
    fun btnDisable(button: Button) {
        button.isEnabled = false
    }
    fun btnEnable(button: Button) {
        button.isEnabled = true
    }

}
class Teclado(val name: String) {
    val mutex = Mutex()

    fun pillar(filosofo: Filosofo):Boolean {
        val exito = mutex.tryLock()
        val nombre = filosofo.nombre
        if (!exito) {
            println(" $nombre no ha podido pillar el teclado $name")
        } else {
            println("  $nombre ha pillado el teclado $name")
        }
        return exito


    }

    fun soltar(filosofo: Filosofo) {
        val nombre = filosofo.nombre
        if(filosofo.teclado == this){
            try{
                mutex.unlock()
                println("  $nombre ha soltado el teclado $name")
            }catch (e: Exception){

            }
        }

    }
}
class Ordenador(val name: String) {
    val mutex = Mutex()

    fun pillar(filosofo: Filosofo):Boolean {
        val nombre = filosofo.nombre
        val exito = mutex.tryLock()
        if (!exito) {
            println(" $nombre no ha podido pillar el ordenador $name")
        } else {
            println("  $nombre ha pillado el ordenador $name")
        }
        return exito

    }

    fun soltar(filosofo: Filosofo) {
        val nombre = filosofo.nombre
        if(filosofo.ordenador == this) {
            try {
                mutex.unlock()
                println("  $nombre ha soltado el ordenador $name")
            } catch (e: Exception) {

            }
        }

    }
}