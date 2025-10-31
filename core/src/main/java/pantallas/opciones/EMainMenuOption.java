package pantallas.opciones;

import com.badlogic.gdx.Gdx;

import enumeradores.EScreenType;
import interfaces.INavigableOption;
import interfaces.IScreenAction;
import logica.MainGame;

public enum EMainMenuOption implements INavigableOption {
	INICIAR("Iniciar Juego", (manager) -> {
		manager.cambiarPantalla(EScreenType.JUEGO);
	}),
	PERSONALIZAR("Personalizar Personaje", (manager) -> {
		manager.cambiarPantalla(EScreenType.PERSONALIZACION);
	}),
	OPCIONES("Configuración", (manager) -> {
		manager.cambiarPantalla(EScreenType.CONFIGURACION);
	}),
	TUTORIAL("Tutorial", (manager) -> {
		manager.cambiarPantalla(EScreenType.TUTORIAL);
	}),
	SALIR("Salir del Juego", (manager) -> { Gdx.app.exit();});
	
	private final String nombre;
	private final IScreenAction accion;
	
	EMainMenuOption(String nombre, IScreenAction accion) {
		this.nombre = nombre;
		this.accion = accion;
	}
		
    public IScreenAction getAction() { return accion; }
    
    @Override
    public String getNombre() {
    	return nombre;
    }

	@Override
	public void ejecutar(MainGame game) {
		this.accion.ejecutar(game.getPantallaManager());
	}
}
