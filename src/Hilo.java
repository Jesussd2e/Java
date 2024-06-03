public class Hilo extends Thread{
    private String HiloNombre= " ";
    public String getHiloNombre() {
        return HiloNombre;
    }

    public void setHiloNombre(String hiloNombre) {
        HiloNombre = hiloNombre;
    }
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        System.out.println("Este es el hilo " + getHiloNombre());
        try {
            for (int i = 0; i < 10000; i++) {
                sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Inicial: " + startTime + " Hilo: " + getHiloNombre());
        System.out.println("Final: " + endTime + " Hilo: " + getHiloNombre());
        System.out.println("Duración del hilo " + getHiloNombre() + ": " + duration + " milisegundos");
    }


}
