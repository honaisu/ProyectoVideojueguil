package armas.factories;

import armas.Weapon;
import armas.state.MeleeState;
import data.WeaponData;
import interfaces.IAttackStrategy;
import interfaces.IAttackable;
import interfaces.ICombatFactory;
import interfaces.IState;
import managers.assets.AssetManager;

public class MeleeWeaponFactory implements ICombatFactory {
	private final WeaponData data;

    public MeleeWeaponFactory(WeaponData data) {
        this.data = data;
    }

	@Override
	public IState createState() {
		return new MeleeState(data.getFireRate());
	}

	@Override
	public IAttackable createWeapon(IState state, IAttackStrategy strategy) {
		return new Weapon(data, state, strategy);
	}

	@Override
    public IAttackStrategy createStrategy() {
        return AssetManager.getInstancia().getStrategy(data.getProjectileData().getType());
    }
}
