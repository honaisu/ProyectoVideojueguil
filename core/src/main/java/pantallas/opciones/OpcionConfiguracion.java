package pantallas.opciones;

public enum OpcionConfiguracion {
	VOLUMEN_GENERAL("Volumen General"),
	VOLUMEN_MUSICA("Volumen Música"),
	VOLUMEN_EFECTOS("Volumen Efectos"),
	GUARDAR("Guardar"),
	VOLVER("Volver");
	
	private String nombre;
	
	OpcionConfiguracion(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
}
