package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.*;
import logica.AssetsLoader;
import logica.MainGame;

import managers.GameManager;
import pantallas.BaseScreen;
import pantallas.ScreenType;
import personajes.Jugador;

public class PantallaJuego extends BaseScreen {
    private Sound explosionSound = AssetsLoader.getInstancia().getExplosionSound();
    private Music gameMusic = AssetsLoader.getInstancia().getGameMusic();

    // Managers
    private GameManager gameManager;

    // Pausa
    private boolean paused = false;
    private float pauseToggleCooldown = 0f;
    private final float pauseToggleDelay = 0.2f;

    // Overlay de pausa
    public PantallaJuego(MainGame game) {
    	super(game);
    }

    public PantallaJuego(MainGame game, int ronda, int vidas, int score) {
    	super(game);

        explosionSound = AssetsLoader.getInstancia().getExplosionSound();
        gameMusic = AssetsLoader.getInstancia().getGameMusic();
        gameMusic.setLooping(true);
        gameMusic.setVolume(game.getVolumen().getMusicVolume());
        gameMusic.play();
        
        //Crear Jugador
        /*
        jugador = new Jugador(
            Gdx.graphics.getWidth() / 2, 	// X
            Gdx.graphics.getHeight() / 2,	// Y
            0f,								// ROTACION
            new Melee(),					// ARMA PRINCIPAL
            game.getSkinSelected()			// SKIN
        );*/
        
        //jugador.setVidas(vidas);
        //jugador.setArma(new Escopeta());
    }

    @Override public void show() { 
    	if (gameMusic != null) gameMusic.play(); 
    }

    @Override
    public void pause() {
        paused = true;
        gameMusic.setVolume(gameMusic.getVolume() * 0.33f);
    }

    @Override
    public void resume() {
        paused = false;
        gameMusic.setVolume(game.getVolumen().getMusicVolume());
    }

    @Override
    public void dispose() {
        if (explosionSound != null) explosionSound.dispose();
        if (gameMusic != null) gameMusic.dispose();
        if (gameManager != null) gameManager.dispose();
    }
    
	@Override
	protected void update(float delta) {
		// Toggle pausa con ESC
        pauseToggleCooldown -= delta;
        if (pauseToggleCooldown <= 0f && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pauseToggleCooldown = pauseToggleDelay;
            if (!paused) pause();
            else resume();
        }

        // Update si no está en pausa ni herido
        if (!paused && !jugador.estaHerido()) {
            gameManager.update(delta);

            int gained = gameManager.getCollisionManager().handleCollisions(jugador, 
            		gameManager.getBulletManager(), 
            		gameManager.getMeleeManager(), 
            		gameManager.getAsteroidManager());
            if (gained > 0) jugador.aumentarScore(gained);

            if (jugador.estaDestruido()) {
                if (jugador.getScore() > game.getHighScore()) game.setHighScore(jugador.getScore());
                game.getPantallaManager().cambiarPantalla(ScreenType.GAME_OVER);
                return;
            }
            
            // TODO si AsteroidManager está vacío (?)
        }
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		// Render
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gameManager.render(batch);
        jugador.draw(batch, this, paused, 0);
        // TODO reemplazarlo con HUD screen o algo similar
        if (paused) game.getPantallaManager().cambiarPantalla(ScreenType.PAUSA);
        dibujaEncabezado();
        batch.end();
	}
}
