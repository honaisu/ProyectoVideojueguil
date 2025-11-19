package interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Interfaz que provee dos métodos a sobreescribir. Estos involucran la lógica
 * esperada para cualquier objeto que lo implemente, teniendo un método update
 * (de su lógica) y un método draw (para dibujarlo)
 */
public interface IRenderizable {
	// Constante que define el tamaño de HUD
	final float HUD_HEIGHT = 100f;
	/**
	 * Método default que seguirá la sucesión de pasos esperada para las clases que
	 * implementen esta interfaz.
	 * 
	 * @param delta Diferencia de tiempo
	 * @param batch El "espacio" donde dibujar
	 */
	public default void render(float delta, SpriteBatch batch) {
		this.update(delta);
		this.draw(batch);
	}

	/**
	 * Método encargado de manejar la lógica interna del objeto.
	 * 
	 * @param delta La diferencia de tiempo (sirve para las físicas y que sean
	 *              coherentes)
	 */
	public void update(float delta);

	/**
	 * Método encargado de dibujar un objeto dentro de un batch de sprites.
	 * 
	 * @param batch El batch de sprites.
	 */
	public void draw(SpriteBatch batch);
}
