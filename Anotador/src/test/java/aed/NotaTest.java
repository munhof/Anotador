package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.shape.Path;

public class NotaTest {
    private String leerContenidoArchivo(String rutaArchivo) {
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


    private Nota nota;

    @BeforeEach
    public void setUp() {
        // Puedes inicializar recursos necesarios antes de cada prueba
        String path = "E:\\Nueva carpeta\\Anotador\\Pruebas\\";
        nota = new Nota(path, "MiTitulo");
    }

    @AfterEach
    public void tearDown() {
        // Puedes limpiar y restaurar el estado después de cada prueba
        nota.borrar();
    }

    @Test
    public void testGenerarCarpeta() {
        assertTrue(nota.getNota().exists());
    }

    @Test
    public void testCrear() {
        assertTrue(nota.getNota().exists());
    }

    @Test
    public void testBorrar() {
        nota.borrar();
        assertFalse(nota.getNota().exists());
    }

    @Test
    public void testCrearError() {
        nota.borrar();
        assertFalse(nota.getNota().exists());
    }

    @Test
    public void testBorrarNoExistente() {
        nota.borrar();
        assertFalse(nota.getNota().exists());
    }

    @Test
    public void testEscribirEnArchivo() {
        String contenido = "Este es un texto de prueba";
        nota.anotar(contenido);
        String contenidoLeido = nota.getContenido();
    
        // Verifica que el contenidoLeido contiene el texto original
        assertTrue(contenidoLeido.contains(contenido));
    }
    
    @Test
    public void testGetContenido() {
        String contenido = "Este es un texto de prueba";
        nota.anotar(contenido);
        String contenidoLeidoMetodo = nota.getContenido();
    
        // Verifica que el contenidoLeidoMetodo contiene el texto original
        assertTrue(contenidoLeidoMetodo.contains(contenido));
    }

    @Test
    void testGetBasicAttribute() {
        BasicFileAttributes attributes = nota.getBasicAtribute();
        assertNotNull(attributes, "Los atributos no deben ser nulos");
    }

    @Test
    void testGetCreationDate() {
        Date creationDate = nota.getCreationDate();
        assertNotNull(creationDate, "La fecha de creación no debe ser nula");
    }

    @Test
    void testGetLastModifyDate() {
        Date lastModifyDate = nota.getLastModifyDate();
        assertNotNull(lastModifyDate, "La fecha de última modificación no debe ser nula");
    }

    @Test
    void testAnotarYGetContenido() {
        String contenidoInicial = "Contenido inicial";
        nota.anotar(contenidoInicial);

        String contenidoAdicional = "Contenido adicional";
        nota.anotar(contenidoAdicional);

        String contenidoObtenido = nota.getContenido();
        assertTrue(contenidoObtenido.contains(contenidoInicial), "El contenido inicial debería estar presente");
        assertTrue(contenidoObtenido.contains(contenidoAdicional), "El contenido adicional debería estar presente");
    }

    @Test
    void testGetBasicAttributeForNonExistentFile() {
        nota.borrar();
        BasicFileAttributes attributes = nota.getBasicAtribute();
        assertNull(attributes, "Los atributos deberían ser nulos para un archivo inexistente");
    }

    @Test
    void testGetContenidoArchivoVacio() throws IOException {
        assertEquals("", nota.getContenido(),
                "El contenido debería ser una cadena vacía para un archivo vacío");

        String contenido = "Este es un contenido de prueba";
        nota.anotar(contenido);
        String contenidoObtenido = nota.getContenido();
        assertTrue(contenidoObtenido.contains(contenido), "El contenido adicional debería estar presente");
        
    }

    @Test
    void testGetContenidoMultipleLineas() throws IOException {
        String contenido = "Línea 1\n" + "Línea 2\n" + "Línea 3";
        nota.anotar(contenido);
        String leido = nota.getContenidoHTML();
        String contenidoEsperado = "<div id=\"div_contenido_1\">\n <p>Línea 1</p>\n <br>\n <p>Línea 2</p>\n <br>\n <p>Línea 3</p>\n</div>";
        assertEquals(contenidoEsperado, leido);
    }

    @Test
    void testGetContenidoArchivoInexistente() {
        nota.borrar();
        assertEquals("", nota.getContenidoHTML(), "El contenido debería ser una cadena vacía para un archivo inexistente");
    }

    @Test
    void testGetHead(){
        String contenidoEsperado = "<title>Nota: MiTitulo.html</title>";
        String leido = nota.getHeadHTML();
        assertEquals(contenidoEsperado, leido);
    }

    @Test
    void testGetHeadArchivoInexistente() {
        nota.borrar();
        assertEquals("", nota.getHeadHTML(), "El contenido debería ser una cadena vacía para un archivo inexistente");
    }
}

