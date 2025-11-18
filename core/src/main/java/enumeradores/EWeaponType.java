package enumeradores;

import enumeradores.recursos.EDropType;

public enum EWeaponType {
	ROCKET_LAUNCHER(EDropType.ROCKET_LAUNCHER),
	LASER_GUN(EDropType.LASER_GUN),
	FLAMESHOT(EDropType.FLAMESHOT),
	SHOTGUN(EDropType.SHOTGUN),
	MELEE(EDropType.MELEE),
	HEAVY_MACHINE_GUN(EDropType.HEAVY_MACHINE_GUN),
	RAYGUN(EDropType.RAYGUN);
	
	EDropType drop;
	EWeaponType(EDropType drop) {
		this.drop = drop;
	}
	
	public EDropType getDrop() {
		return drop;
	}
}
