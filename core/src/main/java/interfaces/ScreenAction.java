package interfaces;

import managers.ScreenManager;

/**
 * Interfaz encarga de permitir a una clase ejecutar una acción en el manager de pantalla.
 * <p>
 * Es utilizado para poder mover el menú principal.
 */
public interface ScreenAction {
	public void ejecutar(ScreenManager pantallaManager);
}
