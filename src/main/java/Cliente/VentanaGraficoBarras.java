/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Cliente;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.Map;
import Servidor.ControladorVotos;

/**
 *
 * @author Luxzor
 */
public class VentanaGraficoBarras extends javax.swing.JFrame {

    /**
     * Creates new form VentanaGraficoBarras
     */
    
    private ControladorVotos controlador;
    private DefaultCategoryDataset dataset;
    
    public VentanaGraficoBarras(ControladorVotos controlador) {
        this.controlador = controlador;
        initComponents();
        crearGrafico();
        actualizarGrafico(controlador.obtenerDatosProductos());
    }
    
     public void actualizarGrafico(Map<String, Integer> datosProductos) {
        dataset.clear();
        for (Map.Entry<String, Integer> entrada : datosProductos.entrySet()) {
            dataset.addValue(entrada.getValue(), "Votos", entrada.getKey());
        }
     }
     
     public void crearGrafico(){
        setTitle("Gráfico de Barras");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Votos por Producto",
                "Producto",
                "Votos",
                dataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
