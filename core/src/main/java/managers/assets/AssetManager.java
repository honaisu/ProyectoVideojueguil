package managers.assets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import enumeradores.recursos.EGameMusic;
import enumeradores.recursos.EGameSound;
import interfaces.IAssetRoute;

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
    
    public Texture getTexture(IAssetRoute texture) {
    	return textures.get(texture);
    }
	
	public Sound getSound(EGameSound sound) {
		return sounds.get(sound);
	}

	public Music getMusic(EGameMusic music) {
		return musics.get(music);
	}
}
