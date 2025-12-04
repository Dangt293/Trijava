package Gestionadores;

import Clases.Jugador;
import Clases.Ranking;
import Constantes.Constantes;
import Preguntas.Contador;
import Preguntas.Geografia;
import Preguntas.MasterMind;
import Preguntas.Mates;

import java.util.*;

/**
 * @author Daniel
 * @version 1.0
 */
public class GestionPartida {
    private ArrayList<Jugador> listaParticipantes;
    private int rondas;

    public GestionPartida() {
        this.listaParticipantes = new ArrayList<>();
        this.rondas = 0;
    }

    /**
     *
     * @param depuracion indica si el modo depuración está activo o no
     */
    public void jugarPartida(boolean depuracion) {
        String historico = "";
        this.listaParticipantes.clear();
        do {
        }while (!seleccionParticipantes());
        do {
            this.rondas = seleccionModo();
        }while (this.rondas == 0);
        GestionLog.añadirRegistro(listaParticipantes.size() + Constantes.inicioPartida);
        //JUEGA TODAS LAS RONDAS
        for (int i = 0; i < this.rondas; i++) {
            //JUEGA CADA JUGADOR DENTRO DE LA RONDA
            for (int j = 0; j < this.listaParticipantes.size(); j++) {
                System.out.println();
                System.out.println("Ronda " + (i + 1) + ", turno de " + this.listaParticipantes.get(j).getNombre());
                jugarRonda(this.listaParticipantes.get(j), depuracion);
            }
            mostarParticipantes();
        }
        confirmarGanador();
        //ACTUALIZAR RANKING E HISTÓRICO
        for (int i = 0; i < listaParticipantes.size(); i++) {
            Iterator<Map.Entry<String, Integer>> iter = Ranking.rankingJug.entrySet().iterator();
            //ACTUALIZA EL HISTORICO
            historico = historico + listaParticipantes.get(i).getNombre()+ " " +listaParticipantes.get(i).getPuntuacion() +" ";
            while (iter.hasNext()) {
                Map.Entry<String, Integer> entry = iter.next();
                if (entry.getKey().equalsIgnoreCase(listaParticipantes.get(i).getNombre())) {
                    Ranking.rankingJug.put(entry.getKey(), entry.getValue() + listaParticipantes.get(i).getPuntuacion());
                }
            }
        }
        //REESTABLECE LA PUNTUACION
        for (int i = 0; i < listaParticipantes.size(); i++) {
            listaParticipantes.get(i).setPuntuacion(0);
        }
        GestionAplicacion.escribirHistorico(historico);
        Ranking.actualizarRanking();
    }

    /**
     *
     * @return true si todos los participantes se han elegido, false si no
     */
    private boolean seleccionParticipantes() {
        Scanner tcl = new Scanner(System.in);
        int jugadores, jugadoresPers;
        String nombreJug;
        int contCPU = 0;

        try {
            do {
                System.out.println("Cuántos jugadores tendrá la partida (mínimo 2, máximo 4)");
                jugadores = tcl.nextInt();
                if (jugadores < Constantes.minimoJug || jugadores > Constantes.maximoJug) {
                    System.out.println(Constantes.errorNumeroJug);
                }
            } while (jugadores < Constantes.minimoJug || jugadores > Constantes.maximoJug);
            do {
                System.out.println("Cuántos jugadores serán personas");
                jugadoresPers = tcl.nextInt();
                if(jugadoresPers > jugadores || jugadoresPers < 0 || jugadoresPers > GestionJugadores.listaJug.size()) {
                    System.out.println(Constantes.errorNumeroJug+ ". No hay suficientes jugadores registrados");
                }
            } while (jugadoresPers > jugadores || jugadoresPers < 0 || jugadoresPers > GestionJugadores.listaJug.size());
            tcl.nextLine(); //Limpia el buffer
            //AÑADE LOS JUGADORES
            for (int i = 0; i < jugadoresPers; i++) {
                do {
                    GestionJugadores.mostarLista();
                    System.out.println(Constantes.nombrePart + (i + 1));
                    nombreJug = tcl.nextLine();
                }while (!buscarJugador(nombreJug));
            }
            //AÑADE LAS CPUs RESTANTES
            while (this.listaParticipantes.size() < jugadores) {
                contCPU++;
                this.listaParticipantes.add(new Jugador(Constantes.nombreCPU + contCPU, false));
            }
            barajearJugadores();
            mostarParticipantes();
        }catch (InputMismatchException input) {
            System.out.println(Constantes.caracterIncorrecto);
            return false;
        }catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Busca el nombre del jugador en la lista de jugadores registrados
     * @param nombre el nombre del jugador que se añadirá como participante
     * @return true si el jugador existe, false si no
     */
    private boolean buscarJugador(String nombre) {
        int cont = 0;
        boolean encontrado = false;
        while (cont < GestionJugadores.listaJug.size() && !encontrado) {
            if (GestionJugadores.listaJug.get(cont).getNombre().equalsIgnoreCase(nombre)) {
                if (!comprobarParticipacion(nombre)) {
                    this.listaParticipantes.add(GestionJugadores.listaJug.get(cont));
                    System.out.println("Participante añadido");
                }else {
                    System.out.println("El jugador ya esta participando");
                    return false;
                }
                encontrado = true;
            }
            cont++;
        }
        if (!encontrado) {
            System.out.println("El jugador no existe");
            return false;
        }
        return true;
    }

    /**
     * Comprueba que el jugador no este participando ya
     * @param nombre el nombre del jugador que va a participar en el juego
     * @return true si el jugador estaba ya participando, false si no
     */
    private boolean comprobarParticipacion(String nombre) {
        for (int i = 0; i < this.listaParticipantes.size(); i++) {
            if (this.listaParticipantes.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return el número de rondas a jugar
     */
    private int seleccionModo() {
        Scanner tcl = new Scanner(System.in);
        int resp;

            try {
                System.out.println(Constantes.opcion);
                System.out.println("1.- Partida rápida (3 rondas)");
                System.out.println("2.- Partida corta (5 rondas)");
                System.out.println("3.- Partida normal (10 rondas)");
                resp = tcl.nextInt();
                switch (resp) {
                    case 1:
                        return Constantes.rapida;
                    case 2:
                        return Constantes.corta;
                    case 3:
                        return Constantes.normal;
                    default:
                        System.out.println(Constantes.opcionMala);
                }
                tcl.nextLine(); //LIMPIA EL BUFFER
            } catch (InputMismatchException input) {
                tcl.nextLine();
                System.out.println(Constantes.caracterIncorrecto);
            }
        return 0;
    }

    /**
     * Barajea el orden de los jugadores
     */
    private void barajearJugadores() {
        System.out.println("Cambiando el orden de turnos...");
        Collections.shuffle(listaParticipantes);
    }

    /**
     * Muesta los participantes con su puntuación en pantalla
     */
    public void mostarParticipantes() {
        System.out.println();
        System.out.println("---- PUNTUACIÓN ----");
        for (int i = 0; i < this.listaParticipantes.size(); i++) {
            System.out.print(this.listaParticipantes.get(i).getNombre()+ " ");
            System.out.println(this.listaParticipantes.get(i).getPuntuacion());
        }
        System.out.println();
    }

    /**
     *
     * @param jugador el jugador que va a jugar la ronda
     * @param depuracion indica si el modo depuración está o no activo
     */
    private void jugarRonda(Jugador jugador, boolean depuracion){
        Random rdm = new Random();
        int pregunta;


        pregunta = rdm.nextInt(1,5);
        switch (pregunta) {
            case 1: //Pregunta Mates
                Mates mates = new Mates(depuracion);
                if (jugador.isPersona()) {
                    mates.jugarPregunta(jugador);
                }else {
                    mates.simularPregunta(jugador);
                }
                break;
            case 2: //Pregunta MasterMind
                MasterMind masterMind = new MasterMind(depuracion);
                if (jugador.isPersona()) {
                    masterMind.jugarPregunta(jugador);
                }else {
                    masterMind.simularPregunta(jugador);
                }
                break;
            case 3: //Pregunta Geografía
                Geografia geografia = new Geografia(depuracion);
                if (jugador.isPersona()) {
                    geografia.jugarPregunta(jugador);
                }else {
                    geografia.simularPregunta(jugador);
                }
                break;
            case 4: //Pregunta Contador
                Contador contador = new Contador();
                if (jugador.isPersona()) {
                    contador.jugarPregunta(jugador);
                }else {
                    contador.simularPregunta(jugador);
                }
                break;
        }
    }

    /**
     * Revisa la lista de participantes y calcula quien tiene más puntos
     */
    private void confirmarGanador() {
        ArrayList<Jugador> ganadores = new ArrayList<>();
        int maxPunt = 0;
        //COMPRUEBA LA PUNTUACION MÁS ALTA
        for (int i = 0; i < this.listaParticipantes.size(); i++) {
            if (this.listaParticipantes.get(i).getPuntuacion() > maxPunt) {
                maxPunt = this.listaParticipantes.get(i).getPuntuacion();
            }
        }
        //AÑADE A UNA LISTA LOS JUGADORES CON DICHA PUNTUACIÓN
        for (int i = 0; i < this.listaParticipantes.size(); i++) {
            if (this.listaParticipantes.get(i).getPuntuacion() == maxPunt) {
                ganadores.add(this.listaParticipantes.get(i));
            }
        }
        if (ganadores.size() == 1) {
            System.out.println(Constantes.ganador +ganadores.getFirst().getNombre());
            GestionLog.añadirRegistro(Constantes.ganador +ganadores.getFirst().getNombre());
        }else {
            System.out.println(Constantes.empate);
            GestionLog.añadirRegistro(Constantes.empate);
        }
    }
}
