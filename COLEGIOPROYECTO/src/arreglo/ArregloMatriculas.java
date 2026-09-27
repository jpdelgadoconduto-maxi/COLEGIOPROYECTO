package arreglo;

import clases.Matricula;
import java.io.*;
import java.util.ArrayList;

public class ArregloMatriculas {
    private ArrayList<Matricula> matriculas;
    private String archivo = "matriculas.txt";

    public ArregloMatriculas() {
        matriculas = new ArrayList<>();
        cargar();
    }

    public void adicionar(Matricula m) {
        matriculas.add(m);
        guardar();
    }

    public int tamaño() {
        return matriculas.size();
    }

    public Matricula obtener(int i) {
        return matriculas.get(i);
    }

    public Matricula buscar(int numMatricula) {
        for (Matricula m : matriculas) {
            if (m.getNumMatricula() == numMatricula) return m;
        }
        return null;
    }

    public int contarPorGrado(int codGrado) {
        int count = 0;
        for (Matricula m : matriculas) {
            if (m.getCodGrado() == codGrado) count++;
        }
        return count;
    }

    public void eliminar(Matricula m) {
        matriculas.remove(m);
        guardar();
    }

    public int numeroCorrelativo() {
        if (matriculas.isEmpty()) return 100001;
        return matriculas.get(matriculas.size() - 1).getNumMatricula() + 1;
    }

    public void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Matricula m : matriculas) {
                pw.println(m.getNumMatricula() + ";" + m.getCodAlumno() + ";" + m.getCodGrado() + ";" +
                           m.getFecha() + ";" + m.getHora());
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
                    matriculas.add(new Matricula(
                        Integer.parseInt(datos[0]), Integer.parseInt(datos[1]),
                        Integer.parseInt(datos[2]), datos[3], datos[4]
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}