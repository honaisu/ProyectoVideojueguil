package enumeradores.recursos;

import interfaces.IAssetRoute;

public enum EProjectileType implements IAssetRoute {
	SWING("semicirculo.png"),
	LASER_GUN("laserGun.png"),
	LASER_CANNON("laserCannon.png"),
	BULLET_TEMPLATE("Bala.png");
	
	private final String ruta;
	
	EProjectileType(String ruta) {
		this.ruta = "textures/projectiles/" + ruta; 
	}

	@Override
	public String getRuta() {
		return ruta;
	}
}
