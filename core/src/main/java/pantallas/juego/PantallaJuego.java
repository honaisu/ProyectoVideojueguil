package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.*;
import armas.proyectiles.Bullet;
import armas.proyectiles.Swing;
import logica.AssetsLoader;
import logica.NotHotlineMiami;

import managers.AsteroidManager;
import managers.BulletManager;
import managers.CollisionManager;
import managers.GameManager;
import managers.MeleeManager;
import pantallas.BaseScreen;
import personajes.Jugador;

public class PantallaJuego extends BaseScreen {
    private SpriteBatch batch;

    private Sound explosionSound;
    private Music gameMusic;


    private Jugador jugador;

    // Managers
    private GameManager gameManager;

    // Pausa
    private boolean paused = false;
    private float pauseToggleCooldown = 0f;
    private final float pauseToggleDelay = 0.2f;

    private float keyCooldown = 0f;
    private final float repeatDelay = 0.15f;

    // Overlay de pausa
    public PantallaJuego(NotHotlineMiami game) {
    	super(game);
    	
    	int x = Gdx.graphics.getWidth() / 2;
    	int y = Gdx.graphics.getHeight() / 2;
    	
    	jugador = new Jugador(x, y, new Melee());
    }

    public PantallaJuego(NotHotlineMiami game, int ronda, int vidas, int score) {
    	super(game);

        batch = game.getBatch();

        explosionSound = AssetsLoader.getInstancia().getExplosionSound();
        gameMusic = AssetsLoader.getInstancia().getGameMusic();
        gameMusic.setLooping(true);
        aplicarVolumenMusica();
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
        
        jugador.setVidas(vidas);
        jugador.setArma(new Escopeta());
    }
    
    private void aplicarVolumenMusica() {
        gameMusic.setVolume(game.getMusicVolume());
    }

    private void dibujaEncabezado() {
        
    }

    @Override
    public void render(float delta) {
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

            int gained = gameManager.getCollisionManager().handleCollisions(jugador, bulletManager, meleeManager, asteroidManager);
            if (gained > 0) score += gained;

            if (jugador.estaDestruido()) {
                if (score > game.getHighScore()) game.setHighScore(score);
                Screen ss = new GameOverScreen(game);
                ss.resize(1200, 800);
                game.setScreen(ss);
                dispose();
                return;
            }
            
            // TODO si AsteroidManager está vacío (?)
        }
    }
    
    // Hooks para que Armas agreguen entidades a sus managers
    public boolean agregarBala(Bullet b) { bulletManager.add(b); return true; }
    public void agregarSwing(Swing s) { meleeManager.add(s); }

    @Override public void show() { 
    	if (gameMusic != null) gameMusic.play(); 
    }

    @Override
    public void pause() {
        paused = true;
        gameMusic.setVolume(game.getMusicVolume() * 0.33f);
    }

    @Override
    public void resume() {
        paused = false;
        aplicarVolumenMusica();
        keyCooldown = repeatDelay;
    }

    @Override
    public void dispose() {
        if (explosionSound != null) explosionSound.dispose();
        if (gameMusic != null) gameMusic.dispose();
        if (gameManager != null) gameManager.dispose();
    }
    
	@Override
	protected void update(float delta) {
		
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		// Render
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gameManager.render(batch);
        jugador.draw(batch, this, paused, delta);

        if (paused) dibujarMenuPausa(delta);

        dibujaEncabezado();
        batch.end();
	}
}
