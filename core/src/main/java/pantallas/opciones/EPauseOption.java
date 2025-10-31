package pantallas.opciones;

import interfaces.INavigableOption;

public enum EPauseOption implements INavigableOption {
	CONTINUAR("Continuar"),
	MENU("Menú Principal");
	
	private final String nombre;
	
	EPauseOption(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}
}
