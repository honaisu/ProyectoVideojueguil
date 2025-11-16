package managers.assets;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import enumeradores.recursos.EBackgroundType;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EEnemyType;
import enumeradores.recursos.EPlayerSkin;
import enumeradores.recursos.EProjectileType;
import enumeradores.recursos.EObstacleSkin;
import interfaces.ITexture;

public class TextureManager implements Disposable {
	private Map<ITexture, Texture> textures = new HashMap<>();
    
    public TextureManager() {}
    
    public void load() {
    	// Carga los enumeradores
    	loadFromEnum(EProjectileType.values());
    	loadFromEnum(EBackgroundType.values());
    	loadFromEnum(EPlayerSkin.values());
    	loadFromEnum(EEnemyType.values());
    	loadFromEnum(EDropType.values());
    	loadFromEnum(EObstacleSkin.values());
    }
    
    /**
     * Método encargado de poder cargar las texturas dentro del mapa enum que se quiera.
     * 
     * @param elements Los elementos del enumerador
     */
    private void loadFromEnum(ITexture[] elements) {
    	// ITera sobre todos los valores del IASSETROUTE
    	for (ITexture element : elements) {
    		// Crea una textura en base al método que sabe que implementará (getRuta)
    		Texture textura = new Texture(Gdx.files.internal(element.getRuta()));
    		// Se guarda en el mapa de enumeradores
    		textures.put(element, textura);
    	}
    }
    
    public Texture get(ITexture asset) {
    	return textures.get(asset);
    }

	@Override
	public void dispose() {
		for (Texture texture : textures.values()) {
			texture.dispose();
		}
		textures.clear();
	}
}
