package personajes;

import com.badlogic.gdx.graphics.g2d.Sprite;

import hitboxes.Hitbox;

public abstract class Entity extends Hitbox {
	protected int hits;
	protected float xVel;
	protected float yVel;
	protected float rotation;
	
	public Entity(float x, float y) {
		super(x, y);
		this.hits = 3;
		this.xVel = 0f;
		this.yVel = 0f;
		this.rotation = 0f;
	}
	
	public Entity(float x, float y, Sprite sprite) {
		super(x, y, sprite);
		this.hits = 3;
		this.xVel = 0f;
		this.yVel = 0f;
		this.rotation = 0f;
	}
}
