package es.masanz.noviembre.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArchivoManager {

    private static final Logger logger = LogManager.getLogger(ArchivoManager.class);

    private String filePath;

    // TODO: Implementar el constructor
    public ArchivoManager(String filePath) {
        this.filePath = filePath;
    }

    // TODO: Devolver todo el texto del archivo
    public List<String> leerDocumento() {
        List<String> listaLineas = new ArrayList<>();
        try (FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null)
                listaLineas.add(linea);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return listaLineas;
    }

    // TODO: Devolver una linea aleatoria del archivo
    public String leerLineaAleatoria() {
        Random rnd = new Random();
        List<String> listaLineas = new ArrayList<>(leerDocumento());
        int numeroAleatorio = rnd.nextInt(0, listaLineas.size());
        return listaLineas.get(numeroAleatorio);
    }

    // TODO: Sustituye la linea del archivo que contenga el texto indicado por el nuevo valor
    public void modificarLinea(String textoLinea, String nuevaLinea) {
        logger.info("Buscando la linea: "+textoLinea);
    }

    // TODO: Incluye la linea indicada al final del archivo
    public void escribirLinea(String nuevaLinea) {

    }

    // TODO: Elimina la linea que coincida con textoLinea
    public void eliminarLinea(String textoLinea) {

    }
}