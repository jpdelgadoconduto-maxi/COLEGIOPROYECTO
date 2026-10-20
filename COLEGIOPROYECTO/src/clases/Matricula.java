package clases;

public class Matricula {
//declarando Atributos privados
	private String matricula;
    private String dniAlumno;
    private String dniApoderado;
    private String telefono;
    private String fecha;
    private String hora;
    private String nombresAlumno;
    private String nombresApoderado;
    private String gradoSeccion;
    private int vacante;
    
	public Matricula() {
	
	}

	public Matricula(String matricula, String dniAlumno, String dniApoderado,
			String telefono, String fecha, String hora, String nombresAlumno,
			String nombresApoderado, String gradoSeccion, int vacante) {
		super();
		this.matricula = matricula;
		this.dniAlumno = dniAlumno;
		this.dniApoderado = dniApoderado;
		this.telefono = telefono;
		this.fecha = fecha;
		this.hora = hora;
		this.nombresAlumno = nombresAlumno;
		this.nombresApoderado = nombresApoderado;
		this.gradoSeccion = gradoSeccion;
		this.vacante = vacante;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getDniAlumno() {
		return dniAlumno;
	}

	public void setDniAlumno(String dniAlumno) {
		this.dniAlumno = dniAlumno;
	}

	public String getDniApoderado() {
		return dniApoderado;
	}

	public void setDniApoderado(String dniApoderado) {
		this.dniApoderado = dniApoderado;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getNombresAlumno() {
		return nombresAlumno;
	}

	public void setNombresAlumno(String nombresAlumno) {
		this.nombresAlumno = nombresAlumno;
	}

	public String getNombresApoderado() {
		return nombresApoderado;
	}

	public void setNombresApoderado(String nombresApoderado) {
		this.nombresApoderado = nombresApoderado;
	}

	public String getGradoSeccion() {
		return gradoSeccion;
	}

	public void setGradoSeccion(String gradoSeccion) {
		this.gradoSeccion = gradoSeccion;
	}

	public int getVacante() {
		return vacante;
	}

	public void setVacante(int vacante) {
		this.vacante = vacante;
	}
	//Cabecera de la tabla
    public static String obtenerCabecera() {
        return String.format("%-10s %-10s %-20s %-10s %-20s %-10s %-8s %-10s %-6s\n",
                "MATRÍCULA", "DNI ALUM.", "NOMBRES ALUMNO", "DNI APOD.", "NOMBRES APODERADO", "TELÉFONO", "GRADO", "FECHA", "HORA")
             + "------------------------------------------------------------------------------------------------------------------\n";
    }
 // Fila con datos alineados en columnas
    public String obtenerFila() {
        return String.format("%-10s %-10s %-20s %-10s %-20s %-10s %-8s %-10s %-6s\n",
                matricula, dniAlumno, nombresAlumno, dniApoderado, nombresApoderado, telefono, gradoSeccion, fecha, hora);
    }
	//imprimir
	public String obtenerResumen() {
		return "=== COMPROBANTE DE MATRÍCULA ===\n" +
	               "N° Matrícula: " + matricula + "\n" +
	               "Fecha/Hora: " + fecha + " - " + hora + "\n" +
	               "Alumno: " + nombresAlumno + " (DNI: " + dniAlumno + ")\n" +
	               "Apoderado: " + nombresApoderado + " (DNI: " + dniApoderado + ")\n" +
	               "Teléfono: " + telefono + "\n" +
	               "Grado / Sección: " + gradoSeccion + "\n" +
	               "Vacantes Restantes: " + vacante + "\n" +
	               "-------------------------------------\n";
	}
    
}
