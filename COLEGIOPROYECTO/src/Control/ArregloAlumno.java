package Control;

import java.io.*;
import java.util.ArrayList;

public class ArregloAlumno {

    private ArrayList<Alumno> alumnos;
    private final String archivo = "alumnos.txt"; // mismo archivo que lee Consulta

    public ArregloAlumno() {
        alumnos = new ArrayList<>();
        cargar();
    }

    public void adicionar(Alumno a) {
        alumnos.add(a);
        guardar();
    }

    public int tamano() {
        return alumnos.size();
    }

    public Alumno obtener(int i) {
        return alumnos.get(i);
    }

    public Alumno buscarCodigo(String codigo) {
        for (Alumno a : alumnos) {
            if (a.getCodigo().equals(codigo)) return a;
        }
        return null;
    }

    public Alumno buscarDni(String dni) {
        for (Alumno a : alumnos) {
            if (a.getDni().equals(dni)) return a;
        }
        return null;
    }

    public void eliminar(Alumno a) {
        alumnos.remove(a);
        guardar();
    }

    // Tras cambiar un alumno con sus setters, se llama a modificar()
    public void modificar() {
        guardar();
    }

    public String codigoCorrelativo() {
        int max = 202610000;
        for (Alumno a : alumnos) {
            try {
                int num = Integer.parseInt(a.getCodigo().trim());
                if (num > max) max = num;
            } catch (NumberFormatException ignored) {}
        }
        return String.valueOf(max + 1);
    }

    public void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Alumno a : alumnos) {
                pw.println("Código: " + a.getCodigo());
                pw.println("DNI: " + a.getDni());
                pw.println("Nombre: " + a.getNombre());
                pw.println("Apellidos: " + a.getApellidos());
                pw.println("Edad: " + a.getEdad());
                pw.println("Grado al que pasa: " + a.getGrado());
                pw.println("Teléfono: " + a.getTelefono());
                pw.println("Nombre del Papá: " + a.getPapa());
                pw.println("Nombre de la Mamá: " + a.getMama());
                pw.println("Celular Apoderado: " + a.getCellularApoderado());
                pw.println("Dirección: " + a.getDireccion());
                pw.println("Estado de Matrícula: " + a.getEstadoMatricula());
                pw.println("Fecha de Movimiento: " + a.getFechaMovimiento());
                pw.println("Responsable del Movimiento: " + a.getResponsableMovimiento());
                pw.println("====");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargar() {
        File f = new File(archivo);
        if (!f.exists()) return;
        java.util.Map<String, String> d = new java.util.HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                if (linea.startsWith("====")) {
                    if (d.containsKey("DNI")) {
                        int edad = 0;
                        try { edad = Integer.parseInt(d.getOrDefault("Edad", "0")); }
                        catch (NumberFormatException ignored) {}
                        alumnos.add(new Alumno(
                            d.getOrDefault("Código", ""), d.getOrDefault("DNI", ""),
                            d.getOrDefault("Nombre", ""), d.getOrDefault("Apellidos", ""),
                            edad, d.getOrDefault("Grado al que pasa", ""),
                            d.getOrDefault("Teléfono", ""), d.getOrDefault("Nombre del Papá", ""),
                            d.getOrDefault("Nombre de la Mamá", ""), d.getOrDefault("Celular Apoderado", ""),
                            d.getOrDefault("Dirección", ""), d.getOrDefault("Estado de Matrícula", ""),
                            d.getOrDefault("Fecha de Movimiento", ""),
                            d.getOrDefault("Responsable del Movimiento", "")));
                    }
                    d.clear();
                } else if (linea.contains(":")) {
                    String[] p = linea.split(":", 2);
                    d.put(p[0].trim(), p[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



