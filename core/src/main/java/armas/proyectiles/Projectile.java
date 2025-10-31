package armas.proyectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import hitboxes.Hitbox;

public abstract class Projectile {
	private Hitbox hitbox;
	private boolean destroyed;
	
	public Projectile(Hitbox hitbox) {
		this.hitbox = hitbox;
		this.destroyed = false;
	}
	
	public abstract void update(float delta, Rectangle r, float rotation);
	public abstract void draw(SpriteBatch batch);
	
	public void destroy() {
        destroyed = true;
    }
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
}
