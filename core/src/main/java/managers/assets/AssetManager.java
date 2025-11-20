package managers.assets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import data.WeaponData;
import enumeradores.EWeaponType;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameMusic;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import interfaces.IAttackStrategy;
import interfaces.ITexture;


/**
 * Clase encargada de poder tener todas las texturas, sonidos y música del juego.
 * <p>
 * Esta clase es un <b>SINGLETON</b>.
 */
public class AssetManager {
	private static AssetManager instancia;
	
    private TextureManager textures = new TextureManager();
    private SoundManager sounds = new SoundManager();
    private MusicManager musics = new MusicManager();
    private DataManager data = new DataManager();
    private StrategyRegistry strategy = new StrategyRegistry();
    
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
    	textures.load();
    	sounds.load();
    	musics.load();
    	data.load();
    	strategy.load();
    }
    
	/**
     *  Método para liberar todos los recursos de la memoria
     *  Nacho: Mejor que el Garbage Collector deah
     */
    public void dispose() {
    	textures.dispose();
    	sounds.dispose();
    	musics.dispose();
    }
    
    public WeaponData getData(EWeaponType weapon) {
    	return data.getWeaponData(weapon);
    }
    
    public WeaponData getData(EDropType weapon) {
    	return data.getWeaponData(weapon);
    }
    
    public Texture getTexture(String texture) {
    	return textures.get(texture);
    }
    
    public Texture getTexture(ITexture texture) {
    	return textures.get(texture);
    }
	
	public Sound getSound(EGameSound sound) {
		return sounds.get(sound);
	}
	
	public Sound getSound(String name) {
		return sounds.get(name);
	}

	public Music getMusic(EGameMusic music) {
		return musics.get(music);
	}
	
	public IAttackStrategy getStrategy(EProjectileType type) {
		return strategy.get(type);
	}
}
