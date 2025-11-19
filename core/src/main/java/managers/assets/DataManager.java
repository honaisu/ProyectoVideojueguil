package managers.assets;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import data.*;
import enumeradores.EWeaponType;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;

/**
 * Clase encargada de gestionar el data de las armas de nuestro proyecto.
 * Es una forma más bonita de no tener clases con números mágicos.
 */
public class DataManager {
	private Map<String, WeaponData> weapons;

	@SuppressWarnings("unchecked")
	public void load() {
		Json json = new Json();
		FileHandle file;
				
		json.setTypeName("class");
		// Define Tags para que se asocien a ciertas clases en el .json :D
		json.addClassTag("sound", EGameSound.class);
		json.addClassTag("drop", EDropType.class);
		json.addClassTag("projectileData", ProjectileData.class);
		json.addClassTag("type", EProjectileType.class);
		json.addClassTag("weapon", WeaponData.class);
		json.addClassTag("bullet", BulletData.class);
		json.addClassTag("swing", SwingData.class);
		json.addClassTag("rocket", RocketData.class);
	    json.addClassTag("flame", FlameData.class);
		
		file = Gdx.files.internal("data/weapon_data.json");
		weapons = json.fromJson(HashMap.class, WeaponData.class, file);
	}
	
	public WeaponData getWeaponData(EWeaponType weapon) {
		return weapons.get(weapon.name());
	}

	public WeaponData getWeaponData(EDropType drop) {
		return weapons.get(drop.name());
	}
}
