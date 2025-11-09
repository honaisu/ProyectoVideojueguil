package personajes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.Weapon;
import hitboxes.Hitbox;

public class WeaponDrop extends Hitbox {
	private Weapon weaponToGive;
	
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
	
	public Weapon getWeapon() {
        return weaponToGive;
    }
	
	/**
     * El update podría usarse para animaciones, o para que el drop
     * desaparezca después de un tiempo. Por ahora lo dejamos vacío.
     */
    public void update(float delta) {
        // Opcional: animar, rotar, etc.
    }
}
