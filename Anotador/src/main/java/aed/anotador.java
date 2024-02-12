package aed;

import java.util.List;
import java.util.Objects;
import java.io.*;


public class Anotador implements Serializable {

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
                String name = nota.getName() ;
                if (name.compareTo(titulo) == 0) {
                    return nota;
                }
            }
        }
        return null;
    }

    public void cambiarEtiqueta(String titulo, String etiquetaNueva){
        Nota nota;
        String name;
        for (String etiqueta : anotador.getKeys()) {
            ListaEnlazada<String> listaNotas = anotador.buscar(etiqueta);
            for (int i = 0; i < listaNotas.longitud(); i++) {
                nota = Nota.getNota(listaNotas.obtener(i));
                name = nota.getName() ;
                if (name.compareTo(titulo) == 0) {
                    listaNotas.eliminar(i);
                    etiquetarNota(etiquetaNueva, nota);
                }
            }
        }
    }

    public void guardar(String archivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Anotador cargar(String archivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Anotador) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Anotador other = (Anotador) obj;
        // Aquí comparas los atributos relevantes para determinar la igualdad
        return Objects.equals(this.anotador, other.anotador);
    }
}
