package es.masanz.noviembre.controller;

import es.masanz.noviembre.MainApp;
import es.masanz.noviembre.manager.ArchivoManager;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WordleController {

    private static final Logger logger = LogManager.getLogger(WordleController.class);

    private final int MAX_INTENTOS = 5;
    private final int NUM_LETRAS = 5;
    private String palabra;
    private ArchivoManager archivoWordle;


    // TODO: Implementar controlador
    public WordleController(){
        archivoWordle = new ArchivoManager(MainApp.WORDLE_PATH);
    }

    // TODO: Devolver la informacion solicitada
    public void cargarWordle(Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        palabra = archivoWordle.leerLineaAleatoria();
        model.put("longitudPalabra", NUM_LETRAS);
        model.put("intentosMaximos", MAX_INTENTOS);
        model.put("palabra", palabra);
        context.render("/templates/wordle.ftl", model);
    }

    // TODO: Comprobar la palabra que han enviado
    public void comprobarPalabra(Context context) {
        String guess = context.formParam("guess");
        Map<String, Object> response = new HashMap<>();

        if (palabra == null) {
            response.put("message", "El juego no ha sido inicializado. Por favor, vuelve a cargar la página.");
            context.json(response);
            return;
        }

        if (guess.length() != NUM_LETRAS) {
            response.put("message", "Por favor, ingresa una palabra válida de " + NUM_LETRAS + " letras.");
            context.json(response);
            return;
        }

        guess = guess.toLowerCase();
        String palabraLower = palabra.toLowerCase();

        char[] letrasIntento = guess.toCharArray();
        char[] letrasReales = palabraLower.toCharArray();
        String[] resultado = new String[NUM_LETRAS];
        int[] conteoLetras = new int[26];

        for (char c : letrasReales) {
            conteoLetras[c - 'a']++;
        }

        for (int i = 0; i < NUM_LETRAS; i++) {
            if (letrasIntento[i] == letrasReales[i]) {
                resultado[i] = "green";
                conteoLetras[letrasIntento[i] - 'a']--;
            }
        }

        for (int i = 0; i < NUM_LETRAS; i++) {
            if (resultado[i] == null) {
                int idx = letrasIntento[i] - 'a';
                if (conteoLetras[idx] > 0) {
                    resultado[i] = "yellow";
                    conteoLetras[idx]--;
                } else {
                    resultado[i] = "red";
                }
            }
        }

        response.put("result", resultado);
        context.json(response);
    }

}