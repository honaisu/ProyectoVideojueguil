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

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1, 0.2f);

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("musicaDoom.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.03f);
        gameMusic.play();

        // Skin elegida por path (fallback a default)
        String path = (game.getSelectedShipPath() != null) ? game.getSelectedShipPath() : "MainShip3.png";
        Texture naveTex = new Texture(Gdx.files.internal(path));

        nave = new Jugador(
            Gdx.graphics.getWidth()/2 - 50, 30,
            naveTex,
            Gdx.audio.newSound(Gdx.files.internal("hurt.mp3")),
            new Texture(Gdx.files.internal("Rocket2.png")),
            Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))
        );
        game.setJugador(nave);
        nave.setVidas(vidas);

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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        dibujaEncabezado();

        if (!nave.estaHerido()) {
            for (int i = 0; i < balas.size(); i++) {
                Bullet b = balas.get(i);
                b.update();
                for (int j = 0; j < balls1.size(); j++) {
                    if (b.checkCollision(balls1.get(j))) {
                        explosionSound.play(0.03f);
                        balls1.remove(j);
                        balls2.remove(j);
                        j--;
                        score += 10;
                    }
                }
                if (b.isDestroyed()) {
                    balas.remove(b);
                    i--;
                }
            }
            for (Ball2 ball : balls1) ball.update();
            for (int i = 0; i < balls1.size(); i++) {
                Ball2 ball1 = balls1.get(i);
                for (int j = 0; j < balls2.size(); j++) {
                    Ball2 ball2 = balls2.get(j);
                    if (i < j) ball1.checkCollision(ball2);
                }
            }
        }

        for (Bullet b : balas) b.draw(batch);
        nave.draw(batch, this);

        for (int i = 0; i < balls1.size(); i++) {
            Ball2 b = balls1.get(i);
            b.draw(batch);
            if (nave.checkCollision(b)) {
                balls1.remove(i);
                balls2.remove(i);
                i--;
            }
        }

        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) game.setHighScore(score);
            Screen ss = new PantallaGameOver(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

        batch.end();

        if (balls1.size() == 0) {
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
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (explosionSound != null) explosionSound.dispose();
        if (gameMusic != null) gameMusic.dispose();
        // Las Textures creadas aquí deberían disponerse al cerrar nivel
        // si no se reutilizan (añade dispose según tu gestión de assets).
    }
}
