package entidades.proyectiles;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import data.ProjectileData;
import entidades.Enemy;
import entidades.Entity;
import interfaces.IRenderizable;

public abstract class Projectile extends Entity implements IRenderizable {
	// TODO revisar
	protected final List<Enemy> enemiesHit = new ArrayList<>(); 
	protected boolean piercing;
	protected int damage;

	public Projectile(float x, float y, ProjectileData data) {
		this(new Vector2(x, y), data);
	}

	public Projectile(Vector2 position, ProjectileData data) {
		super(position, data.type);
		this.piercing = data.piercing;
		this.damage = data.damage;
	}

	/**
	 * Método abstracto usado por los proyectiles para poder generar su lógica
	 * interna
	 */
	@Override
	public abstract void update(float delta);

	/**
	 * Método que dibuja el proyectil mientras no sea destruido
	 */
	@Override
	public void draw(SpriteBatch batch) {
		if (!destroyed)
			super.draw(batch);
	}

	public boolean onHitEnemy(Enemy enemy) {
		if (piercing) {
			if (!enemiesHit.contains(enemy)) { // piercing
				enemy.takeDamage(damage);
				enemiesHit.add(enemy);
			}
			return false;
		}else {
			enemy.takeDamage(damage);
			return true;
		}

	}

	public int getDamage() {
		return damage;
	}

	/**
	 * Método estático encargado de calcular el "muzzle" por el que dependerá el
	 * proyectil.
	 * 
	 * @param position Posición a la cuál apuntará el proyectil, modificándolo
	 * @param r        Rectángulo que indica el nuevo vector resultante
	 * @param rotation Rotación a la cuál irá el proyectil
	 * @param onTip    Para ver si queremos que esté "en el tip" del rectángulo
	 */
	public static Vector2 calcularMuzzle(Vector2 position, Rectangle r, float rotation, boolean onTip) {
		float cX = r.getX() + r.getWidth() / 2f;
		float cY = r.getY() + r.getHeight() / 2f;
		float length = r.getHeight() / 2f;
		
		float rad = (float) Math.toRadians(rotation);
		float mx = cX + (float) Math.cos(rad) * length;
		float my = cY + (float) Math.sin(rad) * length;

		if (onTip) {
			return new Vector2(mx, my);
		}
		return new Vector2(cX, cY);
	}

	/**
	 * Método estático encargado de calcular el muzzle entre un vector y una
	 * entidad.
	 * 
	 * @param position Vector a modificar (posición del proyectil).
	 * @param entity   Entidad con la que queremos calcular su muzzle.
	 * @param onTip    Si queremos que esté "en el tip" de la entidad o no.
	 */
	public static Vector2 calcularMuzzle(Vector2 position, Entity entity, boolean onTip) {
		Rectangle r = entity.getSprite().getBoundingRectangle();
		float rotation = entity.getRotation() + 90;
		
		
		
		return calcularMuzzle(position, r, rotation, onTip);
	}
}