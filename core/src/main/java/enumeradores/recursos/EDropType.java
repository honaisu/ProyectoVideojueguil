package enumeradores.recursos;

import interfaces.ITexture;

/**
 * Enumerador que define un <i>drop</i>
 */
public enum EDropType implements ITexture {
	// Drops
	HEAVY_MACHINE_GUN("heavy.png", EGameSound.DROP_HMG),
	ROCKET_LAUNCHER("rocket.png", EGameSound.DROP_RL),
	LASER_CANNON("laser_gun.png", EGameSound.DROP_LC),
	LASER_GUN("laser_gun.png", EGameSound.DROP_LG),
	FLAMESHOT("flame.png", EGameSound.DROP_FS),
	RAYGUN("ray_gun.png", EGameSound.DROP_RG),
	SHOTGUN("shotgun.png", EGameSound.DROP_SHOTGUN),
	MELEE("melee.png", EGameSound.DROP_MELEE);
	
	private final EGameSound sound;
	private final String ruta;
	private final int width;
	private final int height;
	
	EDropType(String ruta, EGameSound pickupSound) {
		this.ruta = "textures/drops/" + ruta;
		this.sound = pickupSound;
		this.width = 22;
		this.height = 20;
	}
	
	@Override
	public String getRuta() { return ruta; }
	
	@Override
	public int getWidth() { return width; }

	@Override
	public int getHeight() { return height; }
	
	public EGameSound getGameSound() {
		return sound;
	}
}
