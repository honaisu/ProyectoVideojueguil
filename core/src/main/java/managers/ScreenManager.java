package managers;

import java.util.EnumMap;
import java.util.Map;

import logica.MainGame;
import pantallas.*;
import pantallas.juego.GameOverScreen;
import pantallas.juego.GameScreen;
import pantallas.menus.ConfigurationScreen;
import pantallas.menus.CustomizationScreen;
import pantallas.menus.MainMenuScreen;
import pantallas.menus.PauseScreen;
import pantallas.menus.TutorialScreen;

public class ScreenManager {
	private final MainGame game;
    private Map<ScreenType, BaseScreen> pantallas;

    public ScreenManager(MainGame game) {
        this.game = game;
        this.pantallas = new EnumMap<>(ScreenType.class);
        this.iniciarPantallas();
    }
    
    private void iniciarPantallas() {
        // Crea una instancia de cada pantalla y la guarda en el mapa
    	pantallas.put(ScreenType.MENU,			new MainMenuScreen(game));
        pantallas.put(ScreenType.GAME_OVER, 		new GameOverScreen(game));
        pantallas.put(ScreenType.PERSONALIZACION, new CustomizationScreen(game));
        pantallas.put(ScreenType.CONFIGURACION, 	new ConfigurationScreen(game));
        pantallas.put(ScreenType.PAUSA, new PauseScreen(game));
        pantallas.put(ScreenType.TUTORIAL, new TutorialScreen(game));
    }
    
    public void cambiarPantalla(ScreenType tipoPantalla) {
        if (tipoPantalla.equals(ScreenType.JUEGO)) {
            // Se desecha la pantalla antigua
        	BaseScreen antiguaPantalla = pantallas.get(ScreenType.JUEGO);
            if (antiguaPantalla != null) antiguaPantalla.dispose();
            
            // Se reemplaza por una nueva pantalla de juego
            GameScreen nuevaPantallaJuego = new GameScreen(game);
            pantallas.put(ScreenType.JUEGO, nuevaPantallaJuego);
            game.setScreen(nuevaPantallaJuego);
            return;
        }

        BaseScreen screen = pantallas.get(tipoPantalla);
        if (screen != null) {
            game.setScreen(screen);
        } 
    }
    
    
    public void dispose() {
        for (BaseScreen screen : pantallas.values()) {
            if (screen == null) continue;
            screen.dispose();
        }
    }
}
