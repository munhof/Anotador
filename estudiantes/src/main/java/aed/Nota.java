package aed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Nota {
    private File nota;
    /**
     * @param titulo 
     * Recibe el titulo de una nota y la crea utilizando ese titulo como un archivo .txt
     */
    public Nota(String titulo) {
        nota = new File(titulo + ".txt");
        try {
            // Crear el archivo si no existe
            if (nota.createNewFile()) {
                System.out.println("Archivo creado: " + nota.getName());
            } else {
                System.out.println("El archivo ya existe.");
            }
        } catch (Exception e) {
            System.out.println("Error al crear el archivo.");
            e.printStackTrace();
        }
    }


    /**
     * toma la nota y la elimina
     */
    public void borrar() {
        if (nota.exists()) {
            try {
                if (nota.delete()) {
                System.out.println("Archivo eliminado: " + nota.getName());
                }
            }
            catch(Exception e) {
                System.out.println("Error al eliminar el archivo.");
                e.printStackTrace();
            }
        } else {
            System.out.println("El archivo no existe.");
        }
    }

    // Getter para acceder a la variable 'nota' si es necesario
    public File getNota() {
        return nota;
    }

    // Getter para acceder al pathAbsoluto de la nota si es necesario
    public String getPath(){
        return nota.getAbsolutePath();
    }

    public void anotar(String contenido){
        try {
            FileWriter escritor = new FileWriter(nota.getPath(), true);

            BufferedWriter bufferEscritor = new BufferedWriter(escritor);

            bufferEscritor.write(contenido);

            bufferEscritor.newLine();

            bufferEscritor.close();

            System.out.println("Contenido escrito en el archivo correctamente.");

        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String contenidoNota(String rutaArchivo) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenido.toString().trim(); // Eliminamos cualquier espacio en blanco adicional al final
    }
}
