import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GestorReservasSala gestor = GestorReservasSala.getInstancia();

        // Simulamos 35 solicitudes concurrentes de socios diferentes
        List<Thread> hilos = new ArrayList<>();
        for (int i = 1; i <= 35; i++) {
            final String socioId = "SOCIO-" + i;
            Thread hilo = new Thread(() -> {
                boolean reservado = gestor.reservar(socioId);
                if (reservado) {
                    System.out.println("Reserva confirmada para " + socioId);
                }
            });
            hilos.add(hilo);
        }

        for (Thread hilo : hilos) {
            hilo.start();
        }
        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println("\nReservas activas (" + gestor.getReservasActivas().size() + "): " + gestor.getReservasActivas());
        System.out.println("Lugares disponibles: " + gestor.getLugaresDisponibles());

        // Intento de duplicado y cancelación inexistente
        System.out.println("Duplicado SOCIO-1: " + gestor.reservar("SOCIO-1"));
        System.out.println("Cancelar inexistente SOCIO-999: " + gestor.cancelarReserva("SOCIO-999"));
        System.out.println("Lugares disponibles tras cancelar inexistente: " + gestor.getLugaresDisponibles());

        // Cancelación válida libera 1 lugar
        System.out.println("Cancelar SOCIO-1: " + gestor.cancelarReserva("SOCIO-1"));
        System.out.println("Lugares disponibles tras liberar SOCIO-1: " + gestor.getLugaresDisponibles());
    }
}