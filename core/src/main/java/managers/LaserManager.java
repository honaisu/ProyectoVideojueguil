package managers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.LaserBeam;

public class LaserManager {
private final ArrayList<LaserBeam> lasers = new ArrayList<>();
    
    // Agrega un nuevo swing
    public void add(LaserBeam s) {
        if (s != null) lasers.add(s);
    }
    
    public void update(float delta) {
        for (int i = lasers.size() - 1; i >= 0; i--) {
        	LaserBeam s = lasers.get(i);
            s.update(delta);
            if (s.isDestroyed()) {
                lasers.remove(i);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (LaserBeam s : lasers) {
            s.draw(batch);
        }
    }

    public ArrayList<LaserBeam> getLasers() {
        return lasers;
    }

    public void clear() {
        lasers.clear();
    }

}
