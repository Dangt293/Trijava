package Clases;

import java.util.ArrayList;

/**
 * @author Daniel
 * @version 1.0
 */
public class Log {
    private static ArrayList<String> log = new ArrayList<>();

    /**
     *
     * @param lineas son las lineas que vamos a añadir al Log registrado por el programa, el cuál es el del día actual
     */
    public static void generarLog(String lineas) {
        log.add(lineas);
    }

    public static ArrayList<String> getLog() {
        return log;
    }

    /**
     * elimina el fichero .log
     */
    public static void eliminarLog() {
        log.clear();
    }
}
