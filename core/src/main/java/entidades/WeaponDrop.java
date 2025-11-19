package entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import armas.Weapon;

public class WeaponDrop extends Entity {
	private Weapon weaponToGive;
	private float activeTime = 0f;
	private float duration = 3f;
	
	private boolean hurted = false; 
	private int hurtTime = 120;
	
	public WeaponDrop(float x, float y, Weapon weapon) {
        super(new Vector2(x, y), weapon.getDropTexture()); 
        
        this.weaponToGive = weapon;
        sprite.scale(1f);
        sprite.setPosition(x, y);
    }
	
	@Override
    public void draw(SpriteBatch batch) {
		if (hurted && hurtTime % 10 < 5)
			return; // Parpadeo
        sprite.draw(batch);
    }
	
	@Override
    public void update(float delta) {
		if (activeTime > 2f) {
			hurted= true;
			hurtTime--; // Esto es un contador de frames para el parpadeo
			if (hurtTime <= 0)
				hurted = false;
		}
    	activeTime += delta;
        if (activeTime > duration) {
            destroy();
        }
    }
	
	public Weapon getWeapon() {
		return weaponToGive;
	}
}
