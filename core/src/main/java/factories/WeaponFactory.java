package factories;

import armas.factories.MeleeWeaponFactory;
import armas.factories.RangedWeaponFactory;
import data.WeaponData;
import enumeradores.EWeaponType;
import interfaces.IAttackable;
import managers.assets.AssetManager;

public class WeaponFactory {
	public static IAttackable create(EWeaponType type) {
		WeaponData data = AssetManager.getInstancia().getData(type);
		ICombatFactory factory;
		
		if (data.maxAmmo == null) {
			factory = new MeleeWeaponFactory(data);
		} else {
			factory = new RangedWeaponFactory(data);
		}
		
		return factory.createWeapon(factory.createState(), factory.createStrategy());
	}
}
