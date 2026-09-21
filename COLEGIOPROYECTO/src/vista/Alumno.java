package vista;

public class Alumno {
 private int codAlumno, edad, celular, direccion;
 private String nombres, apellidos, DNI;
 
 public Alumno(int codAlumno, int edad, int celular, int direccion,
		String nombres, String apellidos, String dNI) {
	super();
	this.codAlumno = codAlumno;
	this.edad = edad;
	this.celular = celular;
	this.direccion = direccion;
	this.nombres = nombres;
	this.apellidos = apellidos;
	DNI = dNI;
 }
 public int getCodAlumno() {
	 return codAlumno;
 }
 
}
