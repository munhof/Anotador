package aed;

import java.util.List;

public class Anotador {

    TrieDict<ListaEnlazada<String>> anotador;

    public Anotador(){
        anotador = new TrieDict<ListaEnlazada<String>>();
    }

    public void crearEtiqueta(String etiqueta){
        anotador.insertar(etiqueta, new ListaEnlazada<String>());
    }

    public void etiquetarNota(String etiqueta, Nota nota){
        List<String> etiquetasActuales = anotador.getKeys();
        if(!(etiquetasActuales.contains(etiqueta))){
            crearEtiqueta(etiqueta);  
        }
        ListaEnlazada<String> notasEtiquetadas = anotador.buscar(etiqueta);
        notasEtiquetadas.agregarAtras(nota.getPath());
    }

    public void crearNota(String path, String titulo){
        Nota nota = new Nota(path, titulo);
        etiquetarNota("sin etiqueta", nota);
    }

    public void crearNota(String path, String titulo, String etiqueta){
        Nota nota = new Nota(path, titulo);
        etiquetarNota(etiqueta, nota);
    }

    public Nota buscarNota(String titulo) {
        for (String etiqueta : anotador.getKeys()) {
            ListaEnlazada<String> listaNotas = anotador.buscar(etiqueta);
            for (int i = 0; i < listaNotas.longitud(); i++) {
                Nota nota = Nota.getNota(listaNotas.obtener(i));
                if (nota.getName() == titulo) {
                    return nota;
                }
            }
        }
        return null;
    }
}
