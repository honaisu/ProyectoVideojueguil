package pantallas;

public enum OpcionesConfiguracion {
	VOLUMEN_GENERAL("Volumen General"),
	VOLUMEN_MUSICA("Volumen Música"),
	VOLUMEN_EFECTOS("Volumen Efectos"),
	GUARDAR("Guardar"),
	VOLVER("Volver");
	
	private String opcion;
	
	OpcionesConfiguracion(String opcion) {
		this.opcion = opcion;
	}
	
	public String getOpcion() {
		return opcion;
	}
}
