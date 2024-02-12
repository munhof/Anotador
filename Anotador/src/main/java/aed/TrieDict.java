package aed;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TrieDict<T> implements Serializable {

    class NodoTrie implements Serializable{
        HashMap<Character, NodoTrie> children;
        T value;

        public NodoTrie() {
            this.children = new HashMap<>();
            this.value = null;
        }
    }

    NodoTrie root;

    public TrieDict() {
        this.root = new NodoTrie();
    }

    public void insertar(String etiqueta, T value) {
        NodoTrie nodoActual = root;
        for (char c : etiqueta.toCharArray()) {
            if (!nodoActual.children.containsKey(c)) {
                nodoActual.children.put(c, new NodoTrie());
            }
            nodoActual = nodoActual.children.get(c);
        }
        nodoActual.value = value;
    }

    public T buscar(String etiqueta) {
        NodoTrie nodoActual = root;
        for (char c : etiqueta.toCharArray()) {
            if (!nodoActual.children.containsKey(c)) {
                return null;
            }
            nodoActual = nodoActual.children.get(c);
        }
        return nodoActual.value;
    }

        public void borrar(String etiqueta) {
        borrarRecursivo(root, etiqueta, 0);
    }

    private boolean borrarRecursivo(NodoTrie nodoActual, String etiqueta, int nivel) {
        if (nivel == etiqueta.length()) {
            if (nodoActual.value != null) {
                nodoActual.value = null;
                return nodoActual.children.isEmpty();
            }
            return false;
        }

        char caracter = etiqueta.charAt(nivel);
        NodoTrie siguienteNodo = nodoActual.children.get(caracter);
        if (siguienteNodo == null)
            return false;

        boolean debeBorrarNodo = borrarRecursivo(siguienteNodo, etiqueta, nivel + 1);

        if (debeBorrarNodo) {
            nodoActual.children.remove(caracter);
            return nodoActual.children.isEmpty();
        }
        return false;
    }

    public List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        getKeysRecursivo(root, "", keys);
        return keys;
    }

    private void getKeysRecursivo(NodoTrie nodoActual, String prefijo, List<String> keys) {
        if (nodoActual.value != null) {
            keys.add(prefijo);
        }

        for (char c : nodoActual.children.keySet()) {
            getKeysRecursivo(nodoActual.children.get(c), prefijo + c, keys);
        }
    }

    public List<T> getValues() {
        List<T> values = new ArrayList<>();
        getValuesRecursivo(root, values);
        return values;
    }

    private void getValuesRecursivo(NodoTrie nodoActual, List<T> values) {
        if (nodoActual.value != null) {
            values.add(nodoActual.value);
        }

        for (NodoTrie child : nodoActual.children.values()) {
            getValuesRecursivo(child, values);
        }
    }

    public void crearKey(String etiqueta) {
        insertar(etiqueta, null);
    }

    public TrieDict<T> copiar() {
        TrieDict<T> copia = new TrieDict<T>() {
        };

        copiarRecursivo(root, "", copia);

        return copia;
    }

    private void copiarRecursivo(NodoTrie nodoActual, String etiqueta, TrieDict<T> copia) {
        if (nodoActual.value != null) {
            copia.insertar(etiqueta, nodoActual.value);
        }

        for (char c : nodoActual.children.keySet()) {
            copiarRecursivo(nodoActual.children.get(c), etiqueta + c, copia);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrieDict<?> trieDict = (TrieDict<?>) o;
        return Objects.equals(root, trieDict.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }
}