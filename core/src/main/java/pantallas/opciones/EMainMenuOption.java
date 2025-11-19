package pantallas.opciones;

import com.badlogic.gdx.Gdx;

import enumeradores.EScreenType;
import interfaces.IExecutableGame;
import interfaces.INavigableOption;
import interfaces.IScreenAction;
import logica.MainGame;

public enum EMainMenuOption implements INavigableOption, IExecutableGame {
	INICIAR("Iniciar Juego", (m) -> m.cambiarPantalla(EScreenType.GAME)),
	PERSONALIZAR("Personalizar Personaje", (m) -> m.cambiarPantalla(EScreenType.CUSTOMIZATION)),
	OPCIONES("Configuración", (m) -> m.cambiarPantalla(EScreenType.CONFIGURATION)),
	TUTORIAL("Tutorial", (m) -> m.cambiarPantalla(EScreenType.TUTORIAL)),
	SALIR("Salir del Juego", (m) -> Gdx.app.exit());
	
	private final String nombre;
	private final IScreenAction accion;
	
	EMainMenuOption(String nombre, IScreenAction accion) {
		this.nombre = nombre;
		this.accion = accion;
	}
		
    public IScreenAction getAction() { return accion; }
    
    @Override
    public String getName() {
    	return nombre;
    }

	@Override
	public void ejecutar(MainGame game) {
		this.accion.ejecutar(game.getPantallaManager());
	}
}
