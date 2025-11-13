package factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import enumeradores.recursos.EPlayerSkin;
import enumeradores.recursos.EProjectileType;
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
	
	public static Animation<TextureRegion> createPlayer(EPlayerSkin skin) {
		Texture jugadorSheet = AssetManager.getInstancia().getTexture(skin);
		int filas = 1;
		int columnas = 4;
		float duracionFrames = 0.2f;
		
		Animation<TextureRegion> animacionJugador = createAnimation(jugadorSheet, filas, columnas, duracionFrames);
		return animacionJugador;
	}
	
	public static Animation<TextureRegion> createSwing() {
		Texture ataqueSheet = AssetManager.getInstancia().getTexture(EProjectileType.SWING);
		int filas = 1;
		int columnas = 8;
		float duracionFrames = 0.25f;
		
		Animation<TextureRegion> animacionAtaque = createAnimation(ataqueSheet, filas, columnas, duracionFrames);
	    animacionAtaque.setPlayMode(PlayMode.NORMAL); 
		return animacionAtaque;
	}
}
