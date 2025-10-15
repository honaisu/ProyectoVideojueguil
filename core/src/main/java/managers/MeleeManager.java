package managers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.Swing;

/** 
 * Clase encargada del manejo de los ataques cuerpo a cuerpo
 */
public class MeleeManager {
    private final ArrayList<Swing> swings = new ArrayList<>();
    
    // Agrega un nuevo swing
    public void add(Swing s) {
        if (s != null) swings.add(s);
    }
    
    
    public void update(float delta) {
        for (int i = swings.size() - 1; i >= 0; i--) {
            Swing s = swings.get(i);
            s.update(delta);
            if (s.isDestroyed()) {
                swings.remove(i);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Swing s : swings) {
            s.draw(batch);
        }
    }

    public ArrayList<Swing> getSwings() {
        return swings;
    }

    public void clear() {
        swings.clear();
    }
}
