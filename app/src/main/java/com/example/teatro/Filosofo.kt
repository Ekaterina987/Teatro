import com.example.teatro.Butaca
import com.example.teatro.MainActivity
import com.example.teatro.Ordenador
import com.example.teatro.Teclado
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

class Filosofo(nombre: String) {
    val nombre = nombre
        get() = field
    var ordenador: Ordenador? = null
        get() = field
        set(value) { field = value }
    var teclado: Teclado? = null
        get() = field
        set(value) { field = value }

    private var ambos: Boolean = false
    var entradas: Int = 0
        get() = field
    var primeraButaca: Butaca? = null
        get() = field
        set(value) { field = value }
    var segundaButaca: Butaca? = null
        get() = field
        set(value) { field = value }
    suspend fun iniciar(){
        delay(100L * (1..10).random())
        ronda()
        if(entradas==10){
            ronda()
        }
    }
    suspend fun ronda(){
        while (MainActivity.asientosLibres>0&&!pillarOrdenadorYTeclado()){
            soltar()
            delay(100L * (1..10).random())
        }
        if(!MainActivity.entradasAgotadas){
            println("$nombre ha conseguido pillar un ordenador y un teclado")
            var i = 0
            while (i < 10 && entradas < 20 &&!MainActivity.entradasAgotadas){
                comprarEntrada()
                i++
                delay(100L * (1..10).random())
                yield()

            }
        }
        soltar()
        yield()
    }
    fun soltar(){
        try {
            teclado?.soltar(this)
            ordenador?.soltar(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun comprarEntrada(){
        if(MainActivity.asientosLibres>0) {
            this.entradas++
            MainActivity.asientosLibres--
            //println("$nombre ha comprado la butaca " + MainActivity.butacasVendidas)
            if(MainActivity.asientosLibres==0){
                println("Se han agotado las butacas")
                MainActivity.entradasAgotadas = true
            }
        }else{
            println("Las butacas están agotadas")
        }
    }

    fun pillarOrdenadorYTeclado(): Boolean{
        val num = (0..2).random()

        val tecladoActual = MainActivity.teclados[num]
        val num2 = (0..2).random()
        val ordenadorActual = MainActivity.ordenadores[num2]

        val tecladoPillado: Boolean = tecladoActual.pillar(this)

        val ordenadorPillado: Boolean = ordenadorActual.pillar(this)

        if(tecladoPillado && ordenadorPillado){
            teclado = tecladoActual
            ordenador = ordenadorActual
            ambos = true
        }else if(tecladoPillado){
            teclado = tecladoActual
        }else if(ordenadorPillado){
            ordenador = ordenadorActual
        }
        return ambos

    }
}