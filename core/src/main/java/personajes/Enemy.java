package personajes;

import com.badlogic.gdx.graphics.g2d.Sprite;

import hitboxes.Hitbox;
import managers.AssetManager;

public class Enemy extends Hitbox {

	private float rareDrop;
	private int healthPoints;
	private float damage;
	
	public Enemy(float x, float y, float rareDrop){//, int healthPoints, int damage) {
		super(x, y, new Sprite(AssetManager.getInstancia().getAsteroideTexture()));
		this.rareDrop = rareDrop;
		//this.healthPoints = healthPoints;
		//this.damage = damage;
	}
}
