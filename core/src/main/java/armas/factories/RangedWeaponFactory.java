package armas.factories;

import armas.Weapon;
import armas.state.RangedState;
import data.WeaponData;
import factories.ICombatFactory;
import interfaces.IAttackStrategy;
import interfaces.IAttackable;
import interfaces.IState;
import managers.assets.AssetManager;

public class RangedWeaponFactory implements ICombatFactory {
	private final WeaponData data;

    public RangedWeaponFactory(WeaponData data) {
        this.data = data;
    }

    @Override
    public IState createState() {
        return new RangedState(data.fireRate, data.maxAmmo);
    }

	@Override
	public IAttackable createWeapon(IState state, IAttackStrategy strategy) {
		return new Weapon(data, state, strategy);
	}

	@Override
    public IAttackStrategy createStrategy() {
        return AssetManager.getInstancia().getStrategy(data.projectileData.type);
    }
}
