package pantallas.opciones;

import interfaces.NavigableOption;

public enum PauseOption implements NavigableOption {
	CONTINUAR("Continuar"),
	MENU("Menú Principal");
	
	private final String nombre;
	
	PauseOption(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}
}
