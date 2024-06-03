public class Hilos extends Thread {
    private final Main mainInstance;

    public Hilos(Main mainInstance) {
        this.mainInstance = mainInstance;
    }

    @Override
    public void run() {
        synchronized (mainInstance) {
            long amountToDecrement = 30;
            long currentDinero = mainInstance.getDinero();
            long startTime = System.currentTimeMillis();

            if (currentDinero >= amountToDecrement) {
                mainInstance.setDinero(currentDinero - amountToDecrement);
                long endTime = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + ": " + mainInstance.getDinero());
                long duration = endTime - startTime;
                System.out.println("Inicial: " + startTime + " Final: " + endTime);
            } else {
              System.err.println(Thread.currentThread().getName() + ": No hay suficiente dinero para descontar, Dinero disponible:"+ currentDinero);
            }
        }
    }
}
