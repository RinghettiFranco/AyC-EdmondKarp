
fun main(args: Array<String>) {
    println("Hello World!")
    val n = Integer.valueOf(readLine())
    val m = Integer.valueOf(readLine())
    val s = Integer.valueOf(readLine())
    val t = Integer.valueOf(readLine())
    val ek: EdmondKarp = EdmondKarp(n,m,s,t)
    ek.inicializarGrafo()
}

class Arco(
    protected val o: Int,
    protected val d: Int,
    protected var residual: Arco? = null,
    protected var flujo: Int = 0,
    protected val capacidad: Int
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

class EdmondKarp(
    protected var n: Int,
    protected var m: Int,
    protected var s: Int,
    protected var t: Int,
) {
    private var token: Int = 1
    private val visitados: Array<Int> = arrayOf(n+1)
    //solved
    val grafo: Array<MutableList<Arco>> = arrayOf()

    fun inicializarGrafo() {
        for(i in (1..n)){
            grafo[i] = mutableListOf()
        }
        for(i in (1..m)){
            val u = Integer.valueOf(readLine())
            val v = Integer.valueOf(readLine())
            val c = Integer.valueOf(readLine())
            grafo[i].add(Arco(u,v,capacidad = c))
        }
    }

    fun visitar(n:Int) {
        visitados[n] = token
    }

    fun visitado(n:Int): Boolean = visitados[n] == token

    fun desmarcarNodos() = token++
}