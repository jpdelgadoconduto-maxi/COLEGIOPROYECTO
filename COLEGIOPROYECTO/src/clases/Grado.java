package clases;

public class Grado {
    private int codGrado;
    private String nombreGrado;
    private String seccion;
    private int capacidad;
    private String nivel;

    public Grado(int codGrado, String nombreGrado, String seccion, int capacidad, String nivel) {
        this.codGrado = codGrado;
        this.nombreGrado = nombreGrado;
        this.seccion = seccion;
        this.capacidad = capacidad;
        this.nivel = nivel;
    }

    public int getCodGrado() { return codGrado; }
    public void setCodGrado(int codGrado) { this.codGrado = codGrado; }

    public String getNombreGrado() { return nombreGrado; }
    public void setNombreGrado(String nombreGrado) { this.nombreGrado = nombreGrado; }

    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
}