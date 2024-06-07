import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    private static int dinero = 100;

    public static void main(String[] args) {
        List<Thread> hilos = new ArrayList<>();
        hilos.add(new Thread(new HiloDescontar("Hilo 1")));
        hilos.add(new Thread(new HiloDescontar("Hilo 2")));
        hilos.add(new Thread(new HiloDescontar("Hilo 3")));
        hilos.add(new Thread(new HiloDescontar("Hilo 4")));
        Collections.shuffle(hilos);
        for (Thread hilo : hilos) {
            hilo.start();
        }
    }

    static class HiloDescontar implements Runnable {
        private final String nombreHilo;

        public HiloDescontar(String nombreHilo) {
            this.nombreHilo = nombreHilo;
        }

        @Override
        public void run() {
            synchronized (Main.class) {
                try {
                    long inicio = System.currentTimeMillis();
                    TimeUnit.SECONDS.sleep(1);
                    if (dinero >= 30) {
                        dinero -= 30;
                        System.out.println(nombreHilo + " descontó 30 pesos. Dinero restante: " + dinero + inicio);
                    } else {
                        System.err.println(nombreHilo + " no tiene suficiente dinero para descontar.");
                    }
                    long fin = System.currentTimeMillis();
                    long tiempoEjecucion = fin - inicio;
                    System.out.println(nombreHilo + " tardó " + tiempoEjecucion + " milisegundos en ejecutarse.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}