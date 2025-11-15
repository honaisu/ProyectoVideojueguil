package enumeradores.recursos;

import interfaces.ITexture;

public enum EProjectileType implements ITexture {
	BULLET_TEMPLATE("bullet_template.png", 32, 32),
	LASER_CANNON("laser_cannon.png", 96, 32),
	HOLLOWPOINT("hollowpoint.png", 32, 32),
	LASER_GUN("laser_gun.png", 96, 32),
	ROUNDNOSE("roundnose.png", 32, 32),
	REDWIRE("redwire.png", 32, 32),
	SWING("swing.png", 96, 64),
	FLAME("flame.png", 32, 32),
	RAYGUN("raypulse.png", 32, 32),
	ROCKET("rocket.png", 64, 64);
	
	EProjectileType(String ruta, int width, int height) {
		this.ruta = "textures/projectiles/" + ruta; 
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
