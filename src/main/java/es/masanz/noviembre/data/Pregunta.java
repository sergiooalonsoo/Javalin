package es.masanz.noviembre.data;

public class Pregunta {

    private String pregunta;
    private String[] opciones = new String[4];
    private String respuestaCorrecta;

    // TODO: Implementa el constructor
    public Pregunta(String pregunta, String[] opciones, String respuestaCorrecta) {
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    // TODO: Implementa los getters, setter y toString que consideres que hagan falta

    // region gs
    public String getPregunta() {
        return pregunta;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public String[] getOpciones() {
        return opciones;
    }

    // endregion
}
