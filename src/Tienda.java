import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class Producto {
    String nombre;
    String descripcion;
    double precio;
    int existencia;

    public Producto(String nombre, String descripcion, double precio, int existencia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencia = existencia;
    }

    public synchronized boolean reducirExistencia() {
        if (existencia > 0) {
            existencia--;
            return true;
        }
        return false;
    }
}

class Inventario {
    private List<Producto> productos = new LinkedList<>();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public synchronized Producto obtenerProductoDisponible() {
        for (Producto producto : productos) {
            if (producto.reducirExistencia()) {
                return producto;
            }
        }
        return null;
    }
}

class CuentaCorriente {
    private double balance;

    public synchronized void agregarBalance(double monto) {
        balance += monto;
    }

    public double getBalance() {
        return balance;
    }
}

class Cajero extends Thread {
    private String nombreCliente;
    private Inventario inventario;
    private CuentaCorriente cuentaCorriente;

    public Cajero(String nombreCliente, Inventario inventario, CuentaCorriente cuentaCorriente) {
        this.nombreCliente = nombreCliente;
        this.inventario = inventario;
        this.cuentaCorriente = cuentaCorriente;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            Producto producto = inventario.obtenerProductoDisponible();
            if (producto != null) {
                long tiempoInicio = System.currentTimeMillis();
                int tiempoCobro = ThreadLocalRandom.current().nextInt(2000, 5000);
                try {
                    Thread.sleep(tiempoCobro);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long tiempoFin = System.currentTimeMillis();
                cuentaCorriente.agregarBalance(producto.precio);
                System.out.println("Producto: " + producto.nombre + ", Precio: " + producto.precio +
                        ", Timestamp: " + tiempoFin + ", Cliente: " + nombreCliente +
                        ", Tiempo de escaneo: " + (tiempoFin - tiempoInicio) + "ms");
            } else {
                System.out.println("No hay más productos disponibles para el cliente " + nombreCliente);
            }
        }
    }
}

public class Tienda {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        CuentaCorriente cuentaCorriente = new CuentaCorriente();

        inventario.agregarProducto(new Producto("Sopa", "Sopa de pollo", 45, 10));
        inventario.agregarProducto(new Producto("Pan", "Pan integral", 35, 10));
        inventario.agregarProducto(new Producto("Leche", "Leche descremada", 22, 10));
        inventario.agregarProducto(new Producto("Huevos", "Huevos", 38, 10));
        inventario.agregarProducto(new Producto("Jugo", "Jugo de naranja", 20, 10));

        Cajero[] cajeros = new Cajero[6];
        for (int i = 0; i < cajeros.length; i++) {
            cajeros[i] = new Cajero("Cliente-" + (i + 1), inventario, cuentaCorriente);
            cajeros[i].start();
        }


        for (Cajero cajero : cajeros) {
            try {
                cajero.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Balance final de la cuenta corriente: $" + cuentaCorriente.getBalance()+ " Pesos");
    }
}
