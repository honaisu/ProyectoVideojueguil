package interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Interfaz que provee dos métodos a sobreescribir. Estos involucran la lógica
 * esperada para cualquier objeto que lo implemente, teniendo un método update
 * (de su lógica) y un método draw (para dibujarlo)
 */
public interface IRenderizable {
	// Constante que define el tamaño de HUD
	
	float HUD_HEIGHT = 100f;
	/**
	 * Método encargado de manejar la lógica interna del objeto.
	 * 
	 * @param delta La diferencia de tiempo (sirve para las físicas y que sean
	 *              coherentes)
	 */
	void update(float delta);

	/**
	 * Método encargado de dibujar un objeto dentro de un batch de sprites.
	 * 
	 * @param batch El batch de sprites.
	 */
	void draw(SpriteBatch batch);
}
