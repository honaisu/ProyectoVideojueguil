package managers;

import java.util.EnumMap;
import java.util.Map;

import enumeradores.EScreenType;
import logica.MainGame;
import pantallas.BaseScreen;


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
    	for (EScreenType screen : EScreenType.values()) {
    		pantallas.put(screen, screen.createScreen(game));
    	}
    }
    
    public void cambiarPantalla(EScreenType tipoPantalla) {
        if (tipoPantalla.equals(EScreenType.JUEGO) || tipoPantalla.equals(EScreenType.TRANSICION)) {
            // Se desecha la pantalla antigua
        	BaseScreen antiguaPantalla = pantallas.get(EScreenType.JUEGO);
            if (antiguaPantalla != null) antiguaPantalla.dispose();
            
            // Se reemplaza por una nueva pantalla de juego
            BaseScreen nuevaPantalla = tipoPantalla.createScreen(game);
            pantallas.put(tipoPantalla, nuevaPantalla);
            game.setScreen(nuevaPantalla);
            return;
        }
        
        BaseScreen screen = pantallas.get(tipoPantalla);
        if (screen == null) return;
        game.setScreen(screen);
    }
    
    
    public void dispose() {
        for (BaseScreen screen : pantallas.values()) {
            if (screen == null) continue;
            screen.dispose();
        }
    }
}
