package managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.Swing;
import interfaces.MomentaneumManager;

/** 
 * Clase encargada del manejo de los ataques cuerpo a cuerpo
 */
public class MeleeManager implements MomentaneumManager {
    private final List<Swing> swings = new ArrayList<>();
    
    // Agrega un nuevo swing
    public void add(Swing s) {
        if (s != null) swings.add(s);
    }
    
    @Override
    public void update(float delta) {
        for (int i = swings.size() - 1; i >= 0; i--) {
            Swing s = swings.get(i);
            s.update(delta);
            if (s.isDestroyed()) {
                swings.remove(i);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        for (Swing s : swings) {
            s.getHitbox().draw(batch);
        }
    }

    public List<Swing> getSwings() {
        return swings;
    }

    public void clear() {
        swings.clear();
    }
    
    //public void agregarSwing(Swing s) { add(s); }
}
