package es.masanz.noviembre.controller;

import es.masanz.noviembre.MainApp;
import es.masanz.noviembre.data.Pregunta;
import es.masanz.noviembre.manager.ArchivoManager;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizController {

    private static final Logger logger = LogManager.getLogger(QuizController.class);

    private final int TOTAL_PREGUNTAS = 5;
    private List<Pregunta> preguntas;
    private int puntuacion;
    private ArchivoManager archivoQuiz;

    // TODO: Inicializar controlador
    public QuizController(){
        this.archivoQuiz = new ArchivoManager(MainApp.QUIZ_PATH);
        this.puntuacion = 0;
        prepararPreguntas();
    }

    private List<Pregunta> prepararPreguntas() {
        preguntas = new ArrayList<>();

        for (int i = 0; i < TOTAL_PREGUNTAS; i++) {
            String linea = archivoQuiz.leerLineaAleatoria();
            String[] lineaEntera = linea.split(";");
            String[] opciones = {lineaEntera[1], lineaEntera[2], lineaEntera[3], lineaEntera[4]};
            String respuestaCorrecta = lineaEntera[5];
            preguntas.add(new Pregunta(lineaEntera[0], opciones, respuestaCorrecta));
        }
        return preguntas;
    }

    // TODO: Enviar pregunta y opciones a la vista y sino la puntuacion
    public void mostrarPregunta(Context context) {
        Map<String, Object> model = new HashMap<String, Object>();

        if (!preguntas.isEmpty()) {
            Pregunta pregunta = preguntas.getFirst();
            model.put("pregunta", pregunta);
            model.put("preguntaTexto", pregunta.getPregunta());
            model.put("opciones", pregunta.getOpciones());
            context.sessionAttribute("preguntaActual", pregunta);

        } else {
            model.put("puntuacion", puntuacion);
        }

        context.render("/templates/quiz.ftl", model);
    }

    // TODO: Procesar la respuesta de la pregunta y redirigir a la vista principal
    public void procesarRespuesta(Context context) {
        String respuestaUsuario = context.formParam("respuesta");
        Pregunta pregunta = context.sessionAttribute("preguntaActual");
        if (pregunta != null && pregunta.getRespuestaCorrecta().equals(respuestaUsuario)) {
            puntuacion++;
        }

        if (!preguntas.isEmpty()) {
            preguntas.removeFirst();
        }

        context.redirect("/quiz");
    }

    // TODO: Reinicializar los atributos necesarios para volver a empezar y redirigir a la vista principal
    public void empezarNuevamente(Context ctx) {
        puntuacion = 0;
        prepararPreguntas();
        ctx.redirect("/quiz");
    }
}
