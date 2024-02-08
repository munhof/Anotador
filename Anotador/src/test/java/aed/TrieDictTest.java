package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TrieDictTest {

    @Test
    public void testInsertarYBuscar() {
        TrieDict<Integer> trie = new TrieDict<>();

        trie.insertar("hola", 1);
        trie.insertar("adios", 2);
        trie.insertar("java", 3);

        assertEquals(Integer.valueOf(1), trie.buscar("hola"));
        assertEquals(Integer.valueOf(2), trie.buscar("adios"));
        assertEquals(Integer.valueOf(3), trie.buscar("java"));
        assertNull(trie.buscar("python"));
    }

    @Test
    public void testBorrar() {
        TrieDict<Integer> trie = new TrieDict<>();

        trie.insertar("hola", 1);
        trie.insertar("adios", 2);
        trie.insertar("java", 3);

        trie.borrar("hola");
        assertNull(trie.buscar("hola"));
        assertEquals(Integer.valueOf(2), trie.buscar("adios"));
        assertEquals(Integer.valueOf(3), trie.buscar("java"));
    }

    @Test
    public void testGetKeys() {
        TrieDict<Integer> trie = new TrieDict<>();

        trie.insertar("hola", 1);
        trie.insertar("adios", 2);
        trie.insertar("java", 3);

        List<String> keys = trie.getKeys();

        assertEquals(3, keys.size());
        assertTrue(keys.contains("hola"));
        assertTrue(keys.contains("adios"));
        assertTrue(keys.contains("java"));
    }

    @Test
    public void testGetValues() {
        TrieDict<Integer> trie = new TrieDict<>();

        trie.insertar("hola", 1);
        trie.insertar("adios", 2);
        trie.insertar("java", 3);

        List<Integer> values = trie.getValues();

        assertEquals(3, values.size());
        assertTrue(values.contains(1));
        assertTrue(values.contains(2));
        assertTrue(values.contains(3));
    }

    @Test
    public void testCrearKey() {
        TrieDict<Integer> trie = new TrieDict<>();

        trie.crearKey("nueva");

        assertNull(trie.buscar("nueva"));
        assertNull(trie.buscar("no_existente"));
    }

    @Test
    public void testCopiar() {
        TrieDict<Integer> trie = new TrieDict<>();

        trie.insertar("hola", 1);
        trie.insertar("adios", 2);
        trie.insertar("java", 3);

        TrieDict<Integer> copia = trie.copiar();

        assertEquals(trie.buscar("hola"), copia.buscar("hola"));
        assertEquals(trie.buscar("adios"), copia.buscar("adios"));
        assertEquals(trie.buscar("java"), copia.buscar("java"));

        copia.insertar("python", 4);

        assertNull(trie.buscar("python"));
    }
}
