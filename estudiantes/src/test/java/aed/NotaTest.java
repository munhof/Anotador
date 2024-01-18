package aed;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import aed.Nota;

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

    @Test
    public void testCrear() {
        Nota nota = new Nota("testNota");
        File archivo = new File("testNota.txt");
        assertTrue(archivo.exists());
    }


    @Test
    public void testBorrar() {
        Nota nota = new Nota("testNota");
        nota.borrar();
        File archivo = new File("testNota.txt");
        assertFalse(archivo.exists());
    }

    @Test
    public void testCrearError() {
        Nota nota = new Nota("testNota");
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
        Nota nota = new Nota("testNota");
        String path = nota.getPath();
        String contenido = "Este es un texto de prueba";
        nota.anotar(contenido);

        // Verificamos si el contenido fue escrito correctamente
        String contenidoLeido = leerContenidoArchivo(path);
        assertEquals(contenido, contenidoLeido);
        nota.borrar();
    }

    // Método para leer el contenido de un archivo y devolverlo como una cadena

}

