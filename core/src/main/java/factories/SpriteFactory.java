package factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import interfaces.ITexture;
import managers.assets.AssetManager;

/**
 * Una "Simple Factory" encargada de la creación de sprites de distinto tipo.
 * <p>
 * Esta toma las texturas existentes del AssetManager, y de sus rutas
 * proveedoras para poder crear un sprite acorde.
 * <p>
 * Provee un método opcional para sobreescribir el tamaño de las texturas
 * independiente de su tamaño dentro del asset. :)
 */
public class SpriteFactory {
	private SpriteFactory() {
	}

	/**
	 * Método que crea un sprite en base a una textura, tomando su tamaño y creando
	 * un sprite correspondiente a ello. :D
	 */
	private static Sprite create(Texture texture, int width, int height) {
		//Sprite sprite = new Sprite(texture, width, height); esto recorta //antiguo
		//probar esto
		Sprite sprite = new Sprite(texture); 
		sprite.setSize(width, height);
		
		return sprite;
	}

	/**
	 * Método que crea un sprite en base a una textura específica existente dentro
	 * del AssetManager. Toma valores de su tamaño que no necesariamente deben ser
	 * igual al tamaño como se presenta dentro del asset.
	 */
	public static Sprite create(ITexture asset, int width, int height) {
		Texture texture = AssetManager.getInstancia().getTexture(asset);
		return create(texture, width, height);
	}

	/**
	 * Método que crea un sprite en base a una textura específica existente. El
	 * tamaño del sprite dependerá de cómo esté hecho dentro de las implementaciones
	 */
	public static Sprite create(ITexture asset) {
		return create(asset, asset.getWidth(), asset.getHeight());
	}
	
	/**
	 * Divide el sprite de las HealthBars 96x352
	 */
	public static TextureRegion[] createHPBarFrames(ITexture texture) {
		Texture sheet = AssetManager.getInstancia().getTexture(texture);
	    TextureRegion[][] tmp = TextureRegion.split(sheet, 96, 32);

	    // El sprite sheet tiene 11 filas y 1 columna.
	    TextureRegion[] frames = new TextureRegion[11];
	    for (int i = 0; i < 11; i++) {
	        frames[i] = tmp[i][0];
	    }
	    return frames;
	}
	
	
}
