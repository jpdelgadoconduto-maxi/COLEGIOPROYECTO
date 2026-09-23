package Control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Alumnocontroler {

    public Alumno buscarPorDni(String dniBuscado) {
        String nombreArchivo = "alumnos.txt"; 
        Map<String, String> datos = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                
                if (linea.isEmpty()) continue;

                // Si encontramos la línea divisoria, evaluamos el bloque acumulado
                if (linea.startsWith("====")) {
                    
                    // Jalamos el DNI del mapa quitándole espacios por si acaso
                    String dniEnArchivo = datos.get("DNI") != null ? datos.get("DNI").trim() : "";
                    
                    if (dniBuscado.equals(dniEnArchivo)) {
                        // Construimos y retornamos el objeto Alumno con los datos limpios
                        return new Alumno(
                            datos.get("Código"),
                            datos.get("DNI"),
                            datos.get("Nombre"),
                            datos.get("Apellidos"),
                            Integer.parseInt(datos.getOrDefault("Edad", "0").trim()),
                            datos.get("Grado al que pasa"),
                            datos.get("Teléfono"),
                            datos.get("Nombre del Papá"),
                            datos.get("Nombre de la Mamá"),
                            datos.get("Celular Apoderado"),
                            datos.get("Dirección"),
                            datos.get("Estado de Matrícula"),
                            datos.get("Fecha de Movimiento"),
                            datos.get("Responsable del Movimiento")
                        );
                    }
                    datos.clear(); // Limpiamos el mapa para el siguiente alumno
                    
                } else if (linea.contains(":")) {
                    String[] partes = linea.split(":", 2);
                    if (partes.length == 2) {
                        // 🌟 SOLUCIÓN: Limpiamos los espacios en blanco de la etiqueta y del valor con .trim()
                        String llave = partes[0].trim();
                        String valor = partes[1].trim();
                        datos.put(llave, valor);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error crítico al leer la base de datos: " + e.getMessage());
        }
        return null; // Retorna null si terminó todo el bloc de notas y no lo encontró
    }
}
