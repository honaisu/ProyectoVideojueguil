package armas.state;

import interfaces.IState;

public class RangedState implements IState {
	private final Integer maxAmmo;
	private final float fireRate;

	private float lastShot;
	private Integer ammo;

	public RangedState(float fireRate, Integer maxMunition) {
		this.fireRate = fireRate;
		this.lastShot = fireRate;
		this.maxAmmo = maxMunition;
		this.ammo = maxMunition;
	}
	
	@Override
	public void update(float delta) {
		if (lastShot < fireRate) {
			lastShot += delta;
		}
	}

	@Override
	public boolean canAttack() {
		return lastShot >= fireRate && ammo > 0;
	}

	@Override
	public void recordAttack() {
		ammo--;
		lastShot = 0;
	}

	@Override
	public boolean isValid() {
		return ammo > 0;
	}

	@Override
	public Integer getCurrentResource() {
		return ammo;
	}

	@Override
	public void setCurrentResource(Integer amount) {
		this.ammo += amount;
	}

	@Override
	public Integer getMaxResource() {
		return maxAmmo;
	}

}
