package managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.Bullet;
import interfaces.MomentaneumManager;

// Clase encargada del manejo de las balas  
public class BulletManager implements MomentaneumManager {
	private final List<Bullet> bullets = new ArrayList<>();
    
    // Agregar una bala a la pantalla del juego
    public void add(Bullet b) {
        if (b != null) bullets.add(b);
    }

    // Actualiza cada bala y elimina las destruidas
    @Override
    public void update(float delta) {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.update();
            if (b.isDestroyed()) {
                bullets.remove(i);
            }
        }
    }
    
    // Dibuja las balas
    @Override
    public void render(SpriteBatch batch) {
        for (Bullet b : bullets) {
            b.draw(batch);
        }
    }

    public ArrayList<Bullet> getBullets() {
        return (ArrayList<Bullet>) bullets;
    }

    public void clear() {
        bullets.clear();
    }
    
    //public boolean agregarBala(Bullet b) { add(b); return true; }
}
