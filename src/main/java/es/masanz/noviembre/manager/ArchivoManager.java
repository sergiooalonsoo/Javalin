package es.masanz.noviembre.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        try {
            List<String> lineas = new ArrayList<>(leerDocumento());
            boolean modificada = false;
            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).equals(textoLinea)) {
                    lineas.set(i, nuevaLinea);
                    modificada = true;
                    break;
                }
            }

            if (modificada) {
                Path path = Paths.get(filePath);
                Files.write(path, lineas);
            }
        } catch (Exception e) {
            logger.error("Error");
        }
    }

    // TODO: Incluye la linea indicada al final del archivo
    public void escribirLinea(String nuevaLinea) {
        try {
            List<String> lineas = new ArrayList<>(leerDocumento());
            lineas.add(nuevaLinea);
            Path path = Paths.get(filePath);
            Files.write(path, lineas);
        } catch (Exception e) {
            logger.error("Error");
        }
    }

    // TODO: Elimina la linea que coincida con textoLinea
    public void eliminarLinea(String textoLinea) {
        try {
            List<String> lineas = new ArrayList<>(leerDocumento());
            boolean eliminada = false;
            for (int i = lineas.size() - 1; i >= 0; i--) {
                if (lineas.get(i).equals(textoLinea)) {
                    lineas.remove(i);
                    eliminada = true;
                }
            }

            if (eliminada) {
                Path path = Paths.get(filePath);
                Files.write(path, lineas);
            }
        } catch (Exception e) {
            logger.error("Error");
        }
    }
}