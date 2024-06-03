//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    long dinero=100;
    public long getDinero() {
        return dinero;
    }

    public void setDinero(long dinero) {
        this.dinero = dinero;
    }
    public static void main(String[] args) {
        Main mainInstance = new Main();
        Hilos hilo1 = new Hilos(mainInstance);
        Hilos hilo2 = new Hilos(mainInstance);
        Hilos hilo3 = new Hilos(mainInstance);
        Hilos hilo4 = new Hilos(mainInstance);
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
    }
}