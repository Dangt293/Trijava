package Clases;

import Constantes.Constantes;

import java.util.ArrayList;

/**
 * @author Daniel
 * @version 1.0
 */
public class Historico {
    private static ArrayList<String> historico = new ArrayList<>();

    /**
     *
     * @param lineas son las partidas que están guardadas en un txt y queremos introducir en los datos del programa
     */
    public static void generarHistorico(String lineas) {
        historico.add(lineas);
    }

    /**
     * Muestra el historico, o un mensaje de vacio en caso de no tener partidas registradas
     */
    public static void mostarHistorico() {
        if (historico.isEmpty()) {
            System.out.println(Constantes.sinRegistros);
        }else {
            for (String linea : historico) {
                System.out.println(linea);
            }
        }
    }

    public static ArrayList<String> getHistorico() {
        return historico;
    }
}
