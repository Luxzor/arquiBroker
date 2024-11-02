package Servidor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bitacora {

    private String archivoBitacora;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnn");

    /**
     * Constructor que inicializa el archivo de bitácora.
     *
     * @param archivoBitacora Ruta del archivo de bitácora.
     */
    public Bitacora(String archivoBitacora) {
        this.archivoBitacora = archivoBitacora;
    }

    /**
     * Registra una entrada en la bitácora con la claseMetodo, mensaje y marca de tiempo.
     *
     * @param claseMetodo   Nombre de la claseMetodo que registra la entrada.
     * @param mensaje Mensaje a registrar.
     */
    public void registrarEntrada(String claseMetodo, String mensaje) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoBitacora, true))) {
            String entrada = LocalDateTime.now().format(formatter) + " [" + claseMetodo + "] " + mensaje;
            writer.write(entrada);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
