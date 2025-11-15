package managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entidades.Player;
import entidades.proyectiles.Projectile;

public class ProjectileManager {
	private final List<Projectile> proyectiles = new ArrayList<>();
	private final List<Projectile> toAdd = new ArrayList<>();
	
	public void add(Projectile proyectil) {
		if (proyectil == null) return;

		toAdd.add(proyectil);
	}
	
	public boolean remove(Projectile proyectil) {
		return proyectiles.remove(proyectil);
	}
	
	public Projectile removeActual() {
		return proyectiles.remove(0);
	}
	
	public Projectile getActual() {
		if (proyectiles.isEmpty()) return null;
		return proyectiles.get(0);
	}
	
	public void update(float delta, Player player) {
		if (!toAdd.isEmpty()) {
			proyectiles.addAll(toAdd);
			toAdd.clear();
		}
		// Lo utiliza por seguridad. No sabía que existia una clase así de buena :o
		Iterator<Projectile> iterator = proyectiles.iterator();

	    while (iterator.hasNext()) {
	        Projectile proyectil = iterator.next();
	        if (proyectil == null) {
	        	iterator.remove();
	        	continue;
	        }
	        proyectil.update(delta, player);
	        if (proyectil.isDestroyed()) {
	            iterator.remove();
	        }
	    }
    }
	
	public void draw(SpriteBatch batch) {
        for (Projectile proyectil : proyectiles) {
            proyectil.draw(batch);
        }
    }
	
	public void clear() {
		proyectiles.clear();
	}
	
	public boolean isEmpty() {
		return proyectiles.isEmpty();
	}
	
	public List<Projectile> getProjectiles() {
        return proyectiles;
    }
}
