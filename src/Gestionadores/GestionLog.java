package Gestionadores;

import Clases.Log;
import Constantes.Constantes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * @author Daniel
 * @version 1.0
 */
public class GestionLog {

    /**
     *
     * @param registro cadena de carácteres que se van a registrar
     */
    public static void añadirRegistro(String registro) {
        String registroFinal = obtenerFecha() + obtenerHora() +":"+ registro;
        if (comprobarFecha()) {
            escribirLog(registroFinal);
        }else {
            archivarFichero();
            escribirLog(registroFinal);
        }
    }

    /**
     *
     * @return la fecha actual en el momento que se añade el registro en formato dd/MM/yyyy
     */
    private static String obtenerFecha() {
        int fecha = LocalDate.now().getDayOfMonth();
        String fechaFinal = fecha + "/";
        fecha = LocalDate.now().getMonthValue();
        String fechaMes = String.format("%02d", fecha);
        fechaFinal = fechaFinal + fechaMes +"/";
        fecha = LocalDate.now().getYear();
        fechaFinal = fechaFinal + fecha;
        return "["+fechaFinal+"]";
    }

    /**
     *
     * @return la hora del momento en el que se añade el registro en formato HH:mm:ss
     */
    private static String obtenerHora() {
        int hora = LocalTime.now().getHour();
        String horaFinal = hora+ ":";
        hora = LocalTime.now().getMinute();
        horaFinal = horaFinal + hora+ ":";
        hora = LocalTime.now().getSecond();
        horaFinal = horaFinal + hora;
        return "["+horaFinal+"]";
    }

    /**
     *
     * @param registro la cadena de carácteres que se queria almacener con la fecga y hora añadidas
     */
    private static void escribirLog(String registro) {
        try {
            Path rutaLog = Paths.get(Constantes.rutaLog);
            if (Log.getLog().isEmpty()) {
                Files.write(rutaLog, (registro+ "\n").getBytes());
            }else {
                Files.write(rutaLog, (registro+"\n").getBytes(), StandardOpenOption.APPEND);
            }
            Log.generarLog(registro);
        }catch (IOException io) {
            System.out.println(Constantes.errorFichero+ "salida.log");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Lee el fichero .log y lo guarda en el programa
     */
    public static void leerLog() {
        System.out.print("Leyendo salida.log ...");
        try {
            Path rutaLog = Paths.get(Constantes.rutaLog);
            ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(rutaLog);
            for (String linea: lineas) {
                Log.generarLog(linea);
            }
            System.out.println(Constantes.lecturaCorrecta);
        }catch (IOException io) {
            System.out.println("El fichero no existe");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @return devuelve true si la fecha actual es del mismo dia que el log o no hay registros, y false si el día es el mismo
     */
    private static boolean comprobarFecha() {
        try {
            if (!Log.getLog().isEmpty()) {
                String lineaPrimera = Log.getLog().getFirst();
                String[] fechaFinal = eliminarCorchetes(lineaPrimera);
                //Obtiene la fecha actual sin corchetes
                String fechaActual = obtenerFecha();
                String[] fechaActualFinal = eliminarCorchetes(fechaActual);
                if (fechaActualFinal[1].equals(fechaFinal[1])) {
                    return true; //MISMO DIA
                }
            }else {
                return true; //NO HAY REGISTROS ANTERIORES
            }
        }catch (IndexOutOfBoundsException index) {
            System.out.println(Constantes.errorFichero + " fichero");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return false; //Distinto dia o hubo un error
    }

    /**
     *
     * @param lineaPrimera cadena de carácteres a evaluar
     * @return fecha de la linea pasada como parámetro eliminando los corchetes de la fecha
     */
    private static String[] eliminarCorchetes(String lineaPrimera) {
        String[] fechas = lineaPrimera.split("]");
        return fechas[0].split("\\[");
    }

    /**
     * Almacena el log actual en un fichero con su fecha correspondiente y lo elimina
     */
    private static void archivarFichero() {
        String fechaFinal = "";
        try {
            if (!Log.getLog().isEmpty()) {
                //COPIA LAS LINEAS DEL FICHERO A UNO CON LA FECHA ANTIGUA
                String lineaPrimera = Log.getLog().getFirst();
                String[] fecha = eliminarCorchetes(lineaPrimera);
                fecha[1] = fecha[1].replace("/", " ");
                //AJUSTA LA FECHA AL FORMATO REQUERIDO yyyyMMDD
                String[] fechas = fecha[1].split(" ");
                //AÑADE EL AÑO
                fechaFinal = fechaFinal + fechas[2];
                //AÑADE EL MES EN EL FORMATO QUE QUEREMOS
                int fechaMes = Integer.valueOf(fechas[1]);
                fechaFinal = fechaFinal + String.format("%02d", fechaMes);
                //AÑADE EL DIA
                fechaFinal = fechaFinal + fechas[0];
                Path rutaLogArchivar = Paths.get(Constantes.rutaLog + "." + fechaFinal);
                Files.write(rutaLogArchivar, (Log.getLog().getFirst() + "\n").getBytes());
                for (int i = 1; i < Log.getLog().size(); i++) {
                    Files.write(rutaLogArchivar, (Log.getLog().get(i) + "\n").getBytes(), StandardOpenOption.APPEND);
                }
                Path rutaLogAntigua = Paths.get(Constantes.rutaLog);
                //ELIMINA EL .LOG ANTIGUO
                Files.delete(rutaLogAntigua);
                //ELIMINA LOS RESTOS DEL .LOG ANTIGUO EN EL PROGRAMA PARA CREAR EL DEL DIA ACTUAL
                Log.eliminarLog();
            }
        }catch (IndexOutOfBoundsException index) {
            System.out.println(Constantes.errorFichero + " fichero");
        }catch (IOException io) {
            System.out.println(Constantes.errorFichero);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
