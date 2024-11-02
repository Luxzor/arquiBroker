package Servidor;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

/**
 * La clase Producto representa un producto (cafetería) que puede recibir votos.
 * Gestiona su nombre, contador de votos y el archivo donde se registran los votos.
 */
public class Producto {
    private String nombre;
    private int contadorVotos;
    private String archivoVotacion;
    private Bitacora bitacora;

    /**
     * Constructor de Producto.
     *
     * @param nombre   Nombre del producto.
     * @param bitacora Instancia de Bitacora para registrar eventos.
     */
    public Producto(String nombre, Bitacora bitacora) {
        this.nombre = nombre;
        this.contadorVotos = 0;
        this.archivoVotacion = "votos_" + nombre + ".txt";
        this.bitacora = bitacora;
    }

    /**
     * Constructor alternativo de Producto.
     *
     * @param nombre          Nombre del producto.
     * @param contadorVotos   Número inicial de votos.
     * @param archivoVotacion Ruta del archivo de votación.
     * @param bitacora        Instancia de Bitacora para registrar eventos.
     */
    public Producto(String nombre, int contadorVotos, String archivoVotacion, Bitacora bitacora) {
        this.nombre = nombre;
        this.contadorVotos = contadorVotos;
        this.archivoVotacion = archivoVotacion;
        this.bitacora = bitacora;
    }

    /**
     * Incrementa el contador de votos en uno.
     */
    public void incrementarVoto() {
        contadorVotos++;
    }

    /**
     * Guarda el voto en el archivo de votación.
     *
     * @param fechaHora Fecha y hora del voto.
     */
    public void guardarVoto(LocalDateTime fechaHora) {
        try {
            String linea = nombre + " " + fechaHora.toString() + "\n";
            Files.write(Paths.get(archivoVotacion), linea.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            bitacora.registrarEntrada("Producto.guardarVoto()", "Voto guardado para: " + nombre);
        } catch (IOException e) {
            bitacora.registrarEntrada("Producto.guardarVoto()", "Error al guardar voto para " + nombre + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga los votos desde el archivo de votación.
     */
    public void cargarVotos() {
        try {
            contadorVotos = 0;
            java.nio.file.Path path = Paths.get(archivoVotacion);
            if (!Files.exists(path)) {
                // Si el archivo no existe, no hay votos que cargar
                bitacora.registrarEntrada("Producto.cargarVotos()", "No hay votos previos para: " + nombre);
                return;
            }
            List<String> lineas = Files.readAllLines(path, StandardCharsets.UTF_8);
            contadorVotos = lineas.size();
            bitacora.registrarEntrada("Producto.cargarVotos()", "Votos cargados para: " + nombre);
        } catch (IOException e) {
            bitacora.registrarEntrada("Producto", "Error al cargar votos para " + nombre + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el contador de votos del producto.
     *
     * @return Contador de votos.
     */
    public int getContadorVotos() {
        return contadorVotos;
    }
}
