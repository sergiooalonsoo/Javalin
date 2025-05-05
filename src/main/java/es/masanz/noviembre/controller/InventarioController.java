package es.masanz.noviembre.controller;

import es.masanz.noviembre.MainApp;
import es.masanz.noviembre.data.Item;
import es.masanz.noviembre.data.Pregunta;
import es.masanz.noviembre.manager.ArchivoManager;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class InventarioController {

    private static final Logger logger = LogManager.getLogger(WordleController.class);

    private Map<String, Item> inventario;
    private Item ultimoItem;
    private ArchivoManager inventarioManager;

    // TODO: Implementar controlador
    public InventarioController(){
        this.inventarioManager = new ArchivoManager(MainApp.INVENTARIO_PATH);
        this.prepararInventario();
        this.ultimoItem = inventario.get(inventario.size() - 1);
    }

    private Map<String, Item> prepararInventario() {
        inventario = new LinkedHashMap<>();
        List<String> listaLineas = new ArrayList<>(inventarioManager.leerDocumento());

        for (String listaLinea : listaLineas) {
            String[] lineaSeparada = listaLinea.split(";");
            String nombre = lineaSeparada[0];
            int cantidad = Integer.parseInt(lineaSeparada[1]);
            inventario.put(nombre, new Item(nombre, cantidad));
        }

        return inventario;
    }

    // TODO: Enviar inventario y ultimoItem
    public void mostrarInventario(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("inventario", inventario);
        if (ultimoItem != null) {
            model.put("itemSeleccionado", ultimoItem);
        }
        context.render("/templates/inventario.ftl", model);
    }

    // Modificar la cantidad de un objeto
    public void modificarCantidad(Context context) {
        String objeto = context.formParam("objeto");
        String accion = context.formParam("accion");
        Item item = inventario.get(objeto);
        String lineaAntigua = item.getNombre() + ";" + item.getCantidad();

        if (inventario.containsKey(objeto)) {
            if (accion.equals("incrementar")) {
                inventario.get(objeto).incrementarCantidad();
            } else if (accion.equals("decrementar")) {
                inventario.get(objeto).decrementarCantidad();
            }

            String lineaNueva = item.getNombre() + ";" + item.getCantidad();

            if (inventario.get(objeto).getCantidad() <= 0) {
                inventario.remove(objeto);
                inventarioManager.eliminarLinea(lineaAntigua);
            } else {
                ultimoItem = inventario.get(objeto);
                inventarioManager.modificarLinea(lineaAntigua, lineaNueva);
            }
        }

        context.redirect("/inventario");
    }

    // Agregar un nuevo objeto o incrementar su cantidad
    public void agregarObjeto(Context ctx) {
        String objeto = ctx.formParam("nuevoObjeto").trim();

        if (inventario.containsKey(objeto)) {
            Item item = inventario.get(objeto);
            String lineaAntigua = item.getNombre() + ";" + item.getCantidad();
            inventario.get(objeto).incrementarCantidad();
            String lineaNueva = item.getNombre() + ";" + item.getCantidad();
            inventarioManager.modificarLinea(lineaAntigua, lineaNueva);

        } else {
            Item nuevoItem = new Item(objeto, 1);
            inventario.put(objeto, nuevoItem);
            String linea = inventario.get(objeto).getNombre() + ";" + inventario.get(objeto).getCantidad();
            inventarioManager.escribirLinea(linea);
        }
        ctx.redirect("/inventario");
    }
}
