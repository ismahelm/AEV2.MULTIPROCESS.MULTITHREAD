package calculadoraMP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class calcularProteinas {

    /**
     * Simula el cálculo de proteínas y guarda los resultados en un archivo.
     * 
     * @param n Número de simulación.
     * @param type Tipo de proteína (1, 2, 3, etc.), que influye en el tiempo de simulación.
     * @param dir El directorio donde se guardarán los archivos de resultados.
     * @return El valor calculado al final de la simulación 
     */
    public static double Simulate(int n, int type, File dir) { 
        // Obtener la fecha y hora actuales para generar un timestamp único
        LocalDateTime now = LocalDateTime.now();   
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");        
        String starttimestamp = now.format(formatter);
        starttimestamp = starttimestamp.substring(0, starttimestamp.length() - 1); // Ajustar el formato

        double calc = 0.0; 
        try {
            // Crear el archivo donde se guardarán los resultados de la simulación
            File file = new File(dir + "/PROT_MP_" + type + "_n" + n + "_" + starttimestamp + ".sim");
            FileWriter fw = new FileWriter(file); 
            BufferedWriter bw = new BufferedWriter(fw); // Usar BufferedWriter para escribir
            
            double simulationTime = Math.pow(5, type); 
            double startTime = System.currentTimeMillis(); 
            double endTime = startTime + simulationTime;
            while (System.currentTimeMillis() < endTime) { 
                calc = Math.sin(Math.pow(Math.random(), 2)); // Realizar un cálculo usando la función seno
            }
            
            // Registrar el tiempo de finalización y escribir los resultados en el archivo
            LocalDateTime end = LocalDateTime.now(); // Obtener la hora de finalización
            bw.write(starttimestamp); // Escribir el timestamp de inicio en el archivo
            bw.newLine();
            bw.newLine();

            // Obtener el timestamp de finalización
            String endtimestamp = end.format(formatter);
            endtimestamp = endtimestamp.substring(0, endtimestamp.length() - 1); // Ajustar el formato

            bw.write(endtimestamp); // Escribir el timestamp de finalización en el archivo
            bw.newLine();
            bw.newLine();
            
            // Calcular el tiempo total de la simulación en segundos
            Float startseconds = Float.parseFloat(starttimestamp.substring(13).replace("_", ".")); // Extraer los segundos del timestamp
            Float endseconds = Float.parseFloat(endtimestamp.substring(13).replace("_", ".")); // Extraer los segundos del timestamp de finalización
            String writefinishtime = String.format("%.2f", endseconds - startseconds); // Calcular la diferencia de tiempo y formatear el resultado
            writefinishtime = writefinishtime.replace(",", "_"); // Reemplazar comas por guiones bajos
            bw.write(writefinishtime); // Escribir el tiempo total de simulación en el archivo
            bw.newLine();
            bw.newLine();

            // Escribir el valor calculado en el archivo
            bw.write(String.valueOf(calc)); // Escribir el valor final de la simulación
            bw.close(); // Cerrar el BufferedWriter
            fw.close(); // Cerrar el FileWriter
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones en caso de errores de entrada/salida
        }

        return calc; // Devolver el valor calculado durante la simulación
    }

    /**
     * Método principal para ejecutar la simulación.
     * 
     * @param args Argumentos de línea de comandos: 
     *             args[0] = tipo de proteína,
     *             args[1] = número de simulación,
     *             args[2] = directorio donde se guardarán los resultados.
     */
    public static void main(String[] args) {
        int type = Integer.parseInt(args[0]); // Obtener el tipo de proteína desde los argumentos
        int n = Integer.parseInt(args[1]); // Obtener el número de simulación desde los argumentos
        File dir = new File(args[2]); // Obtener el directorio donde se guardarán los archivos de resultados

        // Llamar al método Simulate() para ejecutar la simulación y obtener el resultado
        Simulate(n, type, dir);
    }
}

