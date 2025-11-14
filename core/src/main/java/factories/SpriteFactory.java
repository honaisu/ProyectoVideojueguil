package factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import interfaces.IAssetRoute;
import managers.assets.AssetManager;

/**
 * Una "Simple Factory" encargada de la creación de sprites de distinto tipo.
 * <p>
 * Esta toma las texturas existentes del AssetManager, y de sus rutas proveedoras
 * para poder crear un sprite acorde.
 */
public class SpriteFactory {
	private static int DEFAULT_SIZE = 64;
	
	private SpriteFactory() {}
	
	private static Sprite create(Texture texture, int width, int height) {
		Sprite sprite = new Sprite(texture, width, height);
		return sprite;
	}
	
	public static Sprite create(IAssetRoute asset) {
		return create(asset, DEFAULT_SIZE, DEFAULT_SIZE);
	}
	
	public static Sprite create(IAssetRoute asset, int width, int height) {
		Texture texture = AssetManager.getInstancia().getTexture(asset);
		return create(texture, width, height);
	}
	
}
