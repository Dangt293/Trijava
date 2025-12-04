package Preguntas;

import Clases.Jugador;
import Constantes.Constantes;
import Gestionadores.GestionLog;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Daniel
 * @version 1.0
 */
public class Contador {

    public Contador() {
        System.out.println("************");
        System.out.println("  CONTADOR ");
        System.out.println("************");
    }

    /**
     *
     * @param jugador indica que jugador va a jugar la pregunta
     */
    public void jugarPregunta(Jugador jugador) {
        Random rdm = new Random();
        Scanner tcl = new Scanner(System.in);
        int objetivo = rdm.nextInt(Constantes.minimoContador,(Constantes.maximoContador + 1));
        System.out.println(Constantes.indicacionesCont);
        System.out.println("Tiempo a hacer: " + objetivo);
        System.out.println("Enter para iniciar el contador, y vuelve a pulsarlo para terminarlo");
        tcl.nextLine();
        //OBTIENE EL TIEMPO DEL INICIO
        LocalTime inicioTiempo = LocalTime.now();
        System.out.println("Contando...");
        tcl.nextLine();
        //OBTIENE EL TIEMPO DEL FINAL
        LocalTime finalTiempo = LocalTime.now();
        //CALCULA LA DIFERECIA
        long diferencia = Duration.between(inicioTiempo, finalTiempo).toMillis();
        double totalDif = ((double) diferencia) / 1000;
        totalDif = redondearDoble(totalDif);
        //COMPRUEBA EL RESULTADO
        if (totalDif > objetivo + 0.5 || totalDif < objetivo - 0.5) {
            System.out.println(Constantes.fallo);
            System.out.println(Constantes.tiempo +(totalDif));
            GestionLog.añadirRegistro(jugador.getNombre() + Constantes.falloPregunta + "Contador");
        }else {
            System.out.println(Constantes.acierto);
            System.out.println(Constantes.tiempo +(totalDif));
            jugador.setPuntuacion(jugador.getPuntuacion() + Constantes.sumaPuntos);
            GestionLog.añadirRegistro(jugador.getNombre() + Constantes.aciertoPregunta + "Contador");
        }
    }

    /**
     * Simula la pregunta para las CPU´s como si la jugasen
     * @param jugador indica a que jugador le va a corresponder la simulación de la pregunta
     */
    public void simularPregunta(Jugador jugador) {
        Random rdm = new Random();
        int objetivo = rdm.nextInt(Constantes.minimoContador,(Constantes.maximoContador + 1));
        System.out.println(Constantes.indicacionesCont);
        System.out.println("Tiempo a hacer: " + objetivo);
        int fallo = rdm.nextInt(1,3);
        switch (fallo) {
            case 1: //FALLA POR ABAJO DEL MINIMO
                System.out.println(Constantes.fallo);
                System.out.println(Constantes.tiempo +rdm.nextDouble(0, objetivo - 0.1));
                GestionLog.añadirRegistro(jugador.getNombre() + Constantes.falloPregunta + "Contador");
                break;
            case 2: //FALLA POR ARRIBA DEL MAXIMO
                System.out.println(Constantes.fallo);
                System.out.println(Constantes.tiempo +rdm.nextDouble( objetivo + 0.1, Constantes.maximoContador +2));
                GestionLog.añadirRegistro(jugador.getNombre() + Constantes.falloPregunta + "Contador");
                break;
        }
    }

    /**
     *
     * @param numero número decimal al que se le quieren dejar solo dos decimales
     * @return el número con solo dos decimales
     */
    private double redondearDoble(double numero) {
        numero = Math.round(numero * 100);
        numero = numero / 100;
        return numero;
    }
}
