package Clases;

import Constantes.Constantes;

import java.util.*;

/**
 * @author Daniel
 * @version 1.0
 */
public class Ranking {
    public static LinkedHashMap <String, Integer> rankingJug = new LinkedHashMap<>();

    /**
     * Muestra el ranking
     */
    public static void mostrarRanking() {
        if (rankingJug.isEmpty()) {
            System.out.println(Constantes.sinRegistros);
        }else {
            System.out.println("--- RANKING ---");
            Iterator<Map.Entry<String, Integer>> iterator = rankingJug.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> siguiente = iterator.next();
                System.out.println(siguiente.getKey() + " " + siguiente.getValue());
            }
        }
    }

    /**
     *
     * @param clave valor que actua como clave del mapa
     * @param valor valor que corresponde a dicha clave
     */
    public static void añadirRegistro(String clave, Integer valor) {
        rankingJug.put(clave, valor);
        actualizarRanking();
    }

    /**
     * Actualiza el ranking para organizarlo de mayor a menor
     */
    public static void actualizarRanking() {
        ArrayList<Map.Entry<String, Integer>> lista = new ArrayList<>(rankingJug.entrySet());
        ArrayList<Map.Entry<String, Integer>> listaFinal = new ArrayList();
        lista.sort(Map.Entry.comparingByValue());
        for (int i = 1; i <= lista.size(); i++) {
            listaFinal.add(lista.get(lista.size() - i));
        }
        // Para comprobaciones
//        for (int i = 0; i < listaFinal.size(); i++) {
//            System.out.println(listaFinal.get(i));
//        }
        rankingJug.clear();
        for (Map.Entry<String, Integer> entrada: listaFinal) {
            rankingJug.put(entrada.getKey(),entrada.getValue());
        }
    }

    /**
     *
     * @param nombre identifica la clave del campo del mapa a eliminar
     */
    public static void eliminar(String nombre) {
        rankingJug.remove(nombre);
        actualizarRanking();
    }

    /**
     * Permite acceder al mapa y escribir nuevas entradas
     * @return devuelve el ranking en forma de string
     */
    public static String devolverRanking() {
        String ranking = "";
        Iterator<Map.Entry<String, Integer>> iter = rankingJug.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = iter.next();
            ranking = ranking + entry.getKey() + " ";
            ranking = ranking + entry.getValue();
            if (!iter.hasNext()) {
                return ranking;
            }
            ranking = ranking + "\n";
        }
        return ranking;
    }
}
