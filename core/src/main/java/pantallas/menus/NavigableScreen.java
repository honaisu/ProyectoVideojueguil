package pantallas.menus;

import interfaces.INavigableOption;
import logica.MainGame;
import logica.OptionNavigator;
import pantallas.BaseScreen;

public abstract class NavigableScreen extends BaseScreen {
	// Listado de Opciones que muestra el menú
	private final OptionNavigator navegador;
	
	public NavigableScreen(MainGame game, INavigableOption[] opciones) {
		super(game);
		this.navegador = new OptionNavigator(opciones);
	}
	
	public OptionNavigator getNavegador() {
		return navegador;
	}
}
