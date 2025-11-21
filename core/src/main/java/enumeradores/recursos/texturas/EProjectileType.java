package enumeradores.recursos.texturas;

import interfaces.ITexture;

public enum EProjectileType implements ITexture {
	// ESPECIALES //
	SWING_ANIMATION("swing_animation.png", 96, 64),
	LASER_CANNON("laser_cannon.png", 96, 32),
	EXPLOSION("explosion.png", 71, 100),
	LASER_GUN("laser_gun.png", 96, 32),
	SWING("swing.png", 96, 64),
	// BALAS //
	// Los que usan un sprite "default"
	BULLET_TEMPLATE("bullet_template.png"),
	HOLLOWPOINT("hollowpoint.png"),
	ROUNDNOSE("roundnose.png"),
	REDWIRE("redwire.png"),
	RAYGUN("raypulse.png"),
	ROCKET("rocket.png"),
	FLAME("flame.png"),
	;
	
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
