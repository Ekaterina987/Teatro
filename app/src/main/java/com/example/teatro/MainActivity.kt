package com.example.teatro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow

class MainActivity : AppCompatActivity() {
    companion object {
        val listaButacas = arrayListOf<Butaca>()
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
        buttonClick.setOnClickListener {
            val intent = Intent(this, SeleccionButacas::class.java)
            startActivity(intent)
        }
    }

}