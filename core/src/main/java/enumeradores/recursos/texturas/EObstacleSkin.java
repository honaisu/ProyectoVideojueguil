package enumeradores.recursos.texturas;

import interfaces.ITexture;

public enum EObstacleSkin implements ITexture {
	//TODO poner los cosos de estas imnagenes y sus tamaños
	//SPIKE("pua.png", 48, 66),
	//POISON_PUDDLE("charcoVeneno.png", 128, 60),
	//LAVA_PUDDLE("charcoLava.png", 128, 60),
    //BLOCK("bloque.png", 64, 64),
    // Nuevos
    GRASS_SPIKE("grass_spike.png"),
    WATER_SPIKE("water_spike.png"),
    FIRE_SPIKE("fire_spike.png"),
    ICE_SPIKE("ice_spike.png"),
    
    POISON_PUDDLE("poison_puddle.png"),
    LAVA_PUDDLE("lava_puddle.png"),
    
    WATER_BOX("water_box.png"),
    IRON_BOX("iron_box.png"),
    BOX("box.png"),
    ;

    private final String ruta;
	private final int width;
	private final int height;
    
    EObstacleSkin(String ruta, int width, int height) {
		this.ruta = "obstaculos/" + ruta;
		this.width = width;
		this.height = height;
	}
    
    EObstacleSkin(String ruta) {
    	this(ruta, DEFAULT_SIZE, DEFAULT_SIZE);
    }

    @Override
	public String getRuta() { return ruta; }
	@Override
	public int getWidth() { return width; }
	@Override
	public int getHeight() { return height; }
}