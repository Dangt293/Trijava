package Gestionadores;

import Clases.Jugador;
import Clases.Ranking;
import Constantes.Constantes;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Daniel
 * @version 1.0
 */
public class GestionJugadores {
    public static ArrayList<Jugador> listaJug = new ArrayList<>();

    /**
     * Gestiona las acciones del usuario dentro del menú de jugadores
     */
    public static void gestionar() {
        Scanner tcl = new Scanner(System.in);
        int respuesta;
        boolean correcto;

        do {
            try {
                do {
                    System.out.println(Constantes.opcion);
                    pintarMenuU();
                    respuesta = tcl.nextInt();
                    tcl.nextLine();
                    switch (respuesta) {
                        case 1:
                            mostarLista();
                            break;
                        case 2:
                            añadirJugador();
                            break;
                        case 3:
                            eliminarJugador();
                            break;
                        case 4:
                            System.out.println("Regresando al menú principal");
                            break;
                        default:
                            System.out.println(Constantes.opcionMala);
                    }
                } while (respuesta != 4);
                correcto = true;
            } catch (InputMismatchException input) {
                tcl.nextLine();
                System.err.println(Constantes.caracterIncorrecto);
                correcto = false;
            } catch (Exception e) {
                e.printStackTrace();
                correcto = false;
            }
        } while (!correcto);
    }

    /**
     * Pinta el menú de "Jugadores"
     */
    private static void pintarMenuU() {
        System.out.println("1.- Ver jugadores");
        System.out.println("2.- Añadir jugador");
        System.out.println("3.- Eliminar jugador");
        System.out.println("4.- Volver");
    }

    /**
     * Pinta la lista de los nombres de los jugadores registrados
     */
    public static void mostarLista() {
        if (listaJug.isEmpty()) {
            System.out.println(Constantes.sinRegistros);
        }else {
            System.out.println("************************");
            System.out.println("       JUGADORES       ");
            System.out.println("************************");
            for (int i = 0; i < listaJug.size(); i++) {
                System.out.println(listaJug.get(i).getNombre());
            }
            System.out.println();
        }
    }

    /**
     * Añade al programa los usuarios que estan en el fichero ranking.txt
     * @param nombre Nombre del jugador
     */
    public static void añadirViaFichero(String nombre) {
        listaJug.add(new Jugador(nombre));
    }

    /**
     * Registra un jugador en el programa
     */
    private static void añadirJugador() {
        Scanner tcl = new Scanner(System.in);
        String nombre;
        System.out.println("Introduzca un nombre");
        nombre = tcl.nextLine().toUpperCase();
        if (comprobarCPU(nombre)){
            nombre = omitirEspaciados(nombre);
            if (comprobarExistencia(nombre, true) && (comprobarString(nombre))) {
                listaJug.add(new Jugador(nombre));
                Ranking.añadirRegistro(nombre, 0);
                System.out.println("Jugador añadido");
                GestionLog.añadirRegistro(Constantes.añadirJugador + nombre);
            }
        }
    }

    /**
     * Comprueba que el nombre del usuario no comienze por CPU
     * @param nombre Nombre del jugador que se quiere verificar
     * @return true si el nombre es válido, false si no es válido
     */
    private static boolean comprobarCPU(String nombre) {
        String tresLetras = "";
        if (nombre.isBlank()) {
            System.out.println("Error, nombre no válido");
            return false;
        }
        try {
            for (int i = 0; i < 3; i++) {
                tresLetras = tresLetras + nombre.charAt(i);
            }
//            System.out.println(tresLetras);
            if (tresLetras.equals("CPU")) {
                System.out.println("Error, el nombre no puede comenzar como CPU");
                return false;
            }
        }catch (StringIndexOutOfBoundsException indexOutOfBounds) {
            indexOutOfBounds.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    /**
     * Comprueba que el nombre del usuario no este ya registrado
     * @param nombre Nombre del jugador que se quiere verificar
     * @return true si el nombre es válido, false si no es válido
     */
    private static boolean comprobarExistencia(String nombre, boolean mostarMensaje) {
        for (int i = 0; i < listaJug.size(); i++) {
            if (listaJug.get(i).getNombre().equals(nombre)) {
                if (mostarMensaje) {
                    System.out.println("Error, el nombre ya existe");
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba que el nombre del usuario contenga al menos una letra
     * @param nombre Nombre del jugador que se quiere verificar
     * @return true si el nombre es válido, false si no es válido
     */
    private static boolean comprobarString(String nombre) {
        try {
            Integer.valueOf(nombre);
            System.out.println("Error, el nombre no puede ser solamente números");
            return false;
        }catch (NumberFormatException numero) {
            return true;
        }
    }

    /**
     * Ajusta el nombre para omitir los espacios
     * @param nombre Nombre del jugador que se quiere ajustar
     * @return el nombre sin espacios
     */
    private static String omitirEspaciados(String nombre) {
        try {
            String nombreSin = "";
            String[] lista = nombre.split(" ");
            for (int i = 0; i < lista.length; i++) {
                nombreSin = nombreSin + lista[i];
            }
            return nombreSin;
        }catch (StringIndexOutOfBoundsException indexOutBounds) {
            return "";
        }
    }

    /**
     * Permite eliminar un jugador de los registros
     */
    private static void eliminarJugador() {
        Scanner tcl = new Scanner(System.in);
        String nombre, confirmacion;
        boolean terminar;
        System.out.println("Introduzca el nombre que desea eliminar");
        nombre = tcl.nextLine().toUpperCase();
        if (!comprobarExistencia(nombre, false)) {
            System.out.println("Se va a eliminar a " +nombre);
            //CONFIRMAR LA ELIMINACIÓN
            do {
                System.out.println("Confirmar la operación (Si | No)");
                confirmacion = tcl.nextLine();
                terminar = true;
                if (confirmacion.equalsIgnoreCase("SI")) {
                    for (int i = 0; i < listaJug.size(); i++) {
                        if (listaJug.get(i).getNombre().equals(nombre)) {
                            listaJug.remove(i);
                            Ranking.eliminar(nombre);
                            System.out.println("Jugador eliminado");
                            GestionLog.añadirRegistro(Constantes.eliminarJugador + nombre);
                        }
                    }
                } else if (confirmacion.equalsIgnoreCase("NO")) {
                    System.out.println("Se ha cancelado la operación");
                } else {
                    System.out.println("Opción no válida");
                    terminar = false;
                }
            }while (!terminar);
        }else {
            System.out.println("El nombre no existe");
        }
    }
}
