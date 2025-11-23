package interfaces;

/**
 * Interfaz que provee una ruta (path de un archivo) válida a conseguir.
 * <p>
 * Sirve, de ejemplo, para los enumeradores que proveen rutas a algún asset
 * específico, como veáse las Texturas, los Sonidos o la Música del juego.
 */
public interface IAssetPath {
	public String getRuta();
}
