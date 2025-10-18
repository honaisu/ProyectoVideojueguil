package personajes;

public abstract class Entity {
	protected int hits;
	protected float xVel;
	protected float yVel;
	protected float rotation;
	
	public Entity() {
		this.hits = 3;
		this.xVel = 0f;
		this.yVel = 0f;
		this.rotation = 0f;
	}
}
