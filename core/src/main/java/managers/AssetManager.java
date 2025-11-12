package managers;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import enumeradores.ESkinJugador;

/**
 * Clase encargada de poder tener todas las texturas, sonidos y música del juego.
 * <p>
 * Esta clase es un <b>SINGLETON</b>.
 */
public class AssetManager {
	private static AssetManager instancia;
	// Jugador
	// TODO Evaluar para ver si es como SkinManager(?) o que sea parte de un "TextureManager"
    private Map<ESkinJugador, Texture> skinJugadorTextures;
    
    //armas
    private Texture HMTexture;
    private Texture STexture;
    private Texture RLTexture;
    private Texture MTexture;
    private Texture LCTexture;
    private Texture LGTexture;
    
    
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
    
    //drops
    private Sound HMSound;
    private Sound SSound;
    private Sound RLSound;
    private Sound MSound;
    private Sound LCSound;
    private Sound LGSound;
    
    //Entidades
    private Sound explosionSound;
    private Sound hurtSound;
    private Sound muerteSound;
    
    //disparos
    private Sound disparoSound;
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
        
        // TEXTURAS
        enemigoTexture = new Texture(Gdx.files.internal("textures/enemies/Mono.png"));
        
        //Drops
        HMTexture = new Texture(Gdx.files.internal("textures/drops/HeavyDrop.png"));
        STexture = new Texture(Gdx.files.internal("textures/drops/ShotgunDrop.png"));
        RLTexture = new Texture(Gdx.files.internal("textures/drops/RocketDrop.png"));
        MTexture = new Texture(Gdx.files.internal("textures/drops/MeleeDrop.png"));
        //LCTexture = new Texture(Gdx.files.internal("textures/drops/LaserCannonDrop.png"));
        LGTexture = new Texture(Gdx.files.internal("textures/drops/LaserGunDrop.png"));
        
        //swingHitboxTexture = new Texture(Gdx.files.internal("AtaqueMelee.png"));
        balaTexture = new Texture(Gdx.files.internal("textures/projectiles/Bala.png"));
        swingHitboxTexture = new Texture(Gdx.files.internal("textures/projectiles/semicirculo.png"));
        laserContTexture = new Texture(Gdx.files.internal("textures/projectiles/laserCont.png"));
        laserGunTexture = new Texture(Gdx.files.internal("textures/projectiles/laserGun.png"));
        
        // SONIDOS
        //Drops
        HMSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/drops/HeavyMachineGun.mp3"));
        SSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/drops/Shotgun.mp3"));
        RLSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/drops/RocketLauncher.mp3"));
        MSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/drops/Melee.mp3"));
        LCSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/drops/LaserCannon.mp3"));
        LGSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/drops/LaserGun.mp3"));
        
        //Enemigos
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/enemies/explosionSound.ogg"));
        
        //Jugador
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/player/danoSound.mp3"));
        muerteSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/player/muerteSound.mp3"));
        
        //Disparos
        disparoSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/weapons/popSound.mp3"));
        laserContSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/weapons/laserSound.mp3"));
        laserGunSound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx/weapons/canonLaserSound.mp3"));
        
        // musica
        musicaTutorial =  Gdx.audio.newMusic(Gdx.files.internal("audios/music/tutorial.mp3"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("audios/music/musicaDoom.mp3"));
        gameMusic.setLooping(true);
    }
    
    private void loadTexturesJugador() {
    	skinJugadorTextures = new EnumMap<>(ESkinJugador.class);
        // Se itera sobre todos los valores de Skin
        for (ESkinJugador skin : ESkinJugador.values()) {
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
	public Texture getSkinTexture(ESkinJugador skin) {
		return skinJugadorTextures.get(skin);
	}

	//Armas
	public Texture getHMTexture() { return HMTexture; }
	public Texture getSTexture() { return STexture;	}
	public Texture getRLTexture() {	return RLTexture; }
	public Texture getMTexture() { return MTexture;	}
	public Texture getLCTexture() {	return LCTexture; }
	public Texture getLGTexture() {	return LGTexture; }

	// Pnemigos
	public Texture getEnemyTexture() { return enemigoTexture; }
	
	// Proyectiles
	public Texture getBalaTexture() { return balaTexture; }
	public Texture getSwingHitboxTexture() { return swingHitboxTexture; }
	public Texture getLaserContTexture() { return laserContTexture;	}
	public Texture getLaserGunTexture() { return laserGunTexture; }
	
	// SONIDOS
	
	//Drops
	public Sound getHMSound() {	return HMSound;	}
	public Sound getSSound() { return SSound; }
	public Sound getRLSound() {	return RLSound;	}
	public Sound getMSound() { return MSound; }
	public Sound getLCSound() {	return LCSound;	}
	public Sound getLGSound() {	return LGSound;	}

	//Entidades
	public Sound getExplosionSound() { return explosionSound; }
	public Sound getHurtSound() 	{ return hurtSound; }
	public Sound getMuerteSound() { return muerteSound;}

	//Disparos
	public Sound getDisparoSound() 	{ return disparoSound; }
	public Sound getLaserContSound() 	{ return laserContSound; }
	public Sound getLaserGunSound() { return laserGunSound; }

	// Música
	public Music getTutorialSound() {return musicaTutorial;}
	public Music getGameMusic() { return gameMusic; }
}
