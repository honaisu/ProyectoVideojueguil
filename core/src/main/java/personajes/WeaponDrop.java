package personajes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.Weapon;
import hitboxes.Entity;

public class WeaponDrop extends Entity {
	private Weapon weaponToGive;
	private float activeTime = 0f;
	private float duration = 3f;
	
	public WeaponDrop(float x, float y, Weapon weapon) {
        // TODO: Reemplaza "getDropTexture()" con la textura real para la caja/drop
        super(x, y, new Sprite(weapon.getDropTexture())); 
        
        this.weaponToGive = weapon;
        getSpr().scale(1f);
        getSpr().setPosition(x, y);
    }
	
	@Override
    public void draw(SpriteBatch batch) {
        getSpr().draw(batch);
    }
	
    public void update(float delta) {
    	activeTime += delta;
        if (activeTime > duration) {
            destroy();
        }
    }
	
	public Weapon getWeapon() {
		return weaponToGive;
	}
}
