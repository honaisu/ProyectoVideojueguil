package enumeradores;

import java.util.function.Function;

import logica.MainGame;
import pantallas.*;
import pantallas.TutorialScreen;
import pantallas.menus.*;
import pantallas.juego.*;

public enum EScreenType {

	MENU(g -> new MainMenuScreen(g)),
	JUEGO(g -> new GameScreen(g)),
	PERSONALIZACION(g -> new CustomizationScreen(g)),
	CONFIGURACION(g -> new ConfigurationScreen(g)),
	TUTORIAL(g -> new TutorialScreen(g)),
	PAUSA(g -> new PauseScreen(g)),
	TRANSICION(g -> new LevelTransitionScreen(g)),
	GAME_OVER(g -> new GameOverScreen(g)),
	VICTORIA(g -> new WinScreen(g));
	
	private Function<MainGame, BaseScreen> screen;
	
	EScreenType(Function<MainGame, BaseScreen> factory) {
		screen = factory;
	}
	
	public BaseScreen createScreen(MainGame game) {
		return this.screen.apply(game);
	}

}
