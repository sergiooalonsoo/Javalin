package es.masanz.noviembre.controller;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class InventarioController {

    private static final Logger logger = LogManager.getLogger(WordleController.class);

    // TODO: Implementar controlador
    public InventarioController(){

    }

    // TODO: Enviar inventario y ultimoItem
    public void mostrarInventario(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("inventario", new Object());
        model.put("itemSeleccionado", new Object());
        context.render("/templates/inventario.ftl", model);
    }

    // Modificar la cantidad de un objeto
    public void modificarCantidad(Context context) {
        String objeto = context.formParam("objeto");
        String accion = context.formParam("accion");
        logger.info("El item ha modificar es: "+objeto);
        logger.info("Se debe realizar la siguiente accion: "+accion);
        context.redirect("/inventario");
    }

    // Agregar un nuevo objeto o incrementar su cantidad
    public void agregarObjeto(Context ctx) {
        String objeto = ctx.formParam("nuevoObjeto").trim();
        logger.info("El item que se ha incluido es: "+objeto);
        ctx.redirect("/inventario");
    }
}
