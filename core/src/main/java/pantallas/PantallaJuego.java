package pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.Bullet;
import personajes.Jugador; // eliminar ne caso de el merch
import managers.AsteroidManager;
import managers.BulletManager;
import managers.CollisionManager;
import personajes.SpaceNavigation;

public class PantallaJuego implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Sound explosionSound;
	private Music gameMusic;

	private int score;
	private int ronda;
	private int velXAsteroides;
	private int velYAsteroides;
	private int cantAsteroides;

	private Jugador nave;

	// Managers
	private AsteroidManager asteroidManager;
	private BulletManager bulletManager;
	private CollisionManager collisionManager;

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

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,
	                     int velXAsteroides, int velYAsteroides, int cantAsteroides) {
	    this.game = game;
	    this.ronda = ronda;
	    this.score = score;
	    this.velXAsteroides = velXAsteroides;
	    this.velYAsteroides = velYAsteroides;
	    this.cantAsteroides = cantAsteroides;

	    batch = game.getBatch();
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 640);

	    // Audio
	    explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));

	    gameMusic = Gdx.audio.newMusic(Gdx.files.internal("musicaDoom.mp3"));
	    gameMusic.setLooping(true);
	    aplicarVolumenMusica();
	    gameMusic.play();

	    // Managers
	    asteroidManager = new AsteroidManager(cantAsteroides, velXAsteroides, velYAsteroides);
	    bulletManager = new BulletManager();
	    collisionManager = new CollisionManager(explosionSound, 10);

	    // Jugador (lógica Nave4)
	    String path = (game.getSelectedShipPath() != null) ? game.getSelectedShipPath() : "MainShip3.png";
	    Texture naveTex = new Texture(Gdx.files.internal(path));

	    nave = new Jugador(
	        Gdx.graphics.getWidth()/2 - 50, 30,
	        naveTex,
	        cargarSoundSeguro("hurt.mp3", "hurt.ogg"),
	        new Texture(Gdx.files.internal("Rocket2.png")),
	        cargarSoundSeguro("pop-sound.mp3", "pop-sound.ogg"),
	        game
	    );
	    game.setJugador(nave);
	    nave.setVidas(vidas);

	    // Crear textura de overlay 1x1
	    com.badlogic.gdx.graphics.Pixmap pm = new com.badlogic.gdx.graphics.Pixmap(
	        1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888
	    );
	    pm.setColor(1, 1, 1, 1);
	    pm.fill();
	    texturaPausa = new Texture(pm);
	    pm.dispose();
	}

	private Sound cargarSoundSeguro(String primario, String alterno) {
	    if (Gdx.files.internal(primario).exists()) return Gdx.audio.newSound(Gdx.files.internal(primario));
	    if (Gdx.files.internal(alterno).exists()) return Gdx.audio.newSound(Gdx.files.internal(alterno));
	    return null;
	}

	private void aplicarVolumenMusica() {
	    float mv = game.getMasterVolume();
	    float mus = game.getMusicVolume();
	    gameMusic.setVolume(mv * mus);
	}

	private void dibujaEncabezado() {
	    game.getFont().getData().setScale(2f);
	    game.getFont().draw(batch, "Vidas: " + nave.getVidas() + " Ronda: " + ronda, 10, 30);
	    game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 150, 30);
	    game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
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

	    // Update solo si no está pausado y no está herido
	    if (!paused && !nave.estaHerido()) {
	        bulletManager.update(delta);
	        asteroidManager.update();

	        int gained = collisionManager.handleCollisions(nave, bulletManager, asteroidManager);
	        if (gained > 0) score += gained;

	        if (nave.estaDestruido()) {
	            if (score > game.getHighScore()) game.setHighScore(score);
	            Screen ss = new PantallaGameOver(game);
	            ss.resize(1200, 800);
	            game.setScreen(ss);
	            dispose();
	            return;
	        }

	        if (asteroidManager.isEmpty()) {
	            Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), score,
	                    velXAsteroides + 3, velYAsteroides + 3, cantAsteroides + 10);
	            ss.resize(1200, 800);
	            game.setScreen(ss);
	            dispose();
	            return;
	        }
	    }

	    // Render
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    dibujaEncabezado();

	    bulletManager.render(batch);
	    nave.draw(batch, this, paused);
	    asteroidManager.render(batch);

	    if (paused) dibujarMenuPausa(delta);

	    batch.end();
	}

	private void dibujarMenuPausa(float delta) {
	    // Overlay de atenuación
	    Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    batch.setColor(0f, 0f, 0f, 0.45f);
	    batch.draw(texturaPausa, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    batch.setColor(1f, 1f, 1f, 1f);

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

	    // Panel y textos
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

	// Punto de inserción para balas (usado por Jugador/armas)
	public boolean agregarBala(Bullet bb) { bulletManager.add(bb); return true; }

	@Override public void show() { gameMusic.play(); }
	@Override public void resize(int width, int height) {}

	@Override
	public void pause() {
	    paused = true;
	    float mv = game.getMasterVolume();
	    float mus = game.getMusicVolume();
	    gameMusic.setVolume(mv * mus * 0.33f);
	}

	@Override
	public void resume() {
	    paused = false;
	    aplicarVolumenMusica();
	    keyCooldown = repeatDelay;
	}

	@Override public void hide() {}

	@Override
	public void dispose() {
	    if (explosionSound != null) explosionSound.dispose();
	    if (gameMusic != null) gameMusic.dispose();
	    if (texturaPausa != null) texturaPausa.dispose();
	    if (asteroidManager != null) asteroidManager.clear();
	    if (bulletManager != null) bulletManager.clear();
	}


}
