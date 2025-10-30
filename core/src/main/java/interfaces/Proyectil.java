package interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Proyectil {
	public void update(float delta);
	public void draw(SpriteBatch batch);
	public boolean isDestroyed();
}
