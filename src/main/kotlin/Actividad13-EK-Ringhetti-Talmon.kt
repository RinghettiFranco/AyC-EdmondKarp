import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    val m = scanner.nextInt()
    val s = scanner.nextInt()
    val t = scanner.nextInt()
    val ek: EdmondKarp = EdmondKarp(n,m,s,t)
    ek.inicializarGrafo()
}

class EdmondKarp(
    protected var n: Int,
    protected var m: Int,
    protected var s: Int,
    protected var t: Int,
) {
    class Arco(
        val o: Int,
        val d: Int,
        var residual: Arco? = null,
        var flujo: Int = 0,
        val capacidad: Int
    ){
        fun esResidual(): Boolean = capacidad == 0

        fun capacidadDisponible(): Int = capacidad - flujo

        fun aumentar(aumento: Int) {
            flujo = flujo + aumento
            residual?.let{
                it.flujo = it.flujo - aumento
            }
        }
    }

    private val scanner = Scanner(System.`in`)
    private var token: Int = 1
    private val visitados: Array<Int> = arrayOf(n+1)

    private val grafo: Array<MutableList<Arco>> = arrayOf()

    fun inicializarGrafo() {
        for(i in (1..n)){
            grafo[i] = mutableListOf()
        }
        for(i in (1..m)){
            val u = scanner.nextInt()
            val v = scanner.nextInt()
            val c = scanner.nextInt()
            añadirArco(u,v,c)
        }
    }

    fun añadirArco(o:Int, d:Int, c:Int) {
        val arco = Arco(o,d,capacidad = c)
        val residual = Arco(d,o,capacidad = 0)
        arco.residual = residual
        residual.residual = arco
        grafo[o].add(arco)
        grafo[d].add(residual)
    }

    fun visitar(n:Int) {
        visitados[n] = token
    }

    fun visitado(n:Int): Boolean = visitados[n] == token

    fun desmarcarNodos() = token++

    fun calcularFlujoMaximo(): Int

    fun BFSEdmondKarp(): Int
}