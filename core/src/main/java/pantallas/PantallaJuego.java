package pantallas;

import java.util.ArrayList;
import java.util.Random;

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
import enemigos.Ball2;
import personajes.Jugador;
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
	private ArrayList<Ball2> balls1 = new ArrayList<>();
	private ArrayList<Ball2> balls2 = new ArrayList<>();
	private ArrayList<Bullet> balas = new ArrayList<>();

	// Cosa para que en la propia pantalla de juego se pause
	// y estos vendrian a serr los controladores para el "pausa"
	private boolean paused = false;
	private float pauseToggleCooldown = 0f;
	private final float pauseToggleDelay = 0.2f;

	// Menu de pausa como tal
	private final String[] pauseOptions = { "Continuar", "Menu principal" };
	private int pauseSelec = 0;
	private float keyCooldown = 0f;
	private final float repeatDelay = 0.15f;

	// Pixel 1x1 para sombrear (overlay) generado en runtime
	private Texture pixel1x1;

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

	    // inicializar assets; musica de fondo y efectos de sonido
	    explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
	    explosionSound.setVolume(1, 0.2f); // valor base, luego se aplica master*sfx al reproducir

	    // antiguo
	    // gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); //
	    /* gameMusic.setLooping(true);
	       gameMusic.setVolume(0.5f);//se baja el volumen aqui
	       gameMusic.play(); */
	    gameMusic = Gdx.audio.newMusic(Gdx.files.internal("musicaDoom.mp3"));
	    gameMusic.setLooping(true);

	    aplicarVolumenMusica();
	    gameMusic.play();

	    // Skin elegida por path (fallback a default)
	    String path = (game.getSelectedShipPath() != null) ? game.getSelectedShipPath() : "MainShip3.png";
	    Texture naveTex = new Texture(Gdx.files.internal(path));

	    // cargar imagen de la nave, 64x64
	    // IMPORTANTE: pasar 'game' al Jugador para que use master*sfx en sus sonidos
	    nave = new Jugador(
	        Gdx.graphics.getWidth()/2 - 50, 30,
	        naveTex,
	        Gdx.audio.newSound(Gdx.files.internal("hurt.mp3")),
	        new Texture(Gdx.files.internal("Rocket2.png")),
	        Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")),
	        game // <- referencia para volúmenes globales
	    );
	    game.setJugador(nave);
	    nave.setVidas(vidas);

	    // crear asteroides
	    Random r = new Random();
	    for (int i = 0; i < cantAsteroides; i++) {
	        Ball2 bb = new Ball2(
	            r.nextInt((int) Gdx.graphics.getWidth()),
	            50 + r.nextInt((int) Gdx.graphics.getHeight() - 50),
	            20 + r.nextInt(10),
	            velXAsteroides + r.nextInt(4),
	            velYAsteroides + r.nextInt(4),
	            new Texture(Gdx.files.internal("aGreyMedium4.png"))
	        );
	        balls1.add(bb);
	        balls2.add(bb);
	    }

	    // Crear pixel 1x1 para overlay translúcido (sin asset extra)
	    com.badlogic.gdx.graphics.Pixmap pm = new com.badlogic.gdx.graphics.Pixmap(1,1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
	    pm.setColor(1,1,1,1);
	    pm.fill();
	    pixel1x1 = new Texture(pm);
	    pm.dispose();
	}

	// Helper para aplicar master*music a la música actual
	private void aplicarVolumenMusica() {
	    float mv = game.getMasterVolume();
	    float mus = game.getMusicVolume();
	    gameMusic.setVolume(mv * mus);
	}

	public void dibujaEncabezado() {
	    CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
	    game.getFont().getData().setScale(2f);
	    game.getFont().draw(batch, str, 10, 30);
	    game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 150, 30);
	    game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
	}

	@Override
	public void render(float delta) {
	    // ESC solo decide si llamar a pause() o resume(), no modifica flags directamente
	    pauseToggleCooldown -= delta;
	    if (pauseToggleCooldown <= 0f && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
	        pauseToggleCooldown = pauseToggleDelay;
	        if (!paused) pause();
	        else resume();
	    }

	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    dibujaEncabezado();

	    // Actualizar SOLO si no está pausado y no está herido
	    if (!paused && !nave.estaHerido()) {
	        // colisiones entre balas y asteroides y su destruccion
	        for (int i = 0; i < balas.size(); i++) {
	            Bullet b = balas.get(i);
	            b.update();
	            for (int j = 0; j < balls1.size(); j++) {
	                if (b.checkCollision(balls1.get(j))) {
	                    // reproducir SFX aplicando volúmenes globales
	                    float mv = game.getMasterVolume();
	                    float sfx = game.getSfxVolume();
	                    explosionSound.play(mv * sfx);

	                    balls1.remove(j);
	                    balls2.remove(j);
	                    j--;
	                    score += 10;
	                }
	            }
	            if (b.isDestroyed()) {
	                balas.remove(b);
	                i--; // para no saltarse 1 tras eliminar del arraylist
	            }
	        }
	        // actualizar movimiento de asteroides dentro del area
	        for (Ball2 ball : balls1) ball.update();
	        // colisiones entre asteroides y sus rebotes
	        for (int i = 0; i < balls1.size(); i++) {
	            Ball2 ball1 = balls1.get(i);
	            for (int j = 0; j < balls2.size(); j++) {
	                Ball2 ball2 = balls2.get(j);
	                if (i < j) ball1.checkCollision(ball2);
	            }
	        }
	    }

	    // Dibujar siempre (para ver el fondo cuando está pausado)
	    for (Bullet b : balas) b.draw(batch);

	    //esto es para probar si funciona el que el jugador se quede quieto en el menu de pausa
	    // Pasamos 'paused' a Jugador para que no procese input ni mueva/dispare en pausa
	    nave.draw(batch, this, paused);

	    for (int i = 0; i < balls1.size(); i++) {
	        Ball2 b = balls1.get(i);
	        b.draw(batch);
	        if (!paused && nave.checkCollision(b)) {
	            balls1.remove(i);
	            balls2.remove(i);
	            i--;
	        }
	    }

	    // Overlay y menú de pausa
	    if (paused) {
	        // Oscurecer con rectángulo negro translúcido
	        batch.setColor(0f, 0f, 0f, 0.45f);
	        batch.draw(pixel1x1, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        batch.setColor(1f, 1f, 1f, 1f);

	        // Navegación del menú de pausa
	        keyCooldown -= delta;
	        if (keyCooldown <= 0f) {
	            if (Gdx.input.isKeyPressed(Input.Keys.UP))   { pauseSelec = (pauseSelec - 1 + pauseOptions.length) % pauseOptions.length; keyCooldown = repeatDelay; }
	            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { pauseSelec = (pauseSelec + 1) % pauseOptions.length; keyCooldown = repeatDelay; }
	        }
	        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
	            if (pauseSelec == 0) {
	                // Continuar
	                resume();
	            } else {
	                // Menu principal
	                Screen ss = new PantallaMenu(game);
	                ss.resize(1200, 800);
	                game.setScreen(ss);
	                dispose();
	                batch.end();
	                return;
	            }
	        }

	        // Panel y textos del menú de pausa
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

	    // Game Over solo si no está en pausa
	    if (!paused && nave.estaDestruido()) {
	        if (score > game.getHighScore()) game.setHighScore(score);
	        Screen ss = new PantallaGameOver(game);
	        ss.resize(1200, 800);
	        game.setScreen(ss);
	        dispose();
	    }

	    batch.end();

	    // Avance de ronda solo si no está en pausa
	    if (!paused && balls1.size() == 0) {
	        Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), score,
	                velXAsteroides + 3, velYAsteroides + 3, cantAsteroides + 10);
	        ss.resize(1200, 800);
	        game.setScreen(ss);
	        dispose();
	    }
	}

	public boolean agregarBala(Bullet bb) { return balas.add(bb); }

	@Override public void show() { gameMusic.play(); }
	@Override public void resize(int width, int height) {}

	@Override
	public void pause() {
	    // Activar pausa y bajar música respetando volúmenes globales
	    paused = true;
	    float mv = game.getMasterVolume();
	    float mus = game.getMusicVolume();
	    gameMusic.setVolume(mv * mus * 0.33f);
	}

	@Override
	public void resume() {
	    // Desactivar pausa y restaurar volumen
	    paused = false;
	    aplicarVolumenMusica();
	    keyCooldown = repeatDelay; // anti doble enter al continuar
	}

	@Override public void hide() {}

	@Override
	public void dispose() {
	    if (explosionSound != null) explosionSound.dispose();
	    if (gameMusic != null) gameMusic.dispose();
	    if (pixel1x1 != null) pixel1x1.dispose();
	    // Las Textures creadas aquí deberían disponerse al cerrar nivel
	    // si no se reutilizan (añade dispose según tu gestión de assets).
	}




}
