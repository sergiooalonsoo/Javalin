package es.masanz.noviembre.data;

public class Item {

    private String nombre;
    private int cantidad;

    // TODO: Implementa el constructor
    public Item(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    // TODO: Implementa los getters, setter y toString que consideres que hagan falta

    // region gs
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    // endregion

    // TODO: Incrementa en 1 la cantidad del item
    public void incrementarCantidad() {
        cantidad++;
    }

    // TODO: Reduce en 1 la cantidad del item
    public void decrementarCantidad() {
        cantidad--;
    }
}