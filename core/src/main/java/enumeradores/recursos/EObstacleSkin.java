package enumeradores.recursos;

import interfaces.ITexture;

public enum EObstacleSkin implements ITexture {
	//TODO poner los cosos de estas imnagenes y sus tamaños
	SPIKE("obstaculos/pua.png", 48, 66),
	POISON_PUDDLE("obstaculos/charcoVeneno.png", 128, 60),
	LAVA_PUDDLE("obstaculos/charcoLava.png", 128, 60),
    BLOCK("obstaculos/bloque.png", 64, 64);

    private final String ruta;
	private final int width;
	private final int height;
    
    EObstacleSkin(String ruta, int width, int height) {
		this.ruta = ruta;
		this.width = width;
		this.height = height;
	}

    @Override
	public String getRuta() { return ruta; }
	@Override
	public int getWidth() { return width; }
	@Override
	public int getHeight() { return height; }
}