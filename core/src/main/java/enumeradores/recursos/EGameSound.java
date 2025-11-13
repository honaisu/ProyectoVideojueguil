package enumeradores.recursos;

import interfaces.IAssetRoute;

/**
 * Enumerador encargado de todos los sonidos provenientes del juego.
 * <p>
 * Cada sonido va a estar localizado en la carpeta <i>"assets/audios/sfx"</i> 
 * del proyecto (<b>Requerimiento</b>).
 */
public enum EGameSound implements IAssetRoute {
	// Drops
	DROP_HMG("drops/HeavyMachineGun.mp3"),
	DROP_SHOTGUN("drops/Shotgun.mp3"),
	DROP_RL("drops/RocketLauncher.mp3"),
	DROP_MELEE("drops/Melee.mp3"),
	DROP_LC("drops/LaserCannon.mp3"),
	DROP_LG("drops/LaserGun.mp3"),
	// Weapons
	SHOOT("weapons/popSound.mp3"),
	SHOOT_LASER_CANNON("weapons/canonLaserSound.mp3"),
	SHOOT_LASER("weapons/laserSound.mp3"),
	// Enemies
	EXPLOSION("enemies/explosionSound.ogg"),
	// Player
	HURT("player/danoSound.mp3"),
	DEATH("player/muerteSound.mp3");
	
	private String ruta;
	
	EGameSound(String ruta) {
		// TODOS los sonidos vienen de acá.
		this.ruta = "audios/sfx/" + ruta;
	}

	@Override
	public String getRuta() {
		return ruta;
	}
}
