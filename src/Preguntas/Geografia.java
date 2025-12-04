package Preguntas;

import Clases.Jugador;
import Constantes.Constantes;
import Gestionadores.GestionLog;

import java.util.*;

/**
 * @author Daniel
 * @version 1.0
 */
public class Geografia {
    private static ArrayList<String> ciudades = new ArrayList<>();
    private char resp;
    private HashMap<String, Double> opciones = new HashMap<>();

    /**
     *
     * @param depuracion indica si el modo depuración está o no activo
     */
    public Geografia(boolean depuracion) {
        System.out.println("*************");
        System.out.println("  GEOGRAFIA ");
        System.out.println("*************");
        generarPregunta(depuracion);
    }

    public static void setCiudades(ArrayList<String> fichero) {
        ciudades = fichero;
    }

    /**
     *
     * @param depuracion indica si el modo depuración está o no activo
     */
    public void generarPregunta(boolean depuracion) {
        try {
            //SE SELECCIONA UNA CIUDAD Y SE ELIMINA DEL ARRAY AUXILAR
            Random rdm = new Random();
            String[] ciudad;
            String objetivo, clave;
            double longitud, latitud, longitud2, latitud2, kilometros;
            ArrayList<String> ciudadesAux = new ArrayList<>();
            for (int i = 0; i < ciudades.size(); i++) {
                ciudadesAux.add(ciudades.get(i));
            }
            int linea = rdm.nextInt(1, ciudadesAux.size());
            ciudad = ciudadesAux.get(linea).split(",");
            objetivo = ciudad[0];
            latitud = Double.valueOf(ciudad[1]);
            longitud = Double.valueOf(ciudad[2]);
            System.out.println(Constantes.indicacionesGeo);
            System.out.println(Constantes.objetivo +objetivo);
            ciudadesAux.remove(linea);
            //SELECCIONA LAS CUATRO OPCIONES
            for (int i = 0; i < Constantes.opcionesGeo; i++) {
                linea = rdm.nextInt(1,ciudadesAux.size());
                ciudad = ciudadesAux.get(linea).split(",");
                clave = ciudad[0];
                latitud2 = Double.valueOf(ciudad[1]);
                longitud2 = Double.valueOf(ciudad[2]);
                kilometros = calcularDistancia(longitud, latitud, longitud2, latitud2);
                opciones.put(clave,kilometros);
                ciudadesAux.remove(linea);
            }
            this.resp = comprobarRespuesta();
            if (depuracion) {
                System.out.println(Constantes.respuesta + this.resp);
            }
        }catch (IndexOutOfBoundsException index) {
            System.out.println("Error al generar pregunta");
            index.printStackTrace();
        }catch (NumberFormatException number){
            System.out.println("Error en el fichero ciudades");
            number.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param jugador indica que jugador va a jugar la pregunta
     */
    public void jugarPregunta(Jugador jugador) {
        Scanner tcl = new Scanner(System.in);
        char respUsu;
        boolean correcto;
        do {
            try {
                do {
                    mostarOpciones();
                    respUsu = tcl.nextLine().charAt(0);
                    if (respUsu != 'a' && respUsu != 'b' && respUsu != 'c' && respUsu != 'd') {
                        System.out.println(Constantes.opcionMala);
                    }
                }while (respUsu != 'a' && respUsu != 'b' && respUsu != 'c' && respUsu != 'd');
                if (respUsu == this.resp) {
                    System.out.println(Constantes.acierto);
                    jugador.setPuntuacion(jugador.getPuntuacion() + Constantes.sumaPuntos);
                    GestionLog.añadirRegistro(jugador.getNombre() + Constantes.aciertoPregunta + "Geografía");
                }else {
                    System.out.println(Constantes.fallo);
                    System.out.println(Constantes.respuesta+ this.resp);
                    GestionLog.añadirRegistro(jugador.getNombre() + Constantes.falloPregunta + "Geografía");
                }
                mostarOpcionesCompletas();
                correcto = true;
            } catch (InputMismatchException input) {
                System.out.println(Constantes.caracterIncorrecto);
                correcto = false;
            }catch (StringIndexOutOfBoundsException indexBounds) {
                System.out.println(Constantes.opcionMala);
                correcto = false;
            }catch (Exception ex) {
                ex.printStackTrace();
                correcto = false;
            }
        }while (!correcto);
    }

    /**
     * Simula la pregunta para las CPU´s como si la jugasen
     * @param jugador indica a que jugador le va a corresponder la simulación de la pregunta
     */
    public void simularPregunta(Jugador jugador) {
        Random rdm = new Random();
        char letra = ' ';
        int indice = rdm.nextInt(0,4);
        switch (indice) {
            case 0:
                letra = 'a';
                break;
            case 1:
                letra = 'b';
                break;
            case 2:
                letra = 'c';
                break;
            case 3:
                letra = 'd';
                break;
        }
        mostarOpciones();
        System.out.println(Constantes.respuestaCPU+ letra);
        if (letra == this.resp) {
            System.out.println(Constantes.acierto);
            jugador.setPuntuacion(jugador.getPuntuacion() + Constantes.sumaPuntos);
            GestionLog.añadirRegistro(jugador.getNombre() + Constantes.aciertoPregunta + "Geografía");
        }else {
            System.out.println(Constantes.fallo);
            System.out.println(Constantes.respuesta+ this.resp);
            GestionLog.añadirRegistro(jugador.getNombre() + Constantes.falloPregunta + "Geografía");
        }
        mostarOpcionesCompletas();
    }

    /**
     *
     * @param lon1 longuitud del país 1
     * @param lat1 latitud del país 1
     * @param lon2 longuitud del país 2
     * @param lat2 latituda del país 2
     * @return la distancia en kilometros entre los dos puntos
     */
    private double calcularDistancia(double lon1, double lat1, double lon2, double lat2) {
        double earthRadius = 6371;

        lon1 = Math.toRadians(lon1);
        lat1 = Math.toRadians(lat1);
        lon2 = Math.toRadians(lon2);
        lat2 = Math.toRadians(lat2);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double sinlat = Math.sin(dlat / 2);
        double sinlon = Math.sin(dlon / 2);

        double a = (sinlat * sinlat) + Math.cos(lat1) * Math.cos(lat2) * (sinlon * sinlon);
        double c = 2 * Math.asin(Math.min(1.0, Math.sqrt(a)));

        double distancia = earthRadius * c;
        distancia = Math.round(distancia * 100);
        distancia = distancia / 100;
        return distancia;
    }

    /**
     *
     * @return comprueba que letra corresponde con la respuesta al país más cercano
     */
    private char comprobarRespuesta() {
        double cercano = 0;
        boolean primero = true;
        int cont = 0;
        char letra = ' ';
        //COMPRUEBO CUAL ES EL MÁS CERCANO
        Iterator<Map.Entry<String, Double>> iterator = this.opciones.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Double> entry = iterator.next();
            if (primero) {
                cercano = entry.getValue();
                primero = false;
            }else if (entry.getValue() < cercano) {
                cercano = entry.getValue();
            }
        }
        //ASIGNA LA LETRA CORRESPONDIENTE A LA RESPUESTA
        Iterator<Map.Entry<String, Double>> iter = this.opciones.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Double> entry = iter.next();
            if (entry.getValue() == cercano) {
                switch (cont) {
                    case 0:
                        letra = 'a';
                        break;
                    case 1:
                        letra = 'b';
                        break;
                    case 2:
                        letra = 'c';
                        break;
                    case 3:
                        letra = 'd';
                        break;
                }
            }
            cont++;
        }
        return letra;
    }

    /**
     * Muesta las opciones y su distancia al objetivo
     */
    private void mostarOpcionesCompletas() {
        int cont = 0;
        char letra = ' ';
        Iterator<Map.Entry<String, Double>> iterator = this.opciones.entrySet().iterator();
        while (iterator.hasNext()){
            switch (cont) {
                case 0:
                    letra = 'a';
                    break;
                case 1:
                    letra = 'b';
                    break;
                case 2:
                    letra = 'c';
                    break;
                case 3:
                    letra = 'd';
                    break;
            }
            Map.Entry<String, Double> entry = iterator.next();
            System.out.println(letra+ ") " +entry.getKey()+ " km: " +entry.getValue());
            cont++;
        }
    }

    /**
     * Muestra las opciones SIN su distancia al objetivo
     */
    private void mostarOpciones() {
        int cont = 0;
        char letra = ' ';
        Iterator<Map.Entry<String, Double>> iterator = this.opciones.entrySet().iterator();
        while (iterator.hasNext()){
            switch (cont) {
                case 0:
                    letra = 'a';
                    break;
                case 1:
                    letra = 'b';
                    break;
                case 2:
                    letra = 'c';
                    break;
                case 3:
                    letra = 'd';
                    break;
            }
            Map.Entry<String, Double> entry = iterator.next();
            System.out.println(letra+ ") " +entry.getKey());
            cont++;
        }
    }
}
