package arreglo;

import clases.Grado;
import java.io.*;
import java.util.ArrayList;

public class ArregloGrados {
    private ArrayList<Grado> grados;
    private String archivo = "grados.txt";

    public ArregloGrados() {
        grados = new ArrayList<>();
        cargar();
        if (grados.isEmpty()) {
            // Cargar vacantes iniciales para primaria
            adicionar(new Grado(1001, "1� Grado", "A", 30, "Primaria"));
            adicionar(new Grado(1002, "2� Grado", "A", 30, "Primaria"));
            adicionar(new Grado(1003, "3� Grado", "A", 30, "Primaria"));
            adicionar(new Grado(1004, "4� Grado", "A", 30, "Primaria"));
            adicionar(new Grado(1005, "5� Grado", "A", 30, "Primaria"));
            adicionar(new Grado(1006, "6� Grado", "A", 30, "Primaria"));
        }
    }

    public void adicionar(Grado g) {
        grados.add(g);
        guardar();
    }

    public int tamaño() {
        return grados.size();
    }

    public Grado obtener(int i) {
        return grados.get(i);
    }

    public Grado buscar(int codGrado) {
        for (Grado g : grados) {
            if (g.getCodGrado() == codGrado) return g;
        }
        return null;
    }

    public void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Grado g : grados) {
                pw.println(g.getCodGrado() + ";" + g.getNombreGrado() + ";" + g.getSeccion() + ";" +
                           g.getCapacidad() + ";" + g.getNivel());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargar() {
        File f = new File(archivo);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 5) {
                    grados.add(new Grado(
                        Integer.parseInt(datos[0]), datos[1], datos[2],
                        Integer.parseInt(datos[3]), datos[4]
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
