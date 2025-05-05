package es.masanz.noviembre.controller;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BuscaminasController {

    private static final Logger logger = LogManager.getLogger(BuscaminasController.class);

    private int filas;
    private int columnas;
    private int bombas;
    private int[][] tablero;

    // TODO: Implementar controlador
    public BuscaminasController(int filas, int columnas, int bombas){
        this.filas = filas;
        this.columnas = columnas;
        this.bombas = bombas;
    }

    // TODO: Este metodo NO SE TOCA
    public void cargarBuscaminas(Context context) {
        int[][] tablero = generarTablero();
        Map<String, Object> model = new HashMap<>();
        model.put("tablero", tablero);
        context.render("/templates/buscaminas.ftl", model);
    }

    // TODO: AQUI ES DONDE HAREIS LA MAGIA PARA CREAR UN BUSCAMINAS
    private int[][] generarTablero() {
        tablero = new int[filas][columnas];
        Random random = new Random();
        int bombasColocadas = 0;
        while (bombasColocadas < bombas) {
            int fila = random.nextInt(filas);
            int columna = random.nextInt(columnas);
            if (tablero[fila][columna] != -1) {
                tablero[fila][columna] = -1;
                bombasColocadas++;
            }
        }

        // Todo: recorro todas las filas y cuando no sea una bomba llamo a contarBombasAdyacentes.
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                    if (tablero[i][j] != -1) {
                        tablero[i][j] = contarBombasAdyacentes(i, j);
                    }
            }
        }
        return tablero;
    }

    private int contarBombasAdyacentes(int fila, int columna) {
        int bombasAdyacentes = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;

                if (nuevaFila >= 0 && nuevaFila < filas && nuevaColumna >= 0 && nuevaColumna < columnas && tablero[nuevaFila][nuevaColumna] == -1) {
                    bombasAdyacentes++;
                }
            }
        }
        return bombasAdyacentes;
    }

    // TODO: DEBEREIS DEVOLVER EL VALOR DE LA POSICION SOLICITADA
    public void comprobarPosicion(Context context) {
        int fila = Integer.parseInt(context.formParam("fila"));
        int columna = Integer.parseInt(context.formParam("columna"));
        logger.info("Validando la fila: "+fila+" y columna: "+columna);

        int valor = tablero[fila][columna];
        Map<String, Object> response = new HashMap<>();
        response.put("result", valor);
        context.json(response);
    }
}
