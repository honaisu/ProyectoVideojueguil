package enumeradores.recursos;

import interfaces.ITexture;

public enum EProjectileType implements ITexture {
	// ESPECIALES
	LASER_CANNON("laser_cannon.png", 96, 32),
	LASER_GUN("laser_gun.png", 96, 32),
	SWING("swing.png", 96, 64),
	// Los que usan un sprite "default"
	// BALAS
	BULLET_TEMPLATE("bullet_template.png"),
	HOLLOWPOINT("hollowpoint.png"),
	ROCKET("rocket.png", 64, 64), //TODO perdon nacho hice es sprite 64x64 y con el DEFAULT_SIZE lo deja como 1/4 de la textura XD
	ROUNDNOSE("roundnose.png"),
	REDWIRE("redwire.png"),
	RAYGUN("raypulse.png"),
	FLAME("flame.png");
	
	EProjectileType(String ruta, int width, int height) {
		this.ruta = "textures/projectiles/" + ruta; 
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Inicia las balas con textura 32x32
	 */
	EProjectileType(String ruta) {
		this(ruta, DEFAULT_SIZE/2, DEFAULT_SIZE/2);
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
