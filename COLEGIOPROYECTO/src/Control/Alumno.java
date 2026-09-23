package Control;

	public class Alumno {
	    // Atributos privados (Encapsulamiento)
	    private String codigo;
	    private String dni;
	    private String nombre;
	    private String apellidos;
	    private int edad;
	    private String grado;
	    private String telefono;
	    private String papa;
	    private String mama;
	    private String celularApoderado;
	    private String direccion;
	    private String estadoMatricula;
	    private String fechaMovimiento;
	    private String responsableMovimiento;

	    // Constructor completo
	    public Alumno(String codigo, String dni, String nombre, String apellidos, int edad, String grado,
	                  String telefono, String papa, String mama, String celularApoderado, String direccion,
	                  String estadoMatricula, String fechaMovimiento, String responsableMovimiento) {
	        this.codigo = codigo;
	        this.dni = dni;
	        this.nombre = nombre;
	        this.apellidos = apellidos;
	        this.edad = edad;
	        this.grado = grado;
	        this.telefono = telefono;
	        this.papa = papa;
	        this.mama = mama;
	        this.celularApoderado = celularApoderado;
	        this.direccion = direccion;
	        this.estadoMatricula = estadoMatricula;
	        this.fechaMovimiento = fechaMovimiento;
	        this.responsableMovimiento = responsableMovimiento;
	    }

	    // Getters y Setters
	    public String getCodigo() { return codigo; }
	    public void setCodigo(String codigo) { this.codigo = codigo; }

	    public String getDni() { return dni; }
	    public void setDni(String dni) { this.dni = dni; }

	    public String getNombre() { return nombre; }
	    public void setNombre(String nombre) { this.nombre = nombre; }

	    public String getApellidos() { return apellidos; }
	    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

	    public int getEdad() { return edad; }
	    public void setEdad(int edad) { this.edad = edad; }

	    public String getGrado() { return grado; }
	    public void setGrado(String grado) { this.grado = grado; }

	    public String getTelefono() { return telefono; }
	    public void setTelefono(String telefono) { this.telefono = telefono; }

	    public String getPapa() { return papa; }
	    public void setPapa(String papa) { this.papa = papa; }

	    public String getMama() { return mama; }
	    public void setMama(String mama) { this.mama = mama; }

	    public String getCellularApoderado() { return celularApoderado; }
	    public void setCelularApoderado(String celularApoderado) { this.celularApoderado = celularApoderado; }

	    public String getDireccion() { return direccion; }
	    public void setDireccion(String direccion) { this.direccion = direccion; }

	    public String getEstadoMatricula() { return estadoMatricula; }
	    public void setEstadoMatricula(String estadoMatricula) { this.estadoMatricula = estadoMatricula; }

	    public String getFechaMovimiento() { return fechaMovimiento; }
	    public void setFechaMovimiento(String fechaMovimiento) { this.fechaMovimiento = fechaMovimiento; }

	    public String getResponsableMovimiento() { return responsableMovimiento; }
	    public void setResponsableMovimiento(String responsableMovimiento) { this.responsableMovimiento = responsableMovimiento; }
	}



