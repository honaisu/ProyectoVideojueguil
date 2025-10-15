package pantallas.opciones;

public enum PauseOption {
	CONTINUAR("Continuar"),
	MENU("Menú Principal");
	
	private String nombre;
	
	PauseOption(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
}
