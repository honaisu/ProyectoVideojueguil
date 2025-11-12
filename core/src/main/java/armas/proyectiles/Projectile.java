package armas.proyectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.Entity;
import personajes.Player;

public abstract class Projectile extends Entity{
	
	public Projectile(float x, float y, Sprite spr, Player player) {
		super(x,y,spr);
	}
	
	public abstract void update(float delta, Player player);
	
	public  void draw(SpriteBatch batch) {
		if (!isDestroyed()) {
            super.draw(batch);
        }
	}
	
	//TODO calcularMuzzle
}