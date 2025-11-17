package enumeradores.recursos;

import interfaces.IAssetPath;

/**
 * Enumerador encargado de todos los sonidos provenientes del juego.
 * <p>
 * Cada sonido va a estar localizado en la carpeta <i>"assets/audios/sfx"</i> 
 * del proyecto (<b>Requerimiento</b>).
 */
public enum EGameSound implements IAssetPath {
	// Drops
	DROP_HMG("drops/heavy_machine_gun.mp3"),
	DROP_RL("drops/rocket_launcher.mp3"),
	DROP_LC("drops/laser_cannon.mp3"),
	DROP_SHOTGUN("drops/shotgun.mp3"),
	DROP_FS("drops/flame_shot.mp3"),
	DROP_LG("drops/laser_gun.mp3"),
	DROP_MELEE("drops/melee.mp3"),
	DROP_RG("drops/ray_gun.mp3"),
	// Weapons
	SHOOT_LASER_CANNON("weapons/laser_cannon.mp3"),
	SHOOT_LASER("weapons/laser.mp3"),
	SHOOT("weapons/shoot.mp3"),
	// Enemies
	EXPLOSION("enemies/explosion.ogg"),
	// Player
	DEATH("player/death.mp3"),
	HURT("player/hurt.mp3");
	
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
