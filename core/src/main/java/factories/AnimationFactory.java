package factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import enumeradores.recursos.EPlayerSkin;
import enumeradores.recursos.EProjectileType;
import interfaces.ITexture;
import managers.assets.AssetManager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationFactory {
	private AnimationFactory() {}
	
	/**
	 * Método general para crear animaciones en base a una textura ("sprite sheet")
	 * sus filas, columnas y la duración esperada de frames :D
	 */
	private static Animation<TextureRegion> createAnimation(Texture textura, int filas, int columnas, float duracionFrame) {
		// Parte encargada de la textura para la animación
    	// Toma de forma temporal la textura y la divide en cuadrantes
		TextureRegion[][] tmp = TextureRegion.split(textura, 
    			textura.getWidth() / columnas, 
    			textura.getHeight() / filas);
    	
    	// Crea un Texture Region con el tamaño del "sheet" de animación
		// Estos serán los frames finales :D
    	TextureRegion[] frames = new TextureRegion[filas * columnas];
    	
    	int index = 0;
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		
        Animation<TextureRegion> animacion = new Animation<>(duracionFrame, frames);
        animacion.setPlayMode(PlayMode.LOOP);
		return animacion;
	}
	
	public static Animation<TextureRegion> create(ITexture asset, int filas, int columnas, float frameDuration) {
		Texture sheet = AssetManager.getInstancia().getTexture(asset);
		Animation<TextureRegion> animacionJugador = createAnimation(sheet, filas, columnas, frameDuration);
		return animacionJugador;
	}
	
	public static Animation<TextureRegion> createPlayer(EPlayerSkin skin) {
		return create(skin, 1, 4, 0.25f);
	}
	
	public static Animation<TextureRegion> createSwing() {
		Animation<TextureRegion> animacionAtaque = create(EProjectileType.SWING_ANIMATION, 1, 8, 0.05f);
	    animacionAtaque.setPlayMode(PlayMode.NORMAL); 
		return animacionAtaque;
	}
	
	public static Animation<TextureRegion> createExplosion() {
		Animation<TextureRegion> animation = create(EProjectileType.EXPLOSION, 1, 17, 0.05f);
		animation.setPlayMode(PlayMode.NORMAL);
		return animation;
	}
}
