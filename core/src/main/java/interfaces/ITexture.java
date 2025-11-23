package interfaces;

/**
 * Interfaz dedicada a poder ofrecer métodos para los assets que son texturas.
 * Esto incluye formas de conseguir su tamaño (largo y ancho), junto con una
 * constante que define su tamaño "default".
 * <p>
 * Es una forma general de poder unirlos y definir los assets, donde depende
 * también de la ruta provista (ya que define la textura).
 */
public interface ITexture extends IAssetPath {
	int DEFAULT_SIZE = 64;
	public int getWidth();
	public int getHeight();
}
