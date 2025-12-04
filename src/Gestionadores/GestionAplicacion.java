package Gestionadores;

import Clases.Historico;
import Clases.Ranking;
import Constantes.Constantes;
import Preguntas.Geografia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * @author Daniel
 * @version 1.0
 */
public class GestionAplicacion {
    /**
     * Se ejecuta al inicio y registra la información de los ficheros de datos
     */
    public static void inicarAplicacion() {
        System.out.println("*********************************" +
                           "\n            TRIJAVA " +
                           "\n*********************************");
        leerRanking();
        leerHistorico();
        leerCiudades();
        GestionLog.leerLog();
        GestionLog.añadirRegistro(Constantes.inicioApli);
    }

    /**
     * Registra el cierre del programa
     */
    public static void cerrarAplicacion() {
        System.out.println("Guardando datos y cerrando la aplicación...");
        GestionLog.añadirRegistro(Constantes.cerrarApli);
        escribirRanking();
    }

    /**
     * Lee la información del archivo ranking
     */
    private static void leerRanking() {
        System.out.print("Leyendo ranking...");
        try {
            Path rutaRanking = Paths.get(Constantes.rutaRanking);
            ArrayList<String> lineasRanking;
            lineasRanking = (ArrayList<String>) Files.readAllLines(rutaRanking);
            for (String linea: lineasRanking) {
                String[] lineaDividida = linea.split(" ");
                Ranking.añadirRegistro(lineaDividida[0], Integer.valueOf(lineaDividida[1]));
                GestionJugadores.añadirViaFichero(lineaDividida[0]);
            }
            System.out.println(Constantes.lecturaCorrecta);
        }catch (Exception ex) {
            System.out.println("El fichero no existe");
        }
    }

    /**
     * Escribe un nuevo registro en el ranking
     */

    private static void escribirRanking() {
        try {
            Path rutaRanking = Paths.get(Constantes.rutaRanking);
            Files.write(rutaRanking, Ranking.devolverRanking().getBytes());
        }catch (Exception ex) {
            System.out.println(Constantes.errorFichero + " ranking");
        }
    }

    /**
     * Escribe un nuevo registro en el historico
     * @param partida información de la partida a registrar
     */
    public static void escribirHistorico(String partida) {
        try {
            Path rutaHistorico = Paths.get(Constantes.rutaHistorico);
            if (Historico.getHistorico().isEmpty()) {
                Files.write(rutaHistorico, (partida+ "\n").getBytes());
            }else {
                Files.write(rutaHistorico, (partida+"\n").getBytes(), StandardOpenOption.APPEND);
            }
            Historico.generarHistorico(partida);
        }catch (IOException io) {
            System.out.println(Constantes.errorFichero+ " historico");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Lee la información del archivo historico
     */
    private static void leerHistorico() {
        System.out.print("Leyendo Historico...");
        try {
            Path rutaHistorico = Paths.get(Constantes.rutaHistorico);
            ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(rutaHistorico);
            for (String linea: lineas) {
                Historico.generarHistorico(linea);
            }
            System.out.println(Constantes.lecturaCorrecta);
        }catch (IOException io) {
            System.out.println("El fichero no existe");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Lee la información del archivo config.propierties
     */
    public static boolean leerPropiedades(){
        try {
            Path rutaPropiedades = Paths.get(Constantes.rutaPropiedades);
            ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(rutaPropiedades);
            for (String linea: lineas) {
                String lineasDiv[] = linea.split(" ");
                if (lineasDiv[2].equalsIgnoreCase("true")) {
                    System.out.println("Modo depuración activado");
                    return true;
                }else if (!lineasDiv[2].equalsIgnoreCase("false")) {
                    System.out.println(Constantes.errorFichero+ " configuraciones");
                }
                return false;
            }
        }catch (IOException io) {
            System.out.println(Constantes.errorFichero+ " configuraciones");
        }catch (IndexOutOfBoundsException indexBounds) {
            System.out.println(Constantes.errorFichero+ " configuraciones");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Lee la información del archivo ciudades
     */
    private static void leerCiudades() {
        System.out.print("Leyendo ciudades...");
        try {
            Path ruta = Paths.get(Constantes.rutaCiudades);
            ArrayList<String> ciudades = (ArrayList<String>) Files.readAllLines(ruta);
            Geografia.setCiudades(ciudades);
            System.out.println(Constantes.lecturaCorrecta);
        }catch (IOException io) {
            System.out.println(Constantes.errorFichero + "ciudades");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

