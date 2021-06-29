import java.lang.Integer.min
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    val m = scanner.nextInt()
    val s = scanner.nextInt()
    val t = scanner.nextInt()
    val ek = EdmondKarp(n,m,s,t)
    ek.inicializarGrafo()
    print(ek.calcularFlujoMaximo())
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
        fun capacidadDisponible(): Int = capacidad - flujo

        fun aumentar(aumento: Int) {
            flujo += aumento
            residual?.let{
                it.flujo -= aumento
            }
        }
    }

    private val scanner = Scanner(System.`in`)
    private var token: Int = 1
    private val visitados = IntArray(n)

    private val grafo: Array<MutableList<Arco>?> = arrayOfNulls<MutableList<Arco>>(n+1)

    fun inicializarGrafo() {
        for(i in (1..n)){
            grafo[i] = mutableListOf()
        }
        for(i in (1..m)){
            val u = scanner.nextInt()
            val v = scanner.nextInt()
            val c = scanner.nextInt()
            anadirArco(u,v,c)
        }
    }

    fun anadirArco(o:Int, d:Int, c:Int) {
        val arco = Arco(o,d,capacidad = c)
        val residual = Arco(d,o,capacidad = 0)
        arco.residual = residual
        residual.residual = arco
        grafo[o]?.add(arco)
        grafo[d]?.add(residual)
    }

    fun visitar(n:Int) {
        visitados[n] = token
    }

    fun visitado(n:Int): Boolean = visitados[n] == token

    fun desmarcarNodos() = token++

    fun calcularFlujoMaximo(): Int {
        var flujo = 0
        var flujoMaximo = 0
        do {
            desmarcarNodos()
            flujo = BFSEdmondKarp()
            flujoMaximo += flujo
        } while (flujo != 0)
        return flujoMaximo
    }

    fun BFSEdmondKarp(): Int {
        val cola: Queue<Int> = ArrayDeque(n)
        visitar (s)
        cola.add(s)
        val previos: Array<Arco> = arrayOf()
        while (!cola.isEmpty()) {
            var nodo: Int = cola.poll()
            if (nodo == t) break

            for (arco in grafo[nodo]!!) {
                var cap: Int = arco.capacidadDisponible()
                if (cap > 0 && !visitado(arco.d)) {
                    visitar(arco.d)
                    previos[arco.d] = arco
                    cola.offer(arco.d)
                }
            }
        }
        if (previos[t].equals(null)) return 0
        var toRet : Int = Int.MAX_VALUE
        var arco: Arco = previos[t]
        while (arco!=null) {
            toRet = min (toRet, arco.capacidadDisponible())
            arco.aumentar(toRet)
            arco = previos[arco.o]
        }
        return toRet
    }
}

