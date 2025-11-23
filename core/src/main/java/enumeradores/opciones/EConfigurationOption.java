package enumeradores.opciones;

import interfaces.INavigableOption;

public enum EConfigurationOption implements INavigableOption {
	VOLUMEN_GENERAL("Volumen General"),
	VOLUMEN_MUSICA("Volumen Música"),
	VOLUMEN_EFECTOS("Volumen Efectos"),
	GUARDAR("Guardar"),
	VOLVER("Volver");
	
	private final String nombre;
	
	EConfigurationOption(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String getName() {
		return nombre;
	}
}