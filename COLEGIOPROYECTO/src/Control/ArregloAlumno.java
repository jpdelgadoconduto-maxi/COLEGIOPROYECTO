package Control;

import java.io.*;
import java.util.ArrayList;

public class ArregloAlumno {

    // Estado 0 = registrado (así lo pide el mantenimiento)
    public static final String ESTADO_REGISTRADO = "0";
    public static final String ESTADO_MATRICULADO = "1";

    private ArrayList<Alumno> alumnos;
    // DNI del apoderado por DNI de alumno (la clase Alumno original no tiene ese campo)
    private java.util.Map<String, String> dnisApoderado = new java.util.HashMap<>();
    private final String archivo = "alumnos.txt"; // mismo archivo que lee Consulta

    public ArregloAlumno() {
        alumnos = new ArrayList<>();
        cargar();
    }

    // ---------- Operaciones en memoria (se persisten con grabar()) ----------

    public void adicionar(Alumno a) {
        alumnos.add(a);
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

    // Eliminación física: el alumno se quita del arreglo
    public void eliminar(Alumno a) {
        alumnos.remove(a);
        dnisApoderado.remove(a.getDni());
    }

    // ---------- Apoderado ----------
    // El nombre del apoderado se guarda en el campo "papa" del Alumno (clave "Nombre del Papá"
    // del archivo, así Consulta lo sigue leyendo); el campo "mama" queda vacío.
    public String getApoderado(Alumno a) {
        return a.getPapa() == null ? "" : a.getPapa();
    }

    public void setApoderado(Alumno a, String nombre) {
        a.setPapa(nombre == null ? "" : nombre);
        a.setMama("");
    }

    public String getDniApoderado(Alumno a) {
        String d = dnisApoderado.get(a.getDni());
        return d == null ? "" : d;
    }

    public void setDniApoderado(Alumno a, String dni) {
        dnisApoderado.put(a.getDni(), dni == null ? "" : dni);
    }

    public boolean esRegistrado(Alumno a) {
        return ESTADO_REGISTRADO.equals(a.getEstadoMatricula());
    }

    // Texto para mostrar el estado en pantalla
    public static String textoEstado(String estado) {
        if (ESTADO_REGISTRADO.equals(estado)) return "0 - Registrado";
        if (ESTADO_MATRICULADO.equals(estado)) return "1 - Matriculado";
        return estado;
    }

    // ---------- Código = DNI + correlativo (84261573001, 59374128002, ...) ----------

    private boolean esCodigoValido(String codigo, String dni) {
        if (codigo == null || dni == null) return false;
        codigo = codigo.trim();
        return codigo.length() == dni.length() + 3 && codigo.startsWith(dni)
            && codigo.substring(codigo.length() - 3).matches("\\d{3}");
    }

    // Último número correlativo usado (los 3 últimos dígitos de los códigos válidos)
    private int ultimoCorrelativo() {
        int max = 0;
        for (Alumno a : alumnos) {
            if (esCodigoValido(a.getCodigo(), a.getDni())) {
                String c = a.getCodigo().trim();
                max = Math.max(max, Integer.parseInt(c.substring(c.length() - 3)));
            }
        }
        return max;
    }

    // Código para un alumno nuevo: su DNI + el siguiente correlativo
    public String codigoCorrelativo(String dni) {
        return dni + String.format("%03d", ultimoCorrelativo() + 1);
    }

    // ---------- Grado y sección ("3 - A") ----------

    // "3" (dato antiguo, sin sección) pasa a "3 - A"; "3-c" pasa a "3 - C"
    private String normalizarGrado(String g) {
        if (g == null) return "";
        g = g.trim();
        if (g.matches("[1-6]")) return g + " - A";
        if (g.matches("[1-6]\\s*-\\s*[A-Za-z]")) {
            return g.substring(0, 1) + " - " + g.substring(g.length() - 1).toUpperCase();
        }
        return g;
    }

    // ---------- Archivo ----------

    // Las líneas de matrícula (formato "100001;fecha;hora;...") las escribe
    // ModuloMatricula en este mismo archivo. Se conservan al grabar para no perderlas.
    private boolean esLineaMatricula(String linea) {
        return linea.matches("\\d+;.*");
    }

    private ArrayList<String> leerLineasMatricula() {
        ArrayList<String> lineas = new ArrayList<>();
        File f = new File(archivo);
        if (!f.exists()) return lineas;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (esLineaMatricula(linea)) lineas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineas;
    }

    // Graba todos los cambios. Devuelve true si se pudo escribir el archivo.
    public boolean grabar() {
        ArrayList<String> lineasMatricula = leerLineasMatricula();
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Alumno a : alumnos) {
                pw.println("Código: " + a.getCodigo());
                pw.println("DNI: " + a.getDni());
                pw.println("Nombre: " + a.getNombre());
                pw.println("Apellidos: " + a.getApellidos());
                pw.println("Edad: " + a.getEdad());
                pw.println("Grado al que pasa: " + a.getGrado());
                pw.println("Teléfono: " + a.getTelefono());
                pw.println("Nombre del Papá: " + a.getPapa());       // = apoderado
                pw.println("Nombre de la Mamá: " + a.getMama());
                pw.println("Celular Apoderado: " + a.getCellularApoderado()); // se muestra como "Teléfono"
                pw.println("DNI Apoderado: " + getDniApoderado(a));
                pw.println("Dirección: " + a.getDireccion());
                pw.println("Estado de Matrícula: " + a.getEstadoMatricula());
                pw.println("Fecha de Movimiento: " + a.getFechaMovimiento());
                pw.println("Responsable del Movimiento: " + a.getResponsableMovimiento());
                pw.println("====");
            }
            for (String l : lineasMatricula) {
                pw.println(l);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void cargar() {
        File f = new File(archivo);
        if (!f.exists()) return;
        java.util.Map<String, String> d = new java.util.HashMap<>();
        ArrayList<String> lineasMat = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                if (esLineaMatricula(linea)) { lineasMat.add(linea); continue; }
                if (linea.startsWith("====")) {
                    if (d.containsKey("DNI")) {
                        // Registros antiguos guardaban "Registrado": se convierten al estado 0
                        String estado = d.getOrDefault("Estado de Matrícula", "");
                        if (estado.isEmpty() || estado.equalsIgnoreCase("Registrado")) {
                            estado = ESTADO_REGISTRADO;
                        }

                        int edad = 0;
                        try { edad = Integer.parseInt(d.getOrDefault("Edad", "0")); }
                        catch (NumberFormatException ignored) {}

                        // Apoderado: antes se guardaba como papá o mamá
                        String apoderado = d.getOrDefault("Apoderado", "");
                        if (apoderado.isEmpty()) apoderado = d.getOrDefault("Nombre del Papá", "");
                        if (apoderado.isEmpty()) apoderado = d.getOrDefault("Nombre de la Mamá", "");
                        String celular = d.getOrDefault("Celular Apoderado", "");
                        if (celular.isEmpty()) celular = d.getOrDefault("Teléfono", "");

                        Alumno nuevo = new Alumno(
                            d.getOrDefault("Código", ""), d.getOrDefault("DNI", ""),
                            d.getOrDefault("Nombre", ""), d.getOrDefault("Apellidos", ""),
                            edad, normalizarGrado(d.getOrDefault("Grado al que pasa", "")),
                            d.getOrDefault("Teléfono", ""), apoderado, "", celular,
                            d.getOrDefault("Dirección", ""), estado,
                            d.getOrDefault("Fecha de Movimiento", ""),
                            d.getOrDefault("Responsable del Movimiento", ""));
                        alumnos.add(nuevo);
                        setDniApoderado(nuevo, d.getOrDefault("DNI Apoderado", ""));
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
        // Las matrículas (líneas con ;) también son alumnos: se incorporan a la lista
        for (String l : lineasMat) incorporarMatricula(l);

        // Códigos antiguos o vacíos se reemplazan por DNI + correlativo
        for (Alumno a : alumnos) {
            if (!esCodigoValido(a.getCodigo(), a.getDni())) {
                a.setCodigo(codigoCorrelativo(a.getDni()));
            }
        }
    }

    // Línea de matrícula: Matricula;Fecha;Hora;DniAlumno;NombreAlumno;DniApoderado;NombreApoderado;Telefono;GradoSeccion
    // - Si el DNI ya existe, solo se marca como matriculado (estado 1).
    // - Si no existe, se crea el alumno (el DNI no se repite).
    private void incorporarMatricula(String linea) {
        String[] p = linea.split(";", -1);
        if (p.length < 9) return;
        String dni = p[3].trim();
        if (dni.isEmpty()) return;

        Alumno existente = buscarDni(dni);
        if (existente != null) {
            if (ESTADO_REGISTRADO.equals(existente.getEstadoMatricula())) {
                existente.setEstadoMatricula(ESTADO_MATRICULADO);
            }
            if (existente.getGrado() == null || existente.getGrado().isEmpty()) {
                existente.setGrado(normalizarGrado(p[8]));
            }
            if (getApoderado(existente).isEmpty()) setApoderado(existente, p[6].trim());
            if (getDniApoderado(existente).isEmpty()) setDniApoderado(existente, p[5].trim());
            if (existente.getCellularApoderado() == null || existente.getCellularApoderado().isEmpty()) {
                existente.setCelularApoderado(p[7].trim());
            }
            return;
        }
        // El N° de matrícula ya es DNI + correlativo: se usa como código del alumno
        String codigo = esCodigoValido(p[0], dni) ? p[0].trim() : "";
        String[] nom = separarNombre(p[4].trim());
        Alumno nuevo = new Alumno(codigo, dni, nom[0], nom[1], 0, normalizarGrado(p[8]),
            "", "", "", p[7].trim(), "", ESTADO_MATRICULADO, p[1].trim(), "Matrícula");
        setApoderado(nuevo, p[6].trim());
        setDniApoderado(nuevo, p[5].trim());
        alumnos.add(nuevo);
    }

    // La matrícula guarda el nombre completo en un solo texto: se separa en nombres / apellidos
    // (los dos últimos términos son los apellidos). Se puede corregir luego con Modificar.
    private String[] separarNombre(String completo) {
        String[] w = completo.trim().split("\\s+");
        for (int i = 0; i < w.length; i++) {
            if (!w[i].isEmpty()) w[i] = w[i].substring(0, 1).toUpperCase() + w[i].substring(1).toLowerCase();
        }
        int n = w.length;
        if (n == 0 || (n == 1 && w[0].isEmpty())) return new String[] { "", "" };
        if (n == 1) return new String[] { w[0], "" };
        int corte = (n >= 3) ? n - 2 : 1;
        return new String[] { String.join(" ", java.util.Arrays.copyOfRange(w, 0, corte)),
                              String.join(" ", java.util.Arrays.copyOfRange(w, corte, n)) };
    }
}
