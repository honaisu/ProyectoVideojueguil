package managers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.Bullet;

// Clase encargada del manejo de las balas  
public class BulletManager {
	private final ArrayList<Bullet> bullets = new ArrayList<>();
    
    // Agregar una bala a la pantalla del juego
    public void add(Bullet b) {
        if (b != null) bullets.add(b);
    }

    // Actualiza cada bala y elimina las destruidas
    public void update() {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.update();
            if (b.isDestroyed()) {
                bullets.remove(i);
            }
        }
    }
    
    // Dibuja las balas
    public void render(SpriteBatch batch) {
        for (Bullet b : bullets) {
            b.draw(batch);
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void clear() {
        bullets.clear();
    }
    
    //public boolean agregarBala(Bullet b) { add(b); return true; }
}
