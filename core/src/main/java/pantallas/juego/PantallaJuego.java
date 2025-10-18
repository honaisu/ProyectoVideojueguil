package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logica.MainGame;
import logica.assets.AssetManager;
import managers.GameManager;
import pantallas.BaseScreen;
import pantallas.ScreenType;

public class PantallaJuego extends BaseScreen {
    private Sound explosionSound = AssetManager.getInstancia().getExplosionSound();
    private Music gameMusic = AssetManager.getInstancia().getGameMusic();

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

        gameMusic.setLooping(true);
        gameMusic.setVolume(game.getVolumen().getMusicVolume());
        gameMusic.play();
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
        gameMusic.setVolume(getGame().getVolumen().getMusicVolume());
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
        /*
        if (!paused && !game.getPlayer().estaHerido()) {
            gameManager.update(delta);

            int gained = gameManager.getCollisionManager().handleCollisions(game.getPlayer(), 
            		gameManager.getBulletManager(), 
            		gameManager.getMeleeManager(), 
            		gameManager.getAsteroidManager());
            if (gained > 0) game.getPlayer().aumentarScore(gained);

            if (game.getPlayer().estaDestruido()) {
                if (game.getPlayer().getScore() > game.getHighScore()) game.setHighScore(game.getPlayer().getScore());
                game.getPantallaManager().cambiarPantalla(ScreenType.GAME_OVER);
                return;
            }
            
            // TODO si AsteroidManager está vacío (?)
        }*/
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		// Render
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //gameManager.render(batch);
        //getGame().getPlayer().draw(batch);
        // TODO reemplazarlo con HUD screen o algo similar
        if (paused) getGame().getPantallaManager().cambiarPantalla(ScreenType.PAUSA);
        batch.end();
	}
}
