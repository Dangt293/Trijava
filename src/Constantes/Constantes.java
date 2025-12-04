package Constantes;

/**
 * @author Daniel
 * @version 1.0
 */
public class Constantes {
    //PARA MENSAJES VISUALES
    public static String opcion = "Seleccione una opción";
    public static String opcionMala = "Error, opción no válida, intenteló de nuevo";
    public static String sinRegistros = "No hay datos registrados";
    public static String caracterIncorrecto = "Carácter no válido";
    public static String errorFichero = "Error en la lectura del fichero";
    public static String errorNumeroJug = "Error en el número de jugadores";
    public static String nombrePart = "Indica el nombre del jugador";

    //RONDAS
    public static int rapida = 3;
    public static int corta = 5;
    public static int normal = 10;

    //PARA JUGADORES
    public static int minimoJug = 2;
    public static int maximoJug = 4;
    //PARA JUGADOR CPU
    public static String nombreCPU = "CPU";

    //PREGUNTAS
    public static String respuesta = "Respuesta a la pregunta: ";
    public static String respuestaCPU = "Respuesta CPU: ";
    public static int sumaPuntos = 1;
    public static String acierto = "Correcto, sumas puntos";
    public static String fallo = "Respuesta incorrecta";
    //MASTERMIND
    public static int intentosMasterMind = 3;
    public static int longuitudRespMasterMind = 3;
    //MATES
    public static int minimoDigitosMates = 4;
    public static int maximoDigitosMates = 8;
    public static int valorMinimoMates = 2;
    public static int valorMaximoMates = 12;
    //GEOGRAFÍA
    public static String indicacionesGeo = "Debes elegir la ciudad más proxima en km a la indicada";
    public static String objetivo = "Ciudad objetivo: ";
    public static int opcionesGeo = 4;
    //CONTADOR
    public static int minimoContador = 1;
    public static int maximoContador = 5;
    public static String indicacionesCont = "Debes calcular X segundos con un margen de 0,5 segundos de error";
    public static String tiempo = "Tiempo: ";

    //FICHEROS
    public static String lecturaCorrecta = "OK";
    public static String rutaRanking  = "src/DatosGuardados/ranking.txt";
    public static String rutaPropiedades = "src/Config/config.properties";
    public static String rutaHistorico = "src/DatosGuardados/historico.txt";
    public static String rutaLog = "src/DatosGuardados/salida.log";
    public static String rutaCiudades = "src/Ciudades/ciudades.csv";
    //MENSAJES DEL LOG
    public static String inicioApli = "Se ha iniciado el programa";
    public static String cerrarApli = "Se ha cerrado el programa";
    public static String aciertoPregunta = " ha acertado una pregunta de tipo ";
    public static String falloPregunta = " ha fallado una pregunta de tipo ";
    public static String inicioPartida = " jugadores han iniciado una partida";
    public static String ganador = "Ha ganado la partida ";
    public static String empate = "La partida ha terminado con un empate";
    public static String añadirJugador = "Se ha añadido al jugador: ";
    public static String eliminarJugador = "Se ha eliminado al jugador: ";

    /**
     * Pinta el menú principal del programa
     */
    public static void pintarMenuP() {
        System.out.println("1.- Jugar partida");
        System.out.println("2.- Ranking");
        System.out.println("3.- Histórico");
        System.out.println("4.- Jugadores");
        System.out.println("5.- Salir");
    }
}
