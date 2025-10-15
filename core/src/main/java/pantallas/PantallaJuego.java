package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.*;
import armas.proyectiles.Bullet;
import armas.proyectiles.Swing;
import logica.AssetsLoader;
import logica.NotHotlineMiami;
import managers.AsteroidManager;
import managers.BulletManager;
import managers.CollisionManager;
import managers.MeleeManager;
import personajes.Jugador;

public class PantallaJuego extends PantallaBase {
    private SpriteBatch batch;

    private Sound explosionSound;
    private Music gameMusic;

    private int score;
    private int ronda;
    private int velXAsteroides = 2;
    private int velYAsteroides = 2;
    private int cantAsteroides = 5;

    private Jugador jugador;

    // Managers
    private AsteroidManager asteroidManager;
    private BulletManager bulletManager;
    private CollisionManager collisionManager;
    private MeleeManager meleeManager;

    // Pausa
    private boolean paused = false;
    private float pauseToggleCooldown = 0f;
    private final float pauseToggleDelay = 0.2f;

    private final String[] pauseOptions = { "Continuar", "Menu principal" };
    private int pauseSelec = 0;
    private float keyCooldown = 0f;
    private final float repeatDelay = 0.15f;

    // Overlay de pausa
    private Texture texturaPausa;

    public PantallaJuego(NotHotlineMiami game, int ronda, int vidas, int score) {
        super(game);
        this.ronda = ronda;
        this.score = score;

        batch = game.getBatch();

        explosionSound = AssetsLoader.getInstancia().getExplosionSound();
        gameMusic = AssetsLoader.getInstancia().getGameMusic();
        gameMusic.setLooping(true);
        aplicarVolumenMusica();
        gameMusic.play();


        // Managers
        asteroidManager = new AsteroidManager(cantAsteroides, velXAsteroides, velYAsteroides);
        bulletManager = new BulletManager();
        meleeManager = new MeleeManager();
        //collisionManager = new CollisionManager(explosionSound, 10); // 10 pts por asteroide
        collisionManager = new CollisionManager(game);
        
        //Crear Jugador
        jugador = new Jugador(
            Gdx.graphics.getWidth() / 2, 	// X
            Gdx.graphics.getHeight() / 2,	// Y
            0f,								// ROTACION
            new Melee(),					// ARMA PRINCIPAL
            game.getSkinSelected()			// SKIN
        );
        
        jugador.setVidas(vidas);
        


        //========================
        //Probar armas
        //========================
        //nave.setArma(new Metralleta());
        //nave.setArma(new Escopeta());
        jugador.setArma(new CanonLaser());



        // Crear textura overlay 1x1 para la pausa
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(Color.WHITE);
        pm.fill();
        texturaPausa = new Texture(pm);
        pm.dispose();
    }
    
    private void aplicarVolumenMusica() {
        float mv = game.getMasterVolume();
        float mus = game.getMusicVolume();
        gameMusic.setVolume(mv * mus);
    }

    private void dibujaEncabezado() {
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, "Vidas: " + jugador.getVidas() + " Ronda: " + ronda, 10, 30);
        game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);

        // Munición arma (si aplica)
        //if (nave.getArma() == null || nave.getArma() instanceof Melee) return;
        
        if (jugador.getArma() != null && !(jugador.getArma() instanceof Melee)) {
            int mun = jugador.getArma().getMunicion();
            int max = jugador.getArma().getMunicionMax();
            game.getFont().draw(batch, "Municion: " + mun + " / " + max,
                    Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 20);
        }
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
            bulletManager.update();
            meleeManager.update(delta);
            asteroidManager.update();

            int gained = collisionManager.handleCollisions(jugador, bulletManager, meleeManager, asteroidManager);
            if (gained > 0) score += gained;

            if (jugador.estaDestruido()) {
                if (score > game.getHighScore()) game.setHighScore(score);
                Screen ss = new PantallaGameOver(game);
                ss.resize(1200, 800);
                game.setScreen(ss);
                dispose();
                return;
            }
            
            // TODO
            if (asteroidManager.isEmpty()) {
                
            }
        }

        // Render
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        bulletManager.render(batch);
        meleeManager.render(batch);
        jugador.draw(batch, this, paused, delta);
        asteroidManager.render(batch);

        if (paused) dibujarMenuPausa(delta);

        dibujaEncabezado();
        batch.end();
    }

    private void dibujarMenuPausa(float delta) {
    	final Color transparente = new Color(0f, 0f, 0f, 0.45f);
    	
        // Overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(transparente);
        batch.draw(texturaPausa, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(Color.WHITE);

        // Navegación
        keyCooldown -= delta;
        if (keyCooldown <= 0f) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP))   { pauseSelec = (pauseSelec - 1 + pauseOptions.length) % pauseOptions.length; keyCooldown = repeatDelay; }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { pauseSelec = (pauseSelec + 1) % pauseOptions.length; keyCooldown = repeatDelay; }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (pauseSelec == 0) {
                resume();
            } else {
                Screen ss = new PantallaMenu(game);
                ss.resize(1200, 800);
                game.setScreen(ss);
                dispose();
                return;
            }
        }

        // Texto
        float panelW = 520f, panelH = 260f;
        float px = (Gdx.graphics.getWidth() - panelW) / 2f;
        float py = (Gdx.graphics.getHeight() - panelH) / 2f;

        game.getFont().getData().setScale(2.0f);
        game.getFont().draw(batch, "PAUSA", px + 190, py + 200);

        game.getFont().getData().setScale(1.6f);
        for (int i = 0; i < pauseOptions.length; i++) {
            boolean sel = (i == pauseSelec);
            String text = (sel ? "> " : "  ") + pauseOptions[i] + (sel ? " <" : "");
            game.getFont().draw(batch, text, px + 120, py + 130 - i * 60);
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
        if (texturaPausa != null) texturaPausa.dispose();
        if (asteroidManager != null) asteroidManager.clear();
        if (bulletManager != null) bulletManager.clear();
        if (meleeManager != null) meleeManager.clear();
    }

    // Accesores para managers (si los necesitan las Armas o sistemas externos)
    public AsteroidManager getAsteroidManager() { return asteroidManager; }
    public BulletManager getBulletManager() { return bulletManager; }
    public CollisionManager getCollisionManager() { return collisionManager; }
    public MeleeManager getMeleeManager() { return meleeManager; }

	@Override
	protected void update(float delta) {
		
	}

	@Override
	protected void draw() {
		// TODO Auto-generated method stub
		
	}
}
