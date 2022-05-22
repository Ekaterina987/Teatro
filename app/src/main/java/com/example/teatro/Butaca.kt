package com.example.teatro

import Filosofo
import kotlinx.coroutines.sync.Mutex

class Butaca(val id: Int, val fila: Int, val columna: Int, var Ocupada: Boolean){
    val mutex = Mutex()
    fun pillar(filosofo: Filosofo):Boolean {
        val nombre = filosofo.nombre
        val exito = mutex.tryLock()
        if (!exito) {
            println(" $nombre no ha podido pillar la butaca $id")
        } else {
            println("  $nombre ha pillado la butaca $id")
        }
        return exito

    }

    fun soltar(filosofo: Filosofo) {
        val nombre = filosofo.nombre
        if(filosofo.primeraButaca == this || filosofo.segundaButaca == this) {
            try {
                mutex.unlock()
                println("  $nombre ha soltado la butaca $id")
            } catch (e: Exception) {

            }
        }

    }
}
