package pantallas.opciones;

public enum OpcionMenu {
	INICIAR("Iniciar Juego"),
	PERSONALIZAR("Personalizar Personaje"),
	OPCIONES("Configuración"),
	TUTORIAL("Tutorial"),
	SALIR("Salir del Juego");
	
	private String nombre;
	
	OpcionMenu(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
}
