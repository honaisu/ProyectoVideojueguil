package managers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.Bullet;

public class BulletManager {

	private final ArrayList<Bullet> bullets = new ArrayList<>();

    public BulletManager() { }

    public void add(Bullet b) {
        if (b != null) bullets.add(b);
    }

    public void update() {
        // Actualiza cada bala y elimina las destruidas
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.update();
            if (b.isDestroyed()) {
                bullets.remove(i);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Bullet b : bullets) {
            b.draw(batch);
        }
    }

    /**
     * Devuelve la lista interna. Úsala solo para lectura/modificación desde CollisionManager
     * con cuidado (ambos managers corren en el mismo hilo de render/update).
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Limpia todas las balas (útil en dispose o cambios de pantalla).
     */
    public void clear() {
        bullets.clear();
    }
}
