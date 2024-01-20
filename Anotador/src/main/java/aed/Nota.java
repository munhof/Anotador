package aed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.nio.file.*;



public class Nota {
    private File nota;
    /**
     * @param String titulo 
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
     * Elimina la nota
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

    /**
     * @return Nota devuelve la nota
     */
    public File getNota() {
        return nota;
    }


    /**
     * @return String pathAbsoluto de la nota si es necesario
     */
    public String getPath(){
        return nota.getAbsolutePath();
    }

    /**
     * @return BasicFileAttributes devuelve los atributos basicos de la nota
     */
    public BasicFileAttributes getBasicAtribute() {
        String pathString = nota.getPath();
        BasicFileAttributes attribute = null;
        try {
            Path path = FileSystems.getDefault().getPath(pathString);
            attribute = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attribute;
    }

    /**
     * @return Date fecha creacion
     */
    public Date getCreationDate(){
        BasicFileAttributes attributes = getBasicAtribute();
        FileTime creationTime = attributes.creationTime();
        Date creationDate = new Date(creationTime.toMillis());
        return creationDate;
    }

    /**
     * @return Date fecha ultima modificacion
     */
    public Date getLastModifyDate(){
        BasicFileAttributes attributes = getBasicAtribute();
        FileTime LastModifyTime = attributes.lastModifiedTime();
        Date LastModifyDate = new Date(LastModifyTime.toMillis());
        return LastModifyDate;
    }

    /**
     * @param contenido 
     * Anota en la nota el contenido de input, agregandolo
     */
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

/**
 * @return devuelve en contenido de la nota
 */
public String getContenido() {
    StringBuilder contenido = new StringBuilder();
    String path = nota.getPath();
    try (BufferedReader lector = new BufferedReader(new FileReader(path))) {
        String linea;
        boolean primeraLinea = true;
        while ((linea = lector.readLine()) != null) {
            if (primeraLinea) {
                primeraLinea = false;
            } else {
                contenido.append("\n");
            }
            contenido.append(linea);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return contenido.toString();
}


}
