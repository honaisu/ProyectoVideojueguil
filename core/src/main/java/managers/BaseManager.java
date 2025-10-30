package managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import interfaces.Proyectil;

public abstract class BaseManager {
	private final List<Proyectil> proyectiles = new ArrayList<>();
	
	public void add(Proyectil proyectil) {
		if (proyectil == null) return;
		proyectiles.add(proyectil);
	}
	
	public void update(float delta) {
		for (Proyectil proyectil : proyectiles) {
			proyectil.update(delta);
			if (proyectil.isDestroyed()) {
				proyectiles.remove(proyectil);
			}
		}
    }
	
	public void render(SpriteBatch batch) {
        for (Proyectil proyectil : proyectiles) {
            proyectil.draw(batch);
        }
    }
	
	public void clear() {
		proyectiles.clear();
	}
	
	public boolean isEmpty() {
		return proyectiles.isEmpty();
	}
}
