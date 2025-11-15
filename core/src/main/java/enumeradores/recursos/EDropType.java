package enumeradores.recursos;

import interfaces.ITexture;

public enum EDropType implements ITexture {
	// Drops
	HEAVY_MACHINE_GUN("heavy.png"),
	SHOTGUN("shotgun.png"),
	ROCKET_LAUNCHER("rocket.png"),
	MELEE("melee.png"),
	LASER_CANNON("laser_gun.png"),
	LASER_GUN("laser_gun.png");
	
	EDropType(String ruta) {
		this.ruta = "textures/drops/" + ruta;
		this.width = 22;
		this.height = 20;
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
