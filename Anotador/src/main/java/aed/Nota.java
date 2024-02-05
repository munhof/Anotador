package aed;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Nota {
    private File nota;


    /**
     * @param path String
     * genera una carpeta en la dirreccion
     */
    public void generarCarpeta(String path){
        Path carpeta = Paths.get(path);
        if (!Files.exists(carpeta)) {
            try {
                Files.createDirectories(carpeta);
                System.out.println("Carpeta creada correctamente.");
            } catch (Exception e) {
                System.out.println("Error al crear la carpeta: " + e.getMessage());
            }
        } else {
            System.out.println("La carpeta ya existe.");
        }
    }


    /**
     * @param path String
     * @param titulo String
     * Constructor del metodo, genera una nota en path con el titulo dado
     */
    public Nota(String path ,String titulo) {
        generarCarpeta(path);
        nota = new File(path + titulo + ".html");
        try {
            if (nota.createNewFile()) {
                System.out.println("Archivo creado: " + nota.getName());
            } else {
                System.out.println("El archivo ya existe.");
            }
        } catch (Exception e) {
            System.out.println("Error al crear el archivo.");
            e.printStackTrace();
        }
        crearEstructura();
    }
    
    /**
     * @param titulo
     * @param contenido
     * crea una Nota apartir del titulo y su contenido
     */
    public Nota(String path, String titulo, String contenido){
        Nota nota = new Nota(path, titulo);
        nota.anotar(contenido);
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
    public void anotar(String contenido) {
        try {
            Document document = Jsoup.parse(nota, "UTF-8");
            Element body = document.body();
    
            // Encuentra la cantidad de divs existentes
            int divCount = body.select("div[id^=div_contenido_]").size();
    
            // Crea un nuevo div con un id único
            Element nuevoDiv = new Element("div");
            nuevoDiv.attr("id", "div_contenido_" + (divCount + 1));
    
            // Divide el contenido en líneas y agrega párrafos con saltos de línea
            String[] lineas = contenido.split("\\n");
            for (String linea : lineas) {
                Element parrafo = new Element("p");
                parrafo.text(linea);
                nuevoDiv.appendChild(parrafo);
                // Agrega un <br> después de cada línea, excepto la última
                if (!linea.equals(lineas[lineas.length - 1])) {
                    nuevoDiv.append("<br>");
                }
            }
    
            // Agrega el nuevo div al cuerpo del documento
            body.appendChild(nuevoDiv);
    
            // Actualiza el contenido del archivo
            FileWriter escritor = new FileWriter(nota.getPath(), false);
            BufferedWriter bufferEscritor = new BufferedWriter(escritor);
            bufferEscritor.write(document.html());
            bufferEscritor.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void agregarImagen(String imagePath) {
        try {
            Document document = Jsoup.parse(nota, "UTF-8");
            Element body = document.body();

            // Encuentra el último div de imágenes
            Elements divsImagen = body.select("div[id^=imagen_]");
            int divCount = divsImagen.size();

            // Crea un nuevo div de imagen con un id único
            Element nuevoDivImagen = new Element("div");
            nuevoDivImagen.attr("id", "imagen_" + (divCount + 1));

            // Agrega la imagen al nuevo div
            Element imagen = new Element("img");
            imagen.attr("src", imagePath);
            nuevoDivImagen.appendChild(imagen);

            // Agrega el nuevo div de imagen al cuerpo del documento
            body.appendChild(nuevoDivImagen);

            // Actualiza el contenido del archivo
            FileWriter escritor = new FileWriter(nota.getPath(), false);
            BufferedWriter bufferEscritor = new BufferedWriter(escritor);
            bufferEscritor.write(document.html());
            bufferEscritor.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void crearEstructura() {
        try {
            Document document = Jsoup.parse(nota, "UTF-8");

            // Agrega la etiqueta <head> si no existe
            Element head = document.head();
            if (head == null) {
                head = document.appendElement("head");
            }

            // Agrega el título
            head.appendElement("title").text("Nota: " + nota.getName());

            // Agrega la etiqueta <body> si no existe
            Element body = document.body();
            if (body == null) {
                body = document.appendElement("body");
            }

            // Actualiza el contenido del archivo
            FileWriter escritor = new FileWriter(nota.getPath(), false);
            BufferedWriter bufferEscritor = new BufferedWriter(escritor);
            bufferEscritor.write(document.html());
            bufferEscritor.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * @return devuelve en contenido de la nota en el body
     */
    public String getContenidoHTML() {
        StringBuilder contenido = new StringBuilder();
        String path = nota.getPath();
    
        try {
            Document document = Jsoup.parse(new File(path), "UTF-8");
            Element body = document.body();
    
            // Obtiene el contenido dentro del body
            contenido.append(body.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return contenido.toString();
    }

    public String getHeadHTML(){
        StringBuilder header = new StringBuilder();
        String path = nota.getPath();
    
        try {
            Document document = Jsoup.parse(new File(path), "UTF-8");
            Element head = document.head();
    
            // Obtiene el contenido dentro del head
            header.append(head.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return header.toString();
    }
}
