package personajes;

import com.badlogic.gdx.graphics.g2d.Sprite;

import hitboxes.BallHitbox;
import managers.AssetManager;

public class Enemy extends BallHitbox {
	public Enemy(float x, float y, float size) {
		super(x, y, size, 0f);
		super.setSpr(new Sprite(AssetManager.getInstancia().getAsteroideTexture()));
	}
}
