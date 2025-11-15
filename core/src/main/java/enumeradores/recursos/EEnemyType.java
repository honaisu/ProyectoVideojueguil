package enumeradores.recursos;

import interfaces.ITexture;

public enum EEnemyType implements ITexture {
	GENERIC("generic.png"),
	POINTED("pointed.png"),
	WATER("water.png");
	
	EEnemyType(String ruta) {
		this.ruta = "textures/enemies/" + ruta;
	}

	private final String ruta;
	private final int width = DEFAULT_SIZE;
	private final int height = DEFAULT_SIZE;

	@Override
	public String getRuta() { return ruta; }
	
	@Override
	public int getWidth() { return width; }

	@Override
	public int getHeight() { return height; }
}
