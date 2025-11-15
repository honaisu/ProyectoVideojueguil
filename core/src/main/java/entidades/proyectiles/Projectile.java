package entidades.proyectiles;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import entidades.Enemy;
import entidades.Entity;
import entidades.IRenderizable;
import interfaces.ITexture;

public abstract class Projectile extends Entity implements IRenderizable {
	protected final List<Enemy> enemiesHit; // TODO revisar
	protected boolean piercing;
	protected int damage;

	public Projectile(Vector2 position, ITexture asset, int damage, boolean piercing) {
		super(position, asset);
		this.enemiesHit = new ArrayList<>();
		this.piercing = piercing;
		this.damage = damage;
	}

	public Projectile(float x, float y, ITexture asset, int damage, boolean piercing) {
		this(new Vector2(x, y), asset, damage, piercing);
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
		} else {
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
	public static void calcularMuzzle(Vector2 position, Rectangle r, float rotation, boolean onTip) {
		float centerX = r.getX() + r.getWidth() / 2f;
		float centerY = r.getY() + r.getHeight() / 2f;
		Vector2 center = new Vector2(centerX, centerY);

		if (onTip) {
			float length = r.getHeight() / 2f;

			position.set(length, 0).setAngleDeg(rotation).add(center);
		} else {
			position.set(center);
		}
	}

	/**
	 * Método estático encargado de calcular el muzzle entre un vector y una
	 * entidad.
	 * 
	 * @param position Vector a modificar (posición del proyectil).
	 * @param entity   Entidad con la que queremos calcular su muzzle.
	 * @param onTip    Si queremos que esté "en el tip" de la entidad o no.
	 */
	public static void calcularMuzzle(Vector2 position, Entity entity, boolean onTip) {
		Rectangle r = entity.getSprite().getBoundingRectangle();
		float rotation = entity.getRotation() + 90;
		calcularMuzzle(position, r, rotation, onTip);
	}
}