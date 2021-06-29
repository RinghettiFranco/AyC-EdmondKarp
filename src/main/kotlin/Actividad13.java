import java.util.*;

import static java.lang.Math.min;

public class Actividad13 {
    private static class Arco {
        public int origen, destino;
        public Arco residual;
        public int flujoPropio;
        public int capacidad;

        public Arco(int o, int d, int c) {
            this.origen = o;
            this.destino = d;
            this.capacidad = c;
        }

        public int capacidadRestante() {
            return capacidad - flujoPropio;
        }

        public void aumentar(int aumento) {
            flujoPropio += aumento;
            residual.flujoPropio -= aumento;
        }
    }

    private static class EdmondKarp {
        private int token = 1;
        private int[] visitados;
        final int n, s, t;
        protected List<Arco>[] grafo;

        public EdmondKarp(int n, int s, int t, LinkedList<Arco>[] grafo){
            this.n = n;
            this.s = s;
            this.t = t;
            this.grafo = grafo;
            visitados = new int[n+1];
        }

        private void visitar(int n){
            visitados[n] = token;
        }

        private boolean visitado(int n){
            return visitados[n] == token;
        }

        private void desVisitarTodo(){
            token++;
        }

        public int calcularFlujoMaximo(){
            int flujo;
            int flujoMaximo = 0;
            do {
                desVisitarTodo();
                flujo = BFSEdmondKarp();
                flujoMaximo += flujo;
            } while(flujo != 0);
            return flujoMaximo;
        }

        private int BFSEdmondKarp(){
            Queue<Integer> cola = new ArrayDeque<>(n);
            Arco[] previos = new Arco[n+1];
            int nodoActual;
            int capacidad;
            int minimoPosible;

            visitar(s);
            cola.offer(s);

            while(!cola.isEmpty()){
                nodoActual = cola.poll();
                if(nodoActual==t) break;

                for(Arco arco: grafo[nodoActual]){
                    capacidad = arco.capacidadRestante();
                    if(capacidad>0 && !visitado(arco.destino)){
                        visitar(arco.destino);
                        previos[arco.destino] = arco;
                        cola.offer(arco.destino);
                    }
                }
            }

            if(previos[t]==null) return 0;

            minimoPosible = Integer.MAX_VALUE;
            for (Arco arco = previos[t]; arco != null; arco = previos[arco.origen])
                minimoPosible = min(minimoPosible, arco.capacidadRestante());

            for (Arco arco = previos[t]; arco != null; arco = previos[arco.origen]) arco.aumentar(minimoPosible);

            return minimoPosible;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        int  n, m, s, t, u, v, c;

        n = scanner.nextInt();
        m = scanner.nextInt();
        s = scanner.nextInt();
        t = scanner.nextInt();

        LinkedList<Arco>[] grafo = new LinkedList[n + 1];
        for (int i = 1; i < grafo.length; i++)
            grafo[i] = new LinkedList<>();

        Arco original, residuo;
        for (int i = 0; i < m; i++) {
            u = scanner.nextInt();
            v = scanner.nextInt();
            c = scanner.nextInt();
            original = new Arco(u, v, c);
            residuo = new Arco(v, u, 0);
            original.residual = residuo;
            residuo.residual = original;
            grafo[u].add(original);
            grafo[v].add(residuo);
        }

        scanner.close();

        EdmondKarp ek = new EdmondKarp(n,s,t,grafo);
        System.out.println(ek.calcularFlujoMaximo());
    }
}
