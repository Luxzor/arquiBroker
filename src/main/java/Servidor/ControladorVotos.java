/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Servidor.Bitacora;
import Servidor.GestorProductos;
import Servidor.Producto;
import com.formdev.flatlaf.FlatIntelliJLaf;
import Cliente.App;
import Cliente.VentanaGraficoBarras;
import Cliente.VentanaGraficoPastel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;

/**
 * ControladorVotos actúa como intermediario entre la vista y el modelo,
 * procesando las acciones del usuario y actualizando los datos y las vistas en consecuencia.
 */
public class ControladorVotos {

    private GestorProductos gestorProductos;
    private VentanaGraficoBarras ventanaGraficoBarras;
    private VentanaGraficoPastel ventanaGraficoPastel;
    private App vistaPrincipal;
    private Bitacora bitacora;

    /**
     * Constructor de ControladorVotos.
     * Inicializa el gestor de productos y carga los productos y sus votos.
     */
    public ControladorVotos() {
        this.bitacora = new Bitacora("bitacora.txt");
        this.gestorProductos = new GestorProductos("productos.txt", bitacora);
        gestorProductos.cargarProductos();

        // Cargar votos de cada producto
        for (Producto producto : gestorProductos.getListaProductos()) {
            producto.cargarVotos();
        }
    }

    /**
     * Procesa el voto de un producto específico.
     *
     * @param nombreProducto Nombre del producto que recibió el voto.
     */
    public void procesarVoto(String nombreProducto) {
        Producto producto = gestorProductos.obtenerProducto(nombreProducto);
        if (producto != null) {
            producto.incrementarVoto();
            producto.guardarVoto(LocalDateTime.now());

            // Registrar en la bitácora
            bitacora.registrarEntrada("ControladorVotos.procesarVoto()", "Voto procesado para: " + nombreProducto);

            // Actualizar la vista principal
            if (vistaPrincipal != null) {
                vistaPrincipal.actualizarVista(gestorProductos.obtenerContadoresProductos());
            }

            // Actualizar las ventanas de gráficos si están abiertas
            if (ventanaGraficoBarras != null) {
                ventanaGraficoBarras.actualizarGrafico(gestorProductos.obtenerContadoresProductos());
            }
            if (ventanaGraficoPastel != null) {
                ventanaGraficoPastel.actualizarGrafico(gestorProductos.obtenerContadoresProductos());
            }
        } else {
            bitacora.registrarEntrada("ControladorVotos.procesarVoto()", "Producto no encontrado: " + nombreProducto);
        }
    }

    /**
     * Inicia la aplicación configurando la vista principal.
     * Este método puede ser llamado desde el método main si se prefiere.
     */
    public void iniciarAplicacion() {
        vistaPrincipal = new App(this);
        vistaPrincipal.setVisible(true);
    }

    /**
     * Obtiene un mapa con los nombres de los productos y sus contadores de votos.
     *
     * @return Mapa con los datos de los productos.
     */
    public Map<String, Integer> obtenerDatosProductos() {
        return gestorProductos.obtenerContadoresProductos();
    }

    /**
     * Obtiene una lista con los nombres de los productos.
     *
     * @return Lista de nombres de productos.
     */
    public List<String> obtenerNombresProductos() {
        return gestorProductos.obtenerNombresProductos();
    }

    /**
     * Establece la referencia a la vista principal.
     *
     * @param vistaPrincipal Instancia de la vista principal de la aplicación.
     */
    public void setVistaPrincipal(App vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
    }

    /**
     * Muestra la ventana con el gráfico de barras.
     */
    public void mostrarVentanaGraficoBarras() {
        if (ventanaGraficoBarras == null) {
            ventanaGraficoBarras = new VentanaGraficoBarras(this);
        }
        ventanaGraficoBarras.setVisible(true);
    }

    /**
     * Muestra la ventana con el gráfico de pastel.
     */
    public void mostrarVentanaGraficoPastel() {
        if (ventanaGraficoPastel == null) {
            ventanaGraficoPastel = new VentanaGraficoPastel(this);
        }
        ventanaGraficoPastel.setVisible(true);
    }
    
    public static void main(String[] args) {
        
        Bitacora bitacora = new Bitacora("bitacora.txt");
        
        bitacora.registrarEntrada("ControladorVotos.main()", "Se inicia la aplicacion");
        
        // Set the Look and Feel
        try {
            FlatIntelliJLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize the controller and start the application
        ControladorVotos controlador = new ControladorVotos();
        

        // Create and display the main view
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                App app = new App(controlador);
                controlador.setVistaPrincipal(app);
                app.setVisible(true);
                app.setLocationRelativeTo(null);
            }
        });
    }

    public void registrarEnBitacora(String claseMetodo,String mensaje){
        this.bitacora.registrarEntrada(claseMetodo, mensaje);
    }

}
