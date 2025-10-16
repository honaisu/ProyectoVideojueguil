package logica;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import personajes.SkinJugador;

// TODO Creo que cuenta más como factory
public class AnimationManager {
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
	
	public static Animation<TextureRegion> createJugadorAnimation(SkinJugador skin) {
		Texture jugadorSheet = AssetsLoader.getInstancia().getSkinTexture(skin);
		int filas = 1;
		int columnas = 4;
		float duracionFrames = 0.2f;
		
		Animation<TextureRegion> animacionJugador = createAnimation(jugadorSheet, filas, columnas, duracionFrames);
		return animacionJugador;
	}
}
