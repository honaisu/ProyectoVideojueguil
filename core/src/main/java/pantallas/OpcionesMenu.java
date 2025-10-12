package pantallas;

public enum OpcionesMenu {
	INICIAR("Iniciar Juego"),
	PERSONALIZAR("Personalizar Personaje"),
	OPCIONES("Configuración"),
	TUTORIAL("Tutorial"),
	SALIR("Salir del Juego");
	
	private String opcion;
	
	OpcionesMenu(String opcion) {
		this.opcion = opcion;
	}
	
	public String getOpcion() {
		return opcion;
	}
}
