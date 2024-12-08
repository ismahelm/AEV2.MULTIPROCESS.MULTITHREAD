package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Model {

    /**
     * Método para lanzar una simulación en un hilo separado utilizando multihilo.
     * Este método crea una instancia de la clase SimulacionMT y la ejecuta en un hilo.
     *
     * @param stringtype Tipo de proteína a simular (representado como un string).
     * @param n Índice o número asociado a la simulación.
     * @param dir El directorio donde se guardarán los resultados de la simulación.
     */
    public static void lanzarSimulacionMT(String stringtype, int n, File dir) {
        int type = Integer.parseInt(stringtype); // Convertir el tipo de proteína a un número entero
        SimulacionMT simulador = new SimulacionMT(type, n, dir); // Crear una instancia del simulador
        Thread hilo = new Thread(simulador); // Crear un nuevo hilo para ejecutar la simulación
        hilo.start(); // Iniciar la simulación en un hilo separado
    }

    /**
     * Método para lanzar una simulación de multiproceso.
     * Este método utiliza un ProcessBuilder para ejecutar un proceso Java externo.
     * La simulación se ejecuta en un proceso separado.
     *
     * @param type Tipo de proteína a simular.
     * @param i Índice o número de la simulación.
     * @param dir El directorio donde se guardarán los resultados de la simulación.
     */
    public static void LaunchMultiprocess(String type, int i, File dir) {
        String clase = "calculadoraMP.calcularProteinas"; // Ruta de la clase que se ejecutará en el proceso
        try {
            String javaHome = System.getProperty("java.home"); // Obtener la ruta de instalación de Java
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java"; // Ruta del ejecutable de Java
            String classPath = System.getProperty("java.class.path"); // Obtener el classpath para ejecutar la clase
            String className = clase; // Nombre de la clase que se ejecutará

            // Construir el comando para ejecutar el proceso
            List<String> command = new ArrayList<>();
            command.add(javaBin); // Agregar la ruta del ejecutable de Java
            command.add("-cp"); // Argumento de classpath
            command.add(classPath); // Agregar el classpath
            command.add(className); // Agregar el nombre de la clase a ejecutar
            // Agregar parámetros concretos para la función (tipo, índice, directorio)
            command.add(type);
            command.add(String.valueOf(i));
            command.add(dir.getAbsolutePath());

            // Ejecutar el proceso con ProcessBuilder
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.inheritIO().start(); // Iniciar el proceso y heredar la entrada/salida

        } catch (Exception ex) {
            ex.printStackTrace(); // Manejo de excepciones si ocurre algún error en el proceso
        }
    }

    /**
     * Método para verificar si todas las simulaciones se han completado.
     * Compara el número de archivos generados en el directorio con el total esperado.
     * Si todos los archivos están completos (verificando su contenido), devuelve el tiempo transcurrido.
     *
     * @param dir El directorio donde se almacenan los resultados de las simulaciones.
     * @param total El número total de simulaciones que deberían completarse.
     * @return El tiempo que tardó en verificar si las simulaciones fueron completadas o 0 si no se completaron.
     */
    public double checkComplete(File dir, int total) {
        double startTime = System.currentTimeMillis(); // Marcar el tiempo de inicio para medir el rendimiento

        // Verificar si el número de archivos en el directorio es igual al total esperado
        if (dir.list() != null && dir.list().length == total) {
            Pattern pattern = Pattern.compile("\\d\\."); // Expresión regular para verificar que el contenido del archivo sea válido
            for (int i = 0; i < total; i++) {

                try {
                    String[] files = dir.list(); // Obtener la lista de archivos en el directorio
                    File lastFile = new File(dir, files[i]); // Obtener el archivo específico para la simulación actual

                    // Leer el archivo y obtener la última línea
                    String lastLine = null;
                    try (BufferedReader br = new BufferedReader(new FileReader(lastFile))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            lastLine = line; // Guardar la última línea leída
                        }
                    }

                    // Verificar si la última línea no es nula (el archivo no está vacío)
                    if (lastLine != null) {
                        // Verificar si la línea contiene un dígito seguido de un punto usando una expresión regular
                        Matcher matcher = pattern.matcher(lastLine);

                        if (!matcher.find()) {
                            // Si no se encuentra un dígito seguido de un punto, significa que la simulación no está completa
                            return 0;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace(); // Manejo de excepciones si ocurre un error al leer el archivo
                }

            }
        } else {
            return 0; // Si el número de archivos no coincide con el total esperado, devolver 0
        }

        double endTime = System.currentTimeMillis(); // Marcar el tiempo de fin para medir el rendimiento
        return endTime - startTime; // Devolver el tiempo total transcurrido en la verificación
    }
}

