package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Esta clase representa la simulación de proteínas en múltiples hilos.
 * Implementa la interfaz {@link Runnable} para permitir que cada instancia
 * de esta clase se ejecute en un hilo separado.
 */
public class SimulacionMT implements Runnable {
    
    /** El tipo de simulación a realizar. */
    private int type;
    
    /** El número de la simulación que se está ejecutando. */
    private int n;
    
    /** El directorio donde se guardarán los resultados de la simulación. */
    private File dir;

    /**
     * Constructor de la clase SimulacionMT.
     * 
     * @param type El tipo de simulación a realizar (utilizado para determinar el tiempo de simulación).
     * @param n El número de la simulación.
     * @param dir El directorio donde se almacenarán los archivos resultantes de la simulación.
     */
    public SimulacionMT(int type, int n, File dir) {
        this.type = type;
        this.n = n;
        this.dir = dir;
    }

    /**
     * Método que se ejecuta cuando el hilo es iniciado.
     * Realiza la simulación, calcula el tiempo que tarda y guarda los resultados en un archivo.
     */
    @Override
    public void run() {
        // Obtener el timestamp actual para la creación del archivo
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
        String starttimestamp = now.format(formatter);
        starttimestamp = starttimestamp.substring(0, starttimestamp.length() - 1);  // Ajustar el timestamp

        double calc = 0.0; // Variable para almacenar el resultado de la simulación
        
        try {
            // Crear un archivo para almacenar los resultados de la simulación
            File file = new File(dir + "/PROT_MT_" + type + "_n" + n + "_" + starttimestamp + ".sim");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
            // Calcular el tiempo total de la simulación basado en el tipo
            double simulationTime = Math.pow(5, type);
            double startTime = System.currentTimeMillis();  // Tiempo de inicio de la simulación
            double endTime = startTime + simulationTime;  // Tiempo estimado de finalización

            // Ejecutar la simulación durante el tiempo calculado
            while (System.currentTimeMillis() < endTime) {
                calc = Math.sin(Math.pow(Math.random(), 2));  // Realizar cálculo durante la simulación
            }

            // Obtener el timestamp al final de la simulación
            LocalDateTime end = LocalDateTime.now();
            bw.write(starttimestamp);  // Escribir el timestamp de inicio
            bw.newLine();
            bw.newLine();

            // Ajustar y escribir el timestamp de finalización
            String endtimestamp = end.format(formatter);
            endtimestamp = endtimestamp.substring(0, endtimestamp.length() - 1);
            bw.write(endtimestamp);  // Escribir el timestamp de fin
            bw.newLine();
            bw.newLine();

            // Calcular el tiempo transcurrido en la simulación y escribirlo
            Float startseconds = Float.parseFloat(starttimestamp.substring(13).replace("_", "."));
            Float endseconds = Float.parseFloat(endtimestamp.substring(13).replace("_", "."));
            String writefinishtime = String.format("%.2f", endseconds - startseconds);
            writefinishtime = writefinishtime.replace(",", "_");
            bw.write(writefinishtime);  // Escribir el tiempo de ejecución
            bw.newLine();
            bw.newLine();

            // Escribir el resultado final de la simulación
            bw.write(String.valueOf(calc));
            bw.close();  // Cerrar el BufferedWriter
            fw.close();  // Cerrar el FileWriter

        } catch (IOException e) {
            e.printStackTrace();  // Manejo de errores en caso de una excepción de E/S
        }
    }
}
