package interfaces;

/**
 * Interfaz dedicada a poder ofrecer métodos para los assets que son texturas.
 * <p>
 * Es una forma general de poder unirlos y definir los assets
 */
public interface ITexture extends IAssetPath {
	int DEFAULT_SIZE = 64;
	public int getWidth();
	public int getHeight();
}
