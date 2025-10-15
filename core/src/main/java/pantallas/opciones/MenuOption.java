package pantallas.opciones;

import com.badlogic.gdx.Gdx;

import pantallas.ScreenAction;
import pantallas.ScreenType;

public enum MenuOption {
	INICIAR("Iniciar Juego", (game) -> {
		game.getPantallaManager().cambiarPantalla(ScreenType.JUEGO);
	}),
	PERSONALIZAR("Personalizar Personaje", (game) -> {
		game.getPantallaManager().cambiarPantalla(ScreenType.PERSONALIZACION);
	}),
	OPCIONES("Configuración", (game) -> {
		game.getPantallaManager().cambiarPantalla(ScreenType.CONFIGURACION);
	}),
	TUTORIAL("Tutorial", (game) -> {
		game.getPantallaManager().cambiarPantalla(ScreenType.TUTORIAL);
	}),
	SALIR("Salir del Juego", (game) -> { Gdx.app.exit();});
	
	private final String nombre;
	private final ScreenAction accion;
	
	MenuOption(String nombre, ScreenAction accion) {
		this.nombre = nombre;
		this.accion = accion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
    public ScreenAction getAction() { return accion; }
}
