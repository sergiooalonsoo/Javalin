package es.masanz.noviembre;

import es.masanz.noviembre.controller.BuscaminasController;
import es.masanz.noviembre.controller.InventarioController;
import es.masanz.noviembre.controller.QuizController;
import es.masanz.noviembre.controller.WordleController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.rendering.template.JavalinFreemarker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainApp {

    private static final Logger logger = LogManager.getLogger(MainApp.class);

    public static final String WORDLE_PATH = "src/main/resources/files/wordle.txt";
    public static final String QUIZ_PATH = "src/main/resources/files/quiz.txt";
    public static final String INVENTARIO_PATH = "src/main/resources/files/inventario.txt";

    public static void main(String[] args) {

        // Podeis utilizar un logger para sacar informacion en consola como con un sout.
        // Vereis que logger tiene los metodos: trace, debug, info, warn, error y fatal
        // todos estos mensajes muestran informacion por pantalla, pero cada uno se debe
        // emplear en una situacion concreta, debereis analizarlo
        // TODO: no emplear mensajes System.out.println y emplear el logger en su lugar
        logger.info("ARRANCANDO APLICACION");

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinFreemarker());
        }).start(4567);

        app.get("/", new Handler() {
            @Override
            public void handle(Context context) throws Exception {
                Map<String, Object> model = new HashMap<>();
                Map<String, String> juegos = new LinkedHashMap<>();
                juegos.put("Wordle", "wordle");
                juegos.put("Buscaminas", "buscaminas");
                juegos.put("Quiz", "quiz");
                juegos.put("Inventario", "inventario");

                // TODO: Enviar los datos necesarios para la pantalla inicial
//                model.put("mensajito", "Ver y hacer que funcione bien");
//                model.put("otraVariable", "Puedes enviar tantos datos como quieras");
//                model.put("llamalaComoQuieras", 12345);
                model.put("titulo", "Bienvenido a los Juegos Interactivos");
                model.put("enlaces", juegos);
                context.render("/templates/index.ftl", model);
            }
        });

        // TODO: Analizar y comprender la necesidad de un controller para cada tipo de vista que se vaya a implementar

        BuscaminasController buscaminasController = new BuscaminasController(10, 10, 10);

        // TODO: Analizar y comprender el formato sin lambda
        app.get("/buscaminas", new Handler() {
            @Override
            public void handle(Context context) throws Exception {
                buscaminasController.cargarBuscaminas(context);
            }
        });

        // TODO: Analizar y comprender el formato con lambda
        app.post("/comprobar_posicion", context -> {
            buscaminasController.comprobarPosicion(context);
        });

        app.post("/otro_buscaminas", context -> {
            context.redirect("/buscaminas");
        });

        WordleController wordleController = new WordleController();

        app.get("/wordle", new Handler() {
            @Override
            public void handle(Context context) throws Exception {
                wordleController.cargarWordle(context);
            }
        });

        app.post("/comprobar_palabra", context -> {
            wordleController.comprobarPalabra(context);
        });

        app.post("/otra_palabra", context -> {
            context.redirect("/wordle");
        });

        QuizController quiz = new QuizController();

        app.get("/quiz", ctx -> {
            quiz.mostrarPregunta(ctx);
        });

        app.post("/responder", ctx -> {
            quiz.procesarRespuesta(ctx);
        });

        app.post("/otro_quiz", ctx -> {
            quiz.empezarNuevamente(ctx);
        });

        InventarioController inventario = new InventarioController();

        app.get("/inventario", ctx -> {
            inventario.mostrarInventario(ctx);
        });

        app.post("/modificar_item", ctx -> {
            inventario.modificarCantidad(ctx);
        });

        app.post("/agregar_item", ctx -> {
            inventario.agregarObjeto(ctx);
        });

    }
}