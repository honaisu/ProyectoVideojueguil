package pantallas;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.*;
import armas.proyectiles.Bullet;
import armas.proyectiles.Swing;
import managers.AsteroidManager;
import managers.BulletManager;
import managers.CollisionManager;
import managers.MeleeManager;
import personajes.Nave4;
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

    private Nave4 nave;

    // Managers
    private AsteroidManager asteroidManager;
    private BulletManager bulletManager;
    private CollisionManager collisionManager;
    private MeleeManager meleeManager;

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
        explosionSound.setVolume(1, 0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));

        gameMusic.setLooping(true);
        gameMusic.setVolume(0.03f);
        gameMusic.play();

        // cargar imagen de la nave, 64x64
        nave = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30, 0f,
                new Texture(Gdx.files.internal("MainShip3.png")),
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                new Melee());
        nave.setVidas(vidas);
        
        //PROBAR ARMAS
        nave.setArma(new Metralleta());
        //nave.setArma(new Escopeta());
        //nave.setArma(new Melee());
        
        

        // Inicializar managers
        asteroidManager = new AsteroidManager(cantAsteroides, velXAsteroides, velYAsteroides);
        bulletManager = new BulletManager();
        collisionManager = new CollisionManager(explosionSound, 10); // 10 pts por asteroide (como tenías)
        meleeManager = new MeleeManager();

        // (Opcional) Si antes creabas asteroides manualmente, lo mantiene spawnAsteroids
        // Random r = new Random();
    }

    public void dibujaEncabezado() {
        CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
        
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
        
     // ✅ Mostrar munición (arriba derecha)
        if (nave.getArma() != null) {
            int municion = nave.getArma().getMunicion();
            int municionMax = nave.getArma().getMunicionMax();
            game.getFont().draw(batch,
                    "Munición: " + municion + " / " + municionMax,
                    Gdx.graphics.getWidth() - 300,   // X (a la derecha)
                    Gdx.graphics.getHeight() - 20);  // Y (arriba)
        }
    }

    @Override
    public void render(float delta) {
        // lógica + render por separada
        update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        // Dibujar balas
        bulletManager.render(batch);
        
        //Dibujar Melee
        meleeManager.render(batch);

        // Dibujar nave
        nave.draw(batch, this);

        // Dibujar asteroides
        asteroidManager.render(batch);

        dibujaEncabezado();
        batch.end();
    }

    private void update(float delta) {
        // actualizar nave (input y lógica interna)
        // suponiendo que Nave4 implementa su propio update() o maneja input cuando draw se llama.
        // Si Nave4 no tiene update(), entonces debes invocar su lógica de movimiento aquí si corresponde.
        // Ejemplo: nave.update(); // si existe

        // Actualizar balas y asteroides
        bulletManager.update();
        meleeManager.update(delta);
        asteroidManager.update();

        // Resolver colisiones: devuelve los puntos ganados por las balas
        int gained = collisionManager.handleCollisions(nave, bulletManager, meleeManager, asteroidManager);
        if (gained > 0) score += gained;

        // Comprueba si la nave quedó destruida (nave.estaDestruido() en tu diseño)
        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) game.setHighScore(score);
            Screen ss = new PantallaGameOver(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
            return;
        }

        // Nivel completado (si no quedan asteroides)
        if (asteroidManager.isEmpty()) {
            Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), score,
                    velXAsteroides + 3, velYAsteroides + 3, cantAsteroides + 10);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
    }

    /**
     * La nave llama a este método para añadir una bala.
     * Antes almacenabas en una lista local; ahora delegamos al BulletManager.
     */
    public boolean agregarBala(Bullet bb) {
        bulletManager.add(bb);
        return true;
    }
    
    public void agregarSwing(Swing s) {
        meleeManager.add(s);
    }
    
    @Override
    public void show() {
        gameMusic.play();
    }

    @Override
    public void resize(int width, int height) {
        // opcional: camera.viewportWidth = width; camera.viewportHeight = height; camera.update();
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        this.explosionSound.dispose();
        this.gameMusic.dispose();
        // limpiar managers (texturas/recursos manejados dentro de Ball2/Bullet si corresponde)
        asteroidManager.clear();
        bulletManager.clear();
    }

	public AsteroidManager getAsteroidManager() {
		return asteroidManager;
	}

	public BulletManager getBulletManager() {
		return bulletManager;
	}

	public CollisionManager getCollisionManager() {
		return collisionManager;
	}
	
	public MeleeManager getMeleeManager() {
		return meleeManager;
	}
   
}
