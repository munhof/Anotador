package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

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
        Nota nota = anotador.buscarNota(titulo);
        assertNotNull(nota);
        assertEquals("mi_nota.html", nota.getName());
    }

    @Test
    void testCrearNotaConEtiqueta() {
        // Crear una nota con una etiqueta
        String path = "ruta/a/mi/nota.html";
        String titulo = "mi_nota";
        String etiqueta = "etiqueta1";
        anotador.crearNota(path, titulo, etiqueta);

        // Verificar que la nota se haya creado correctamente y esté etiquetada
        Nota nota = anotador.buscarNota(titulo);
        assertNotNull(nota);
        assertEquals("mi_nota.html", nota.getName());
    }

    @Test
    void testBuscarNota() {
        // Crear una nota y buscarla
        String path = "ruta/a/mi/nota.html";
        String titulo = "mi_nota";
        anotador.crearNota(path, titulo);

        // Verificar que la nota se haya encontrado correctamente
        Nota nota = anotador.buscarNota(titulo);
        assertNotNull(nota);
        assertEquals("mi_nota.html", nota.getName());
    }
}