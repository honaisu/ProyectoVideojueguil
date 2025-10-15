package pantallas.opciones;

import com.badlogic.gdx.Gdx;

import pantallas.ScreenAction;
import pantallas.ScreenType;

public enum MenuOption implements NavigableOption {
	INICIAR("Iniciar Juego", (manager) -> {
		manager.cambiarPantalla(ScreenType.JUEGO);
	}),
	PERSONALIZAR("Personalizar Personaje", (manager) -> {
		manager.cambiarPantalla(ScreenType.PERSONALIZACION);
	}),
	OPCIONES("Configuración", (manager) -> {
		manager.cambiarPantalla(ScreenType.CONFIGURACION);
	}),
	TUTORIAL("Tutorial", (manager) -> {
		manager.cambiarPantalla(ScreenType.TUTORIAL);
	}),
	SALIR("Salir del Juego", (manager) -> { Gdx.app.exit();});
	
	private final String nombre;
	private final ScreenAction accion;
	
	MenuOption(String nombre, ScreenAction accion) {
		this.nombre = nombre;
		this.accion = accion;
	}
		
    public ScreenAction getAction() { return accion; }
    
    @Override
    public String getNombre() {
    	return nombre;
    }
}
