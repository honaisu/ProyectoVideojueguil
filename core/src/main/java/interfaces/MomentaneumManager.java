package interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Interfaz que provee una base para los Managers.
 * <p>
 * Esta se encarga de hacer que cada Manager pueda tener un método update y un método render propio.
 */
public interface MomentaneumManager {
	public void render(SpriteBatch batch);
	public void update(float delta);
}