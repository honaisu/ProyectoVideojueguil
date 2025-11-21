package interfaces;

import logica.MainGame;

/**
 * Interfaz encargada de poder ejecutar una acción dentro del juego.
 */
@FunctionalInterface
public interface IExecutableGame {
	public void executeGame(MainGame game);
}
