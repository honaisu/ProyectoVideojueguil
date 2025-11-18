package enumeradores;

import java.util.function.Function;

import logica.MainGame;
import pantallas.*;
import pantallas.TutorialScreen;
import pantallas.menus.*;
import pantallas.juego.*;

public enum EScreenType {
	PERSONALIZACION(g -> new CustomizationScreen(g)),
	CONFIGURACION(g -> new ConfigurationScreen(g)),
	TRANSICION(g -> new LevelTransitionScreen(g)),
	GAME_OVER(g -> new GameOverScreen(g)),
	TUTORIAL(g -> new TutorialScreen(g)),
	MENU(g -> new MainMenuScreen(g)),
	PAUSA(g -> new PauseScreen(g)),
	JUEGO(g -> new GameScreen(g)),
	;
	
	private Function<MainGame, BaseScreen> screen;
	
	EScreenType(Function<MainGame, BaseScreen> factory) {
		screen = factory;
	}
	
	public BaseScreen createScreen(MainGame game) {
		return this.screen.apply(game);
	}
}
