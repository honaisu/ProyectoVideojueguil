package pantallas.menus;

import logica.MainGame;
import pantallas.BaseScreen;
import pantallas.OptionNavigator;
import pantallas.opciones.NavigableOption;

public abstract class NavigableScreen extends BaseScreen {
	public NavigableScreen(MainGame game, NavigableOption[] opciones) {
		super(game);
		this.navegador = new OptionNavigator(opciones);
		
	}

	// Listado de Opciones que muestra el menú
	// TODO Cambiar a private
	protected final OptionNavigator navegador;
	
	public OptionNavigator getNavegador() {
		return navegador;
	}
}
