package enumeradores.recursos;

import interfaces.IAssetRoute;

public enum EDropType implements IAssetRoute {
	// Drops
	HEAVY_MACHINE_GUN("HeavyDrop.png"),
	SHOTGUN("ShotgunDrop.png"),
	ROCKET_LAUNCHER("RocketDrop.png"),
	MELEE("MeleeDrop.png"),
	LASER_CANNON("LaserGunDrop.png"),
	LASER_GUN("LaserGunDrop.png");

	private final String ruta;
	
	EDropType(String ruta) {
		this.ruta = "textures/drops/" + ruta;
	}

	@Override
	public String getRuta() {
		return ruta;
	}
}
