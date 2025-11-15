package enumeradores.recursos;

import interfaces.ITexture;

public enum EBackgroundType implements ITexture {
	ONE("level_one.png", 887, 888),
	TWO("level_two.png", 1083, 675),
	THREE("fondoNivelTres.png", 341, 341);
	
	
	EBackgroundType(String ruta, int width, int height) {
		this.ruta = "backgrounds/" + ruta;
		this.width = width;
		this.height = height;
	}

	
	private final String ruta;
	private final int width;
	private final int height;

	@Override
	public String getRuta() { return ruta; }
	
	@Override
	public int getWidth() { return width; }

	@Override
	public int getHeight() { return height; }
}
