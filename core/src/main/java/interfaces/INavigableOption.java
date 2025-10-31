package interfaces;

import logica.MainGame;

/**
 * Una interfaz encargada de poder proveer métodos que identifiquen a una Opción como "navegable".
 * <p>
 * A su vez, provee un método opcional encargado de poder ejecutar alguna acción dentro del juego.
 * (Casos como, por ejemplo, cambiar la pantalla actual).
 */
public interface INavigableOption {
	public String getNombre();
	public int ordinal();
	
	default public void ejecutar(MainGame game) {}
}