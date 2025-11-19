package factories;

import armas.Weapon;
import data.WeaponData;
import enumeradores.EWeaponType;
import managers.assets.AssetManager;

public class WeaponFactory {
	public static Weapon create(EWeaponType type) {
		WeaponData data = AssetManager.getInstancia().getData(type);
		
		return new Weapon(data);
	}
}
