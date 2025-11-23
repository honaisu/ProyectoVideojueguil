package interfaces;

import logica.MainGame;

/**
 * Una interfaz encargada de poder proveer métodos que identifiquen a una Opción como "navegable".
 * <p>
 * A su vez, provee un método <b>opcional</b> encargado de poder ejecutar alguna acción dentro del juego.
 * (Casos como, por ejemplo, cambiar la pantalla actual).
 */
public interface INavigableOption extends IExecutableGame {
	public String getName();
	public int ordinal();
	@Override
	public default void executeGame(MainGame game) {}
}