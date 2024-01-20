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
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void setUp() {
        // Se ejecuta antes de cada prueba
        nota = new Nota("testNota");
    }

    @AfterEach
    void tearDown() {
        // Se ejecuta después de cada prueba
        nota.borrar();
    }

    @Test
    public void testCrear() {
        File archivo = new File("testNota.txt");
        assertTrue(archivo.exists());
    }


    @Test
    public void testBorrar() {
        nota.borrar();
        File archivo = new File("testNota.txt");
        assertFalse(archivo.exists());
    }

    @Test
    public void testCrearError() {
        File archivo = new File("/invalid/path/testNota.txt");
        assertFalse(archivo.exists());
    }

    @Test
    public void testBorrarNoExistente() {
        Nota nota = new Nota("testNota");
        nota.borrar();
        assertFalse(nota.getNota().exists());
    }

        @Test
    public void testEscribirEnArchivo() {
        String path = nota.getPath();
        String contenido = "Este es un texto de prueba";
        nota.anotar(contenido);

        // Verificamos si el contenido fue escrito correctamente
        String contenidoLeido = leerContenidoArchivo(path);
        assertEquals(contenido, contenidoLeido);
    }

    @Test
    public void testGetContenido() {
        String path = nota.getPath();
        String contenido = "Este es un texto de prueba";
        nota.anotar(contenido);

        // Verificamos si el contenido fue escrito correctamente
        String contenidoLeido = leerContenidoArchivo(path);
        String contenidoLeidoMetodo = nota.getContenido();
        assertEquals(contenidoLeidoMetodo, contenidoLeido);

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
        // Caso 4: Prueba anotar y obtener contenido, asegurándose de que el contenido no se sobrescriba

        // Anotar un contenido inicial
        String contenidoInicial = "Contenido inicial";
        nota.anotar(contenidoInicial);

        // Anotar un contenido adicional
        String contenidoAdicional = "Contenido adicional";
        nota.anotar(contenidoAdicional);

        // Verificar que el contenido obtenido incluya ambos contenidos
        String contenidoObtenido = nota.getContenido();
        assertTrue(contenidoObtenido.contains(contenidoInicial), "El contenido inicial debería estar presente");
        assertTrue(contenidoObtenido.contains(contenidoAdicional), "El contenido adicional debería estar presente");
    }

    @Test
    void testGetBasicAttributeForNonExistentFile() {
        // Caso 5: Prueba obtener atributos básicos para un archivo inexistente

        // Borrar la nota para asegurarse de que el archivo no exista
        nota.borrar();

        // Obtener los atributos básicos
        BasicFileAttributes attributes = nota.getBasicAtribute();

        // Verificar que los atributos son nulos (ya que el archivo no existe)
        assertNull(attributes, "Los atributos deberían ser nulos para un archivo inexistente");
    }

    @Test
    void testGetContenidoArchivoVacio() throws IOException {
        // Caso de prueba para un archivo vacío

        // Verificar que inicialmente el contenido sea una cadena vacía
        assertEquals("", nota.getContenido(), "El contenido debería ser una cadena vacía para un archivo vacío");

        // Anotar un contenido en el archivo
        String contenido = "Este es un contenido de prueba";
        nota.anotar(contenido);

        // Verificar que el contenido obtenido coincide con el contenido anotado
        assertEquals(contenido, nota.getContenido(), "El contenido debería ser el mismo que el contenido anotado");
    }

    @Test
    void testGetContenidoMultipleLineas() throws IOException {
        // Caso de prueba para contenido con múltiples líneas
    
        // Anotar un contenido con múltiples líneas en el archivo
        String contenido = "Línea 1\n" +
                          "Línea 2\n" +
                          "Línea 3";
        nota.anotar(contenido);
        String leido = nota.getContenido();
        // Verificar que el contenido obtenido coincida con el contenido anotado
        assertEquals(contenido, leido);
    }

    @Test
    void testGetContenidoArchivoInexistente() {
        // Caso de prueba para un archivo inexistente

        // Borrar la nota para asegurarse de que el archivo no exista
        nota.borrar();

        // Verificar que obtener contenido para un archivo inexistente devuelve una cadena vacía
        assertEquals("", nota.getContenido(), "El contenido debería ser una cadena vacía para un archivo inexistente");
    }

    
}

