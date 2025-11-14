package factories;

import com.badlogic.gdx.utils.Disposable;

/**
 * <i>Factory Abstracta</i> que se encarga de crear un recurso.
 * <p>
 * Esto se basa en una interfaz implementada por GDX (Disposable),
 * disponible para "recursos" que pueden ser <i>borrados</i> después.
 */
public interface ResourceFactory {
	Disposable create(String ruta);
}
