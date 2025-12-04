package Preguntas;
import Clases.Jugador;
import Constantes.Constantes;
import Gestionadores.GestionLog;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Daniel
 * @version 1.0
 */
public class MasterMind{
    private String respuesta;

    /**
     *
     * @param depuracion indica si el modo depuración está o no activo
     */
    public MasterMind(boolean depuracion) {
        System.out.println("************");
        System.out.println(" MASTERMIND ");
        System.out.println("************");
        this.respuesta = generarPregunta(depuracion);
    }

    /**
     * @param depuracion indica si el modo depuración esta o no activo
     * @return el número en forma de String que el jugador deberá de intentar adivinar
     */
    public String generarPregunta(boolean depuracion) {
        Random rdm = new Random();
        String resp = "";
        int concaternar;
        for (int i = 0; i < Constantes.longuitudRespMasterMind; i++) {
            do {
                concaternar = rdm.nextInt(0,10);
            }while (resp.contains(Integer.toString(concaternar)));
            resp = resp + concaternar;
        }
        if (depuracion) {
            System.out.println(Constantes.respuesta + resp);
        }
        return resp;
    }

    /**
     *
     * @param jugador Indica que jugador concreto esta jugando la pregunta
     */
    public void jugarPregunta(Jugador jugador) {
        boolean acertada = false;
        int cont = 0;
        while (cont < Constantes.intentosMasterMind && !acertada) {
            System.out.println("Intentos restantes: " +(Constantes.intentosMasterMind - cont));
            if (intento()) {
                acertada = true;
            }
            cont++;
        }
        if (acertada) {
            jugador.setPuntuacion(jugador.getPuntuacion() + Constantes.sumaPuntos);
            GestionLog.añadirRegistro(jugador.getNombre() + Constantes.aciertoPregunta + "MasterMind");
        }else {
            System.out.println(Constantes.fallo+ ".No quedan intentos");
            GestionLog.añadirRegistro(jugador.getNombre() + Constantes.falloPregunta + "MasterMind");
        }
        System.out.println(Constantes.respuesta + this.respuesta);
    }

    /**
     * Simula la pregunta para las CPU´s como si la jugasen
     * @param jugador indica a que jugador le va a corresponder la simulación de la pregunta
     */
    public void simularPregunta(Jugador jugador) {
        Random rdm = new Random();
        String intentoAleat;
        boolean acertada = false;
        int cont = 0;
        while (cont < Constantes.intentosMasterMind && !acertada) {
            System.out.println("Intentos restantes: " +(Constantes.intentosMasterMind - cont));
            intentoAleat = "";
            for (int i = 0; i < Constantes.longuitudRespMasterMind; i++) {
                intentoAleat = intentoAleat + Integer.toString(rdm.nextInt(0,10));
            }
            System.out.println(Constantes.respuestaCPU +intentoAleat);
            if (this.respuesta.equalsIgnoreCase(intentoAleat)) {
                System.out.println(Constantes.acierto);
                acertada = true;
            }else {
                System.out.println("Número incorrecto");
            }
            cont++;
        }
        if (acertada) {
            jugador.setPuntuacion(jugador.getPuntuacion() + Constantes.sumaPuntos);
            GestionLog.añadirRegistro(jugador.getNombre() + Constantes.aciertoPregunta + "MasterMind");
        }else {
            System.out.println("No quedan intentos");
            GestionLog.añadirRegistro(jugador.getNombre() + Constantes.falloPregunta + "MasterMind");
        }
        System.out.println(Constantes.respuesta + this.respuesta);
    }

    /**
     *
     * @return Devuelve un true si se ha acertado el resultado, o un false en caso contrario
     */
    private boolean intento() {
        Scanner tcl = new Scanner(System.in);
        boolean valido;
        String respJugador;
        int contCorrecto, contMalPosicion;
        //EL PRIMER BUCLE "DO" SE ASEGURA DE QUE EL USUARIO NO INTRODUZCA LETRAS SIN RESTAR INTENTO
        do {
            try {
                valido = true;
                do {
                    contCorrecto = 0;
                    contMalPosicion = 0;
                    System.out.println("Introduce un número de 3 cifras");
                    respJugador = tcl.nextLine();
                    Integer.valueOf(respJugador);
                    if (respJugador.length() != Constantes.longuitudRespMasterMind) {
                        System.out.println("Error en la longuitud de cifras");
                    }
                } while (respJugador.length() != Constantes.longuitudRespMasterMind);
                for (int j = 0; j < Constantes.longuitudRespMasterMind; j++) {
                    if (this.respuesta.charAt(j) == respJugador.charAt(j)) {
                        contCorrecto++;
                    } else if (this.respuesta.contains(Character.toString(respJugador.charAt(j)))) {
                        contMalPosicion++;
                    }
                }
                if (contCorrecto == 0 && contMalPosicion == 0) {
                    System.out.println("Ningún digito esta en la secuencia");
                } else if (contCorrecto == 3) {
                    System.out.println(Constantes.acierto);
                    return true;
                } else {
                    System.out.println(contCorrecto + " cifras bien posicionadas y " + contMalPosicion + " correcta pero mal ubicada");
                }
            } catch (InputMismatchException input) {
                tcl.nextLine();
                System.out.println(Constantes.caracterIncorrecto);
                valido = false;
            }catch (NumberFormatException noNumerico) {
                System.out.println(Constantes.caracterIncorrecto);
                valido = false;
            }
        }while (!valido);
        return false;
    }
}
