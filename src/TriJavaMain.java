import Clases.Historico;
import Clases.Ranking;
import Constantes.Constantes;
import Gestionadores.GestionAplicacion;
import Gestionadores.GestionJugadores;

import Gestionadores.GestionPartida;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TriJavaMain {
    public static void main(String[] args) {
        GestionPartida gestPartida = new GestionPartida();
        Scanner tcl = new Scanner(System.in);
        int respPrinc;
        boolean correcto, depuracion;

        GestionAplicacion.inicarAplicacion();
        depuracion = GestionAplicacion.leerPropiedades();
        do {
            try {
                do {
                    System.out.println("---------------------------------");
                    System.out.println(Constantes.opcion);
                    Constantes.pintarMenuP();
                    respPrinc = tcl.nextInt();
                    switch (respPrinc) {
                        case 1:
                            gestPartida.jugarPartida(depuracion);
                            break;
                        case 2:
                            Ranking.mostrarRanking();
                            break;
                        case 3:
                            Historico.mostarHistorico();
                            break;
                        case 4:
                            GestionJugadores.gestionar();
                            break;
                        case 5:
                            GestionAplicacion.cerrarAplicacion();
                            break;
                        default:
                            System.out.println(Constantes.opcionMala);
                    }
                } while (respPrinc != 5);
                correcto = true;
            }catch (InputMismatchException input) {
                tcl.nextLine();
                System.err.println(Constantes.caracterIncorrecto);
                correcto = false;
            }catch (Exception ex) {
                ex.printStackTrace();
                correcto = false;
            }
        }while (!correcto);
    }
}