package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotaTest {

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
    void testGetNota() {
        // Crear una instancia de Nota y obtener su path
        String notaPath = nota.getPath();
        
        // Obtener la Nota utilizando el método getNota
        Nota notaObtenida = Nota.getNota(notaPath);
        
        // Verificar que la Nota obtenida no sea null
        assertNotNull(notaObtenida);
        
        // Verificar que el nombre de la Nota obtenida sea igual al esperado
        assertEquals("MiTitulo.html", notaObtenida.getName());
    }
    
    @Test
    void testGetNotaNoExiste() {
        // Intentar obtener una Nota que no existe
        String pathInexistente = "ruta/a/una/nota/que/no/existe.html";
        Nota notaObtenida = Nota.getNota(pathInexistente);
        
        // Verificar que la Nota obtenida sea null
        assertNull(notaObtenida);
    }

    @Test
    public void testEscribirEnArchivo() {
        String contenido = "Este es un texto de prueba";
        nota.anotar(contenido);
        String contenidoLeido = nota.getContenidoHTML();
    
        // Verifica que el contenidoLeido contiene el texto original
        assertTrue(contenidoLeido.contains(contenido));
    }
    
    @Test
    public void testGetContenido() {
        String contenido = "Este es un texto de prueba";
        nota.anotar(contenido);
        String contenidoLeidoMetodo = nota.getContenidoHTML();
    
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

        String contenidoObtenido = nota.getContenidoHTML();
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
        assertEquals("", nota.getContenidoHTML(),
                "El contenido debería ser una cadena vacía para un archivo vacío");

        String contenido = "Este es un contenido de prueba";
        nota.anotar(contenido);
        String contenidoObtenido = nota.getContenidoHTML();
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

    @Test
    void testAgregarImagen() {
        // Ruta de la imagen que utilizarás para el test
        String imagePath = "/ruta/de/tu/imagen.jpg";

        // Llamar al método agregarImagen
        nota.agregarImagen(imagePath);

        // Obtener el contenido actualizado de la nota
        String contenidoActualizado = nota.getContenidoHTML();

        // Verificar si el contenido contiene el div de imagen esperado
        assertTrue(contenidoActualizado.contains("<div id=\"imagen_1\">\n <img src=\"/ruta/de/tu/imagen.jpg\">\n</div>"));
    }
    @Test
    void testMuchoTexto(){
        String texto = "hola soy el texto numero: ";
        String salidaEsperada = "";
        for (int i = 0; i < 20; i++) {
            nota.anotar(texto+Integer.toString(i+1));
            salidaEsperada = salidaEsperada + "<div id=\"div_contenido_"+Integer.toString(i+1)+"\">\n <p>" + texto + Integer.toString(i+1)+"</p>\n</div>\n";
        }
        if (salidaEsperada.endsWith("\n")) {
            salidaEsperada = salidaEsperada.substring(0, salidaEsperada.length() - 1);
        }
        assertEquals(nota.getContenidoHTML(), salidaEsperada);
    }

    @Test
    void testMuchasImagenes(){
        String texto = "hola soy la imagen numero: ";
        String salidaEsperada = "";
        for (int i = 0; i < 20; i++) {
            nota.agregarImagen(texto+Integer.toString(i+1));;
            salidaEsperada = salidaEsperada + "<div id=\"imagen_"+Integer.toString(i+1)+"\">\n <img src=\""+texto+Integer.toString(i+1)+"\">\n</div>\n";
        }
        if (salidaEsperada.endsWith("\n")) {
            salidaEsperada = salidaEsperada.substring(0, salidaEsperada.length() - 1);
        }
        assertEquals(nota.getContenidoHTML(), salidaEsperada);
    }

    @Test
    void testGetName(){
        String nombre = nota.getName();
        assertEquals(nombre, "MiTitulo.html");
    }

    @Test
    void testGetNameNotFile(){
        nota.borrar();
        String nombre = nota.getName();
        assertNull(nombre);
    }
}

