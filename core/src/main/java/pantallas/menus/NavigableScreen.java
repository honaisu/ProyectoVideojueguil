package pantallas.menus;

import interfaces.NavigableOption;
import logica.MainGame;
import logica.OptionNavigator;
import pantallas.BaseScreen;

public abstract class NavigableScreen extends BaseScreen {
	// TODO Cambiar a private
	// Listado de Opciones que muestra el menú
	protected final OptionNavigator navegador;
	
	public NavigableScreen(MainGame game, NavigableOption[] opciones) {
		super(game);
		this.navegador = new OptionNavigator(opciones);
	}
	
	public OptionNavigator getNavegador() {
		return navegador;
	}
}
