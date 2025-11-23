package factories;

import com.badlogic.gdx.math.MathUtils;

import armas.factories.MeleeWeaponFactory;
import armas.factories.RangedWeaponFactory;
import data.WeaponData;
import enumeradores.EWeaponType;
import interfaces.IAttackable;
import interfaces.ICombatFactory;
import managers.assets.AssetManager;

public class WeaponFactory {
	public static IAttackable create(EWeaponType type) {
		WeaponData data = AssetManager.getInstancia().getData(type);
		ICombatFactory factory;
		
		if (data.getMaxAmmo() == null) {
			factory = new MeleeWeaponFactory(data);
		} else {
			factory = new RangedWeaponFactory(data);
		}
		
		return factory.createWeapon(factory.createState(), factory.createStrategy());
	}
	
	public static IAttackable createRandomAttackable() {
		EWeaponType[] weapons = EWeaponType.values();
		int randomWeapon = MathUtils.random(weapons.length - 1);
		
		return WeaponFactory.create(weapons[randomWeapon]);
	}
}
