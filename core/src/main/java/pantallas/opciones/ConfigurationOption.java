package pantallas.opciones;

import interfaces.NavigableOption;

public enum ConfigurationOption implements NavigableOption {
	VOLUMEN_GENERAL("Volumen General"),
	VOLUMEN_MUSICA("Volumen Música"),
	VOLUMEN_EFECTOS("Volumen Efectos"),
	GUARDAR("Guardar"),
	VOLVER("Volver");
	
	private final String nombre;
	
	ConfigurationOption(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}
}