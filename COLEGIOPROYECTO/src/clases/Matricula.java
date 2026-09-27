package clases;

public class Matricula {
    private int numMatricula;
    private int codAlumno;
    private int codGrado;
    private String fecha;
    private String hora;

    public Matricula(int numMatricula, int codAlumno, int codGrado, String fecha, String hora) {
        this.numMatricula = numMatricula;
        this.codAlumno = codAlumno;
        this.codGrado = codGrado;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getNumMatricula() { return numMatricula; }
    public void setNumMatricula(int numMatricula) { this.numMatricula = numMatricula; }

    public int getCodAlumno() { return codAlumno; }
    public void setCodAlumno(int codAlumno) { this.codAlumno = codAlumno; }

    public int getCodGrado() { return codGrado; }
    public void setCodGrado(int codGrado) { this.codGrado = codGrado; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
}