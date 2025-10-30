package managers;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import enumeradores.SkinJugador;

/**
 * Clase encargada de poder tener todas las texturas, sonidos y música del juego.
 * <p>
 * Esta clase es un <b>SINGLETON</b>.
 */
public class AssetManager {
	private static AssetManager instancia;
	// Jugador
	// TODO Evaluar para ver si es como SkinManager(?) o que sea parte de un "TextureManager"
    private Map<SkinJugador, Texture> skinJugadorTextures;
    
    // TODO Considerar crear TextureManager? (Extensible?)
    // Enemigos
    private Texture enemigoTexture;
    // Proyectiles
    private Texture balaTexture;
    private Texture swingHitboxTexture;
    private Texture laserContTexture;
    private Texture laserGunTexture;
    
    // TODO Considerar crear SoundMusicManager? (Extensible?)
    // Sonidos y Música
    private Sound explosionSound;
    private Sound hurtSound;
    private Sound disparoSound;
    private Sound muerteSound;
    private Sound laserGunSound;
    private Sound laserContSound;

    private Music gameMusic;
    private Music musicaTutorial;

    
    private AssetManager() {}
    
    /**
     * Método que consigue AL AssetsLoader
     * @return AssetsLoader
     */
    public static AssetManager getInstancia() {
        if (instancia == null) instancia = new AssetManager();
        return instancia;
    }

    /**
     *  Método para cargar todo
     */
    public void load() {
        this.loadTexturesJugador();
        
        // texturas
        balaTexture = new Texture(Gdx.files.internal("Bala.png"));
        enemigoTexture = new Texture(Gdx.files.internal("Mono.png"));
        swingHitboxTexture = new Texture(Gdx.files.internal("AtaqueMelee.png"));

        swingHitboxTexture = new Texture(Gdx.files.internal("semicirculo.png"));
        laserContTexture = new Texture(Gdx.files.internal("laserCont.png"));
        laserGunTexture = new Texture(Gdx.files.internal("laserGun.png"));
        
        // sonidos
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("audios/explosionSound.ogg"));
        disparoSound = Gdx.audio.newSound(Gdx.files.internal("audios/popSound.mp3"));
        laserContSound = Gdx.audio.newSound(Gdx.files.internal("audios/laserSound.mp3"));
        laserGunSound = Gdx.audio.newSound(Gdx.files.internal("audios/canonLaserSound.mp3"));
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("audios/danoSound.mp3"));
        muerteSound = Gdx.audio.newSound(Gdx.files.internal("audios/muerteSound.mp3"));
        musicaTutorial =  Gdx.audio.newMusic(Gdx.files.internal("audios/tutorial.mp3"));
        
        // musica
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("audios/musicaDoom.mp3"));
        gameMusic.setLooping(true);
    }
    
    private void loadTexturesJugador() {
    	skinJugadorTextures = new EnumMap<>(SkinJugador.class);
        // Se itera sobre todos los valores de Skin
        for (SkinJugador skin : SkinJugador.values()) {
            // Se crea la textura en base a la ruta de la skin :3
            Texture textura = new Texture(Gdx.files.internal(skin.getRuta()));
            // Se guarda en el mapa de enums
            skinJugadorTextures.put(skin, textura);
        }
    }
    
	/**
     *  Método para liberar todos los recursos de la memoria
     */
    public void dispose() {
        balaTexture.dispose();
        enemigoTexture.dispose();
        explosionSound.dispose();
        hurtSound.dispose();
        disparoSound.dispose();
        gameMusic.dispose();
    }

    // Jugador
	public Texture getSkinTexture(SkinJugador skin) {
		return skinJugadorTextures.get(skin);
	}
	
	// Pnemigos
	public Texture getAsteroideTexture() { return enemigoTexture; }
	
	// Proyectiles
	public Texture getBalaTexture() { return balaTexture; }
	public Texture getSwingHitboxTexture() { return swingHitboxTexture; }
	public Texture getLaserContTexture() { return laserContTexture;	}
	public Texture getLaserGunTexture() { return laserGunTexture; }
	
	// Sonidos
	public Sound getExplosionSound() { return explosionSound; }
	public Sound getHurtSound() 	{ return hurtSound; }
	public Sound getDisparoSound() 	{ return disparoSound; }
	public Sound getLaserContSound() 	{ return laserContSound; }
	public Sound getLaserGunSound() { return laserGunSound; }
	public Sound getMuerteSound() { return muerteSound;}
	public Music getTutorialSound() {return musicaTutorial;}

	// Música
	public Music getGameMusic() { return gameMusic; }
}
