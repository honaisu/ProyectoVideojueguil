package armas.state;

import interfaces.IState;

public class MeleeState implements IState {
	private float cooldown;
    private float timer;

    public MeleeState(float cooldown) {
        this.cooldown = cooldown;
        this.timer = cooldown;
    }

	@Override
	public void update(float delta) {
		if (timer < cooldown) 
			timer += delta;
	}

	@Override
	public boolean canAttack() {
		return timer >= cooldown;
	}

	@Override
	public void recordAttack() {
		timer = 0;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public Integer getCurrentResource() {
		return null;
	}

	@Override
	public Integer getMaxResource() {
		return null;
	}
}
