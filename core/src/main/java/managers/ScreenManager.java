package managers;

import java.util.EnumMap;
import java.util.Map;

import logica.MainGame;
import pantallas.*;
import pantallas.juego.GameOverScreen;
import pantallas.juego.PantallaJuego;
import pantallas.menus.ConfigurationScreen;
import pantallas.menus.CustomizationScreen;
import pantallas.menus.MainMenuScreen;
import pantallas.menus.PauseScreen;

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
    }
    
    public void cambiarPantalla(ScreenType tipoPantalla) {
        if (tipoPantalla.equals(ScreenType.JUEGO)) {
            // Desecha la pantalla de juego anterior si existe
            BaseScreen antiguaPantalla = pantallas.get(ScreenType.JUEGO);
            if (antiguaPantalla != null) antiguaPantalla.dispose();
            
            // Crea y establece la nueva pantalla de juego
            PantallaJuego nuevaPantallaJuego = new PantallaJuego(game);
            pantallas.put(ScreenType.JUEGO, nuevaPantallaJuego);
            game.setScreen(nuevaPantallaJuego);
            return;
        }

        // Para otras pantallas como Menú o Game Over, podemos reutilizar la misma instancia
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
