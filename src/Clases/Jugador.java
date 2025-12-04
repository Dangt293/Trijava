package Clases;

/**
 * @author Daniel
 * @version 1.0
 */
public class Jugador {
    private String nombre;
    private int puntuacion;
    private boolean persona;

    /**
     *
     * @param nombre Nombre del jugador
     */
    public Jugador(String nombre){
        this.nombre = nombre;
        persona = true;
    }

    /**
     * Este constructor se utiliza para espeficiar que el jugador es una CPU
     * @param nombre Nombre del jugador
     * @param persona Booleano que es false si el jugador es una CPU
     */
    public Jugador(String nombre, boolean persona) {
        this.nombre = nombre;
        this.persona = persona;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public boolean isPersona() {
        return persona;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
