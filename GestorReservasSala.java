import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class GestorReservasSala {
    public static final int CAPACIDAD_MAXIMA = 10;

    private static volatile GestorReservasSala instancia;
    private final Set<String> sociosConReserva;

    private GestorReservasSala() {
        this.sociosConReserva = new LinkedHashSet<>();
    }

    public static GestorReservasSala getInstancia() {
        if (instancia == null) {
            synchronized (GestorReservasSala.class) {
                if (instancia == null) {
                    instancia = new GestorReservasSala();
                }
            }
        }
        return instancia;
    }

    public synchronized boolean reservar(String idSocio) {
        if (idSocio == null || idSocio.trim().isEmpty()) {
            return false;
        }
        String socio = idSocio.trim();
        if (sociosConReserva.contains(socio)) {
            return false; // Reserva duplicada
        }
        if (sociosConReserva.size() >= CAPACIDAD_MAXIMA) {
            return false; // Sin cupos disponibles
        }
        return sociosConReserva.add(socio);
    }

    public synchronized boolean cancelarReserva(String idSocio) {
        if (idSocio == null || idSocio.trim().isEmpty()) {
            return false;
        }
        return sociosConReserva.remove(idSocio.trim());
    }

    public synchronized int getLugaresDisponibles() {
        int disponibles = CAPACIDAD_MAXIMA - sociosConReserva.size();
        return Math.max(0, disponibles);
    }

    public synchronized Set<String> getReservasActivas() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(sociosConReserva));
    }
}