package interfaces;

import managers.ScreenManager;

/**
 * Interfaz encarga de permitir a una clase ejecutar una acción en el manager de pantalla.
 * <p>
 * Es utilizado, de ejemplo, para poder mover las pantallas del menú principal.
 */
public interface IScreenAction {
	public void ejecutar(ScreenManager pantallaManager);
}
