package managers;

import java.util.EnumMap;
import java.util.Map;

import enumeradores.EScreenType;
import logica.MainGame;
import pantallas.*;
import pantallas.juego.GameOverScreen;
import pantallas.juego.GameScreen;
import pantallas.juego.PauseScreen;
import pantallas.juego.LevelTransitionScreen;
import pantallas.menus.ConfigurationScreen;
import pantallas.menus.CustomizationScreen;
import pantallas.menus.MainMenuScreen;


public class ScreenManager {
	private final MainGame game;
    private Map<EScreenType, BaseScreen> pantallas;

    public ScreenManager(MainGame game) {
        this.game = game;
        this.pantallas = new EnumMap<>(EScreenType.class);
        this.iniciarPantallas();
    }
    
    private void iniciarPantallas() {
        // Crea una instancia de cada pantalla y la guarda en el mapa
    	// TODO Implementar lógica cambiada de esto para no hacerlo como está ahora :)
    	pantallas.put(EScreenType.MENU,			new MainMenuScreen(game));
        pantallas.put(EScreenType.GAME_OVER, 		new GameOverScreen(game));
        pantallas.put(EScreenType.PERSONALIZACION, new CustomizationScreen(game));
        pantallas.put(EScreenType.CONFIGURACION, 	new ConfigurationScreen(game));
        pantallas.put(EScreenType.PAUSA, new PauseScreen(game));
        pantallas.put(EScreenType.TUTORIAL, new TutorialScreen(game));
    }
    
    public void cambiarPantalla(EScreenType tipoPantalla) {
        if (tipoPantalla.equals(EScreenType.JUEGO)) {
            // Se desecha la pantalla antigua
        	BaseScreen antiguaPantalla = pantallas.get(EScreenType.JUEGO);
            if (antiguaPantalla != null) antiguaPantalla.dispose();
            
            // Se reemplaza por una nueva pantalla de juego
            GameScreen nuevaPantallaJuego = new GameScreen(game);
            pantallas.put(EScreenType.JUEGO, nuevaPantallaJuego);
            game.setScreen(nuevaPantallaJuego);
            return;
        }
        
        //codigo específico para la transicion de niveles
        if (tipoPantalla.equals(EScreenType.TRANSICION)) {
            // Desechamos la pantalla antigua si existe
            BaseScreen antiguaPantalla = pantallas.get(EScreenType.TRANSICION);
            if (antiguaPantalla != null) antiguaPantalla.dispose();

            // Creamos una INSTANCIA NUEVA que lee el nivel correcto
            LevelTransitionScreen nuevaPantallaTransicion = new LevelTransitionScreen(game);
            pantallas.put(EScreenType.TRANSICION, nuevaPantallaTransicion);
            game.setScreen(nuevaPantallaTransicion);
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
