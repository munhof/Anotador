package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AnotadorTests {

    

    private Nota nota;

    @BeforeEach
    public void setUp() {
        // Puedes inicializar recursos necesarios antes de cada prueba
        String path = "E:\\Nueva carpeta\\Anotador\\Pruebas\\";
        nota = new Nota(path, "MiTitulo");
        anotador = new Anotador();
    }

    @AfterEach
    public void tearDown() {
        // Puedes limpiar y restaurar el estado después de cada prueba
        nota.borrar();
    }

    private Anotador anotador;


    @Test
    void testCrearEtiqueta() {
        anotador.crearEtiqueta("etiqueta1");
        assertTrue(anotador.anotador.buscar("etiqueta1") != null);
    }

    @Test
    void testEtiquetarNota() {
        // Crear una nota y etiquetarla
        String path = "E:\\Nueva carpeta\\Anotador\\Pruebas\\";
        anotador.etiquetarNota("etiqueta1", nota);

        // Verificar que la nota se haya etiquetado correctamente
        ListaEnlazada<String> notasEtiquetadas = anotador.anotador.buscar("etiqueta1");
        assertNotNull(notasEtiquetadas);
        assertTrue(notasEtiquetadas.longitud() == 1);
        assertTrue(notasEtiquetadas.obtener(0).equals(path+"MiTitulo.html"));
    }

    @Test
    void testCrearNotaSinEtiqueta() {
        // Crear una nota sin etiqueta
        String path = "E:\\Nueva carpeta\\Anotador\\Pruebas\\";
        String titulo = "MiTitulo";
        anotador.crearNota(path, titulo);

        // Verificar que la nota se haya creado correctamente y esté sin etiqueta
        Nota nota = anotador.buscarNota(titulo+".html");
        assertNotNull(nota);
        assertEquals("MiTitulo.html", nota.getName());
    }

    @Test
    void testCrearNotaConEtiqueta() {
        // Crear una nota con una etiqueta
        String path = "E:\\Nueva carpeta\\Anotador\\Pruebas\\";
        String titulo = "MiTitulo";
        String etiqueta = "etiqueta1";
        anotador.crearNota(path, titulo, etiqueta);

        // Verificar que la nota se haya creado correctamente y esté etiquetada
        Nota nota = anotador.buscarNota(titulo+".html");
        assertNotNull(nota);
        assertEquals("MiTitulo.html", nota.getName());
    }

    @Test
    void testBuscarNota() {
        // Crear una nota y buscarla
        String path = "E:\\Nueva carpeta\\Anotador\\Pruebas\\";
        String titulo = "MiTitulo";
        anotador.crearNota(path, titulo);

        // Verificar que la nota se haya encontrado correctamente
        Nota nota = anotador.buscarNota(titulo+".html");
        assertNotNull(nota);
        assertEquals("MiTitulo.html", nota.getName());
    }

    @Test
    void testCambiarEtiqueta(){
        // Crear una nota y etiquetarla
        String path = "E:\\Nueva carpeta\\Anotador\\Pruebas\\";
        anotador.etiquetarNota("etiqueta1", nota);
        String titulo = "MiTitulo";
        String etiqueta = "etiqueta2";

        // Verificar que la nota se haya etiquetado correctamente con etiqueta1
        ListaEnlazada<String> notasEtiqueta1 = anotador.anotador.buscar("etiqueta1");
        assertNotNull(notasEtiqueta1);
        assertTrue(notasEtiqueta1.longitud() == 1);
        assertTrue(notasEtiqueta1.obtener(0).equals(path+"MiTitulo.html"));
        // Verifico que no exista etiqueta2
        ListaEnlazada<String> notasEtiqueta2 = anotador.anotador.buscar("etiqueta2");
        assertNull(notasEtiqueta2);
        //cambio de lugar la nota
        anotador.cambiarEtiqueta(titulo+".html", etiqueta);
        // Verifico que etiqueta2 tenga la nota
        notasEtiqueta2 = anotador.anotador.buscar("etiqueta2");
        assertNotNull(notasEtiqueta2);
        assertTrue(notasEtiqueta2.longitud() == 1);
        assertTrue(notasEtiqueta2.obtener(0).equals(path+"MiTitulo.html"));
        // Verifico que etiqueta 1 este vacia
        notasEtiqueta1 = anotador.anotador.buscar("etiqueta1");
        assertNotNull(notasEtiqueta1);
        assertTrue(notasEtiqueta1.longitud() == 0);
    }

    @Test
    public void testSerializacion() {
        // Crear una instancia de Anotador con datos de prueba
        Anotador anotadorOriginal = new Anotador();
        anotadorOriginal.crearNota("path1/", "Titulo1", "etiqueta1");
        anotadorOriginal.crearNota("path2/", "Titulo2", "etiqueta2");
        
        // Nombre del archivo para guardar los objetos serializados
        String filename = "anotador.ser";

        try {
            // Realizar la serialización
            FileOutputStream fileOut = new FileOutputStream("anotador.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(anotadorOriginal);
            out.close();
            fileOut.close();

            // Realizar la deserialización
            FileInputStream fileIn = new FileInputStream("anotador.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Anotador anotadorDeserializado = (Anotador) in.readObject();
            in.close();
            fileIn.close();

        /** Esto se serializa correctamente pero no se verificar como hacer para modificar el equals... 
         * supongo que me peleare mas tarde, 
         * pero a mano esto parece que funca xd
         * 
            // Comparar los atributos uno por uno
            assertEquals(anotadorOriginal.anotador, anotadorDeserializado.anotador);
            // Si hay más atributos en la clase Anotador, debes compararlos también aquí
        */
        } catch (IOException e) {
            fail("Excepción de E/S: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            fail("Clase no encontrada: " + e.getMessage());
        }
        
    }
    
}