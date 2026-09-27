package arreglo;

import clases.Alumno;
import java.io.*;
import java.util.ArrayList;

public class ArregloAlumnos {
    private ArrayList<Alumno> alumnos;
    private String archivo = "alumnos.txt";

    public ArregloAlumnos() {
        alumnos = new ArrayList<>();
        cargar();
        
        // Si el archivo est� vac�o, agrega alumnos de prueba autom�ticamente
        if (alumnos.isEmpty()) {
            adicionar(new Alumno(202610001, "Juan Carlos", "P�rez G�mez", "78451236", 7, 987654321, 0));
            adicionar(new Alumno(202610002, "Maria Fe", "Torres Lima", "76123498", 8, 912345678, 0));
            adicionar(new Alumno(202610003, "Lucas Mateo", "Ramos Quispe", "74859612", 6, 954321876, 0));
        }
    }

    public void adicionar(Alumno a) {
        alumnos.add(a);
        guardar();
    }

    public int tamaño() {
        return alumnos.size();
    }

    public Alumno obtener(int i) {
        return alumnos.get(i);
    }

    public Alumno buscar(int codAlumno) {
        for (Alumno a : alumnos) {
            if (a.getCodAlumno() == codAlumno) return a;
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

    public int codigoCorrelativo() {
        if (alumnos.isEmpty()) return 202610001;
        return alumnos.get(alumnos.size() - 1).getCodAlumno() + 1;
    }

    public void guardar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Alumno a : alumnos) {
                pw.println(a.getCodAlumno() + ";" + a.getNombres() + ";" + a.getApellidos() + ";" +
                           a.getDni() + ";" + a.getEdad() + ";" + a.getCelular() + ";" + a.getEstado());
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
                if (datos.length == 7) {
                    alumnos.add(new Alumno(
                        Integer.parseInt(datos[0]), datos[1], datos[2], datos[3],
                        Integer.parseInt(datos[4]), Integer.parseInt(datos[5]), Integer.parseInt(datos[6])
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}