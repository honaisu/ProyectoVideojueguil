package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Clase encargada de poder tener todas las texturas, sonidos y música del juego.
 * TODO por seguridad cambiar los parámetros a private en vez de public
 */
public class AssetsLoader {
	// Texturas
    public Texture naveTexture;
    public Texture balaTexture;
    public Texture asteroideTexture;
    
    // Sonidos y Música
    public Sound explosionSound;
    public Sound hurtSound;
    public Sound disparoSound;
    public Music gameMusic;

    // Método para cargar todo
    public void load() {
        naveTexture = new Texture(Gdx.files.internal("referencia.png"));
        balaTexture = new Texture(Gdx.files.internal("Bala.png"));
        asteroideTexture = new Texture(Gdx.files.internal("Mono.png"));

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        disparoSound = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
        
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.03f);
    }
    
    // Método para liberar todos los recursos de la memoria
    public void dispose() {
        naveTexture.dispose();
        balaTexture.dispose();
        asteroideTexture.dispose();
        explosionSound.dispose();
        hurtSound.dispose();
        disparoSound.dispose();
        gameMusic.dispose();
    }
}
