Ejercicio 5: Gestor Concurrente de Reservas de una Sala de Lectura (Patrón Singleton)
Temática
Gestión de Biblioteca — Administración concurrente y atómica de los 10 cupos de una sala de lectura ante múltiples hilos.

Consigna

 Gestor Concurrente de Reservas de una Sala de Lectura

Contexto del problema: Una biblioteca tiene una sala de lectura con diez lugares. Varios puestos de atención, simulados mediante hilos, reciben solicitudes al mismo tiempo. Si cada puesto controla su propia disponibilidad, se aceptan más reservas que lugares existentes.

Objetivo de aprendizaje: Implementar un Singleton seguro ante concurrencia y proteger las operaciones sobre cupos compartidos.

Requisitos funcionales:

Administrar diez lugares para una misma franja horaria.

Registrar reservas mediante un identificador de socio.

Rechazar reservas duplicadas o solicitudes cuando no queden lugares.

Cancelar reservas y recuperar el lugar correspondiente.

Consultar las reservas activas y los lugares disponibles.

Restricciones técnicas:

La creación de la instancia Singleton debe ser segura ante solicitudes concurrentes.

La comprobación de disponibilidad y el registro de la reserva deben constituir una operación atómica.

Se deben simular al menos treinta solicitudes concurrentes de socios diferentes.

Las reservas activas nunca deben superar diez y la disponibilidad nunca debe ser negativa.

Cancelar una reserva inexistente no debe aumentar los lugares disponibles.



Explicación lógica
Atributos: En la clase GestorReservasSala se declara CAPACIDAD_MAXIMA (public static final int = 10), sociosConReserva (private final Set<String>) para llevar el registro de reservas activas y instancia (private static volatile GestorReservasSala) para garantizar visibilidad segura entre hilos.

Instancia única: El constructor es private y getInstancia() aplica Double-Checked Locking con synchronized y volatile para que la creación de la instancia sea segura incluso bajo acceso concurrente simultáneo.

Asignación y Acceso: En Main, decenas de hilos intentan reservar lugares en paralelo; los métodos reservar(...) y cancelarReserva(...) están sincronizados para que verificar cupo y registrar sean atómicos, impidiendo superar 10 reservas o bajar de 0 disponibles, evitando duplicados, liberando cupo al cancelar una reserva existente y manteniendo intactos los lugares si se cancela una inexistente.

Estructura del ejercicio
GestorReservasSala.java: Implementa el Singleton seguro ante concurrencia y las operaciones atómicas de reserva y cancelación.

Main.java: Simula solicitudes concurrentes con hilos (Thread) y comprueba cupos, reservas duplicadas y cancelaciones.