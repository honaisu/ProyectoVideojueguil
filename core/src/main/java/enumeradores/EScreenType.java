package enumeradores;

import java.util.function.Function;

import logica.MainGame;
import pantallas.*;
import pantallas.TutorialScreen;
import pantallas.menus.*;
import pantallas.juego.*;

public enum EScreenType {
	CUSTOMIZATION(g -> new CustomizationScreen(g)),
	CONFIGURATION(g -> new ConfigurationScreen(g)),
	TRANSITION(g -> new LevelTransitionScreen(g)),
	GAME_OVER(g -> new GameOverScreen(g)),
	TUTORIAL(g -> new TutorialScreen(g)),
	MENU(g -> new MainMenuScreen(g)),
	PAUSE(g -> new PauseScreen(g)),
	GAME(g -> new GameScreen(g)),
	WIN(g -> new WinScreen(g)),
	;
	
	private Function<MainGame, BaseScreen> screen;
	
	EScreenType(Function<MainGame, BaseScreen> factory) {
		screen = factory;
	}
	
	public BaseScreen createScreen(MainGame game) {
		return this.screen.apply(game);
	}

}
