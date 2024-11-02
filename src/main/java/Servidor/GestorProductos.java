package Servidor;

import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * La clase GestorProductos gestiona la lista de productos,
 * permitiendo cargarlos desde un archivo y proporcionando métodos
 * para acceder a ellos y sus contadores de votos.
 */
public class GestorProductos {

    private List<Producto> listaProductos;
    private String archivoProductos;
    private Bitacora bitacora;

    /**
     * Constructor de GestorProductos.
     *
     * @param archivoProductos Ruta del archivo de productos.
     * @param bitacora         Instancia de Bitacora para registrar eventos.
     */
    public GestorProductos(String archivoProductos, Bitacora bitacora) {
        this.listaProductos = new ArrayList<>();
        this.archivoProductos = archivoProductos;
        this.bitacora = bitacora;
    }

    /**
     * Carga los productos desde el archivo especificado.
     */
    public void cargarProductos() {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivoProductos), StandardCharsets.UTF_8);
            for (String linea : lineas) {
                String nombre = linea.trim();
                Producto producto = new Producto(nombre, bitacora);
                listaProductos.add(producto);
            }
            bitacora.registrarEntrada("GestorProductos.cargarProducto()", "Productos cargados exitosamente.");
        } catch (IOException e) {
            bitacora.registrarEntrada("GestorProductos.cargarProducto()", "Error al cargar productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene un producto por su nombre.
     *
     * @param nombre Nombre del producto a buscar.
     * @return El producto si se encuentra, de lo contrario null.
     */
    public Producto obtenerProducto(String nombre) {
        for (Producto producto : listaProductos) {
            if (producto.getNombre().equals(nombre)) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Obtiene una lista con los nombres de todos los productos.
     *
     * @return Lista de nombres de productos.
     */
    public List<String> obtenerNombresProductos() {
        List<String> nombres = new ArrayList<>();
        for (Producto producto : listaProductos) {
            nombres.add(producto.getNombre());
        }
        return nombres;
    }

    /**
     * Obtiene un mapa con los nombres de los productos y sus contadores de votos.
     *
     * @return Mapa con los nombres de los productos y sus votos.
     */
    public Map<String, Integer> obtenerContadoresProductos() {
        Map<String, Integer> contadores = new HashMap<>();
        for (Producto producto : listaProductos) {
            contadores.put(producto.getNombre(), producto.getContadorVotos());
        }
        return contadores;
    }

    /**
     * Obtiene la lista de productos.
     *
     * @return Lista de productos.
     */
    public List<Producto> getListaProductos() {
        return listaProductos;
    }
}
