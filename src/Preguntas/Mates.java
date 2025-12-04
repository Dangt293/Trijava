package Preguntas;

import Clases.Jugador;
import Constantes.Constantes;
import Gestionadores.GestionLog;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Daniel
 * @version 1.0
 */
public class Mates{
    private int resp;

    /**
     *
     * @param depuracion indica si el modo depuración está o no activado
     */
    public Mates(boolean depuracion) {
        System.out.println("*************");
        System.out.println("    MATES    ");
        System.out.println("*************");
        this.resp = Integer.valueOf(generarPregunta(depuracion));

    }

    /**
     *
     * @param depuracion indica si el modo depuración está o no activado
     * @return devuelve el resultado a la pregunta
     */
    public String generarPregunta(boolean depuracion) {
        Random rdm = new Random();
        String operacion = "";
        int cont = 0;
        int longuitud = rdm.nextInt(Constantes.minimoDigitosMates,Constantes.maximoDigitosMates + 1);
        for (int i = 0; i < longuitud; i++) {
            operacion = operacion + Integer.toString(rdm.nextInt(Constantes.valorMinimoMates,Constantes.valorMaximoMates + 1)) +" ";
            //Se asegura de que el String no termine con un operador sino con un número
            if (cont < longuitud - 1) {
                switch (rdm.nextInt(0, 3)) {
                    case 0:
                        operacion = operacion + "+ ";
                        break;
                    case 1:
                        operacion = operacion + "- ";
                        break;
                    case 2:
                        operacion = operacion + "* ";
                        break;
                }
            }
            cont++;
        }
        System.out.println("Introduce el resultado de la siguiente operación: " +operacion);
        Expression calcular = new ExpressionBuilder(operacion).build();
        int result = (int) calcular.evaluate();
        if (depuracion) {
            System.out.println(Constantes.respuesta + result);
        }
        return Integer.toString(result);
    }

    /**
     *
     * @param jugador indica que jugador va a responder a la pregunta
     */
    public void jugarPregunta(Jugador jugador) {
        Scanner tcl = new Scanner(System.in);
        int respJug;
        boolean valido;

        do {
            try {
                valido = true;
                respJug = tcl.nextInt();
                if (respJug == this.resp) {
                    System.out.println(Constantes.acierto);
                    jugador.setPuntuacion(jugador.getPuntuacion() + Constantes.sumaPuntos);
                    GestionLog.añadirRegistro(jugador.getNombre() + Constantes.aciertoPregunta + "Mates");
                }else {
                    System.out.println(Constantes.fallo);
                    GestionLog.añadirRegistro(jugador.getNombre() + Constantes.falloPregunta + "Mates");
                }
            } catch (InputMismatchException input) {
                tcl.nextLine();
                System.out.println(Constantes.caracterIncorrecto);
                valido = false;
            }
        }while (!valido);
        System.out.println(Constantes.respuesta+ this.resp);
    }

    /**
     * Simula la pregunta para las CPU´s como si la jugasen
     * @param jugador indica a que jugador le va a corresponder la simulación de la pregunta
     */
    public void simularPregunta(Jugador jugador) {
        System.out.println(Constantes.respuestaCPU+ this.resp);
        System.out.println(Constantes.acierto);
        System.out.println(Constantes.respuesta+ this.resp);
        jugador.setPuntuacion(jugador.getPuntuacion() + Constantes.sumaPuntos);
        GestionLog.añadirRegistro(jugador.getNombre() + Constantes.aciertoPregunta + "Mates");
    }
}
