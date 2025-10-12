package managers;

import java.util.HashMap;

import logica.NotHotlineMiami;
import pantallas.*;

public class PantallaManager {
	private final NotHotlineMiami game;
    private HashMap<TipoPantalla, PantallaBase> pantallas;

    public PantallaManager(NotHotlineMiami game) {
        this.game = game;
        this.pantallas = new HashMap<>();
        initScreens();
    }
    
    private void initScreens() {
        // Crea una instancia de cada pantalla y la guarda en el mapa
        pantallas.put(TipoPantalla.MENU,		new PantallaMenu(game));
        pantallas.put(TipoPantalla.GAME_OVER, 	new PantallaGameOver(game));
    }
    
    public void changeScreen(TipoPantalla tipoPantalla) {
        if (tipoPantalla.equals(TipoPantalla.JUEGO)) {
            // Desecha la pantalla de juego anterior si existe
            PantallaBase oldGameScreen = pantallas.get(TipoPantalla.JUEGO);
            if (oldGameScreen != null) {
                oldGameScreen.dispose();
            }
            // Crea y establece la nueva pantalla de juego
            PantallaJuego nuevaPantallaJuego = new PantallaJuego(game, 1, 3, 0);
            pantallas.put(TipoPantalla.JUEGO, nuevaPantallaJuego);
            game.setScreen(nuevaPantallaJuego);
            return;
        }

        // Para otras pantallas como Menú o Game Over, podemos reutilizar la misma instancia
        PantallaBase screen = pantallas.get(tipoPantalla);
        if (screen != null) {
            game.setScreen(screen);
        } 
    }
    
    public void dispose() {
        for (PantallaBase screen : pantallas.values()) {
            if (screen == null) continue;
            
            screen.dispose();
        }
    }
}
