package entidades.proyectiles;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import entidades.Enemy;
import entidades.Entity;
import entidades.Player;
import interfaces.ITexture;

public abstract class Projectile extends Entity {
	private List<Enemy> enemiesHit; // TODO revisar
	private boolean isPiercing;
	private int damage;
	
	public Projectile(Vector2 position, ITexture asset, int damage, boolean piercing) {
		super(position, asset);
		this.enemiesHit = new ArrayList<>();
		this.isPiercing = piercing;
		this.damage = damage;
	}

	public Projectile(float x, float y, ITexture asset, int damage, boolean piercing) {
		super(new Vector2(x, y), asset);
		this.damage = damage;
		this.enemiesHit = new ArrayList<>();
		this.isPiercing = piercing;
	}

	/**
	 * Método abstracto usado por los proyectiles para poder generar su lógica interna
	 */
	public abstract void update(float delta, Player player);
	
	public boolean onHitEnemy(Enemy enemy) {
		if (isPiercing) {
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
	
	public static float calcularMuzzle(boolean condition, Player p) {
		return calcularMuzzle(new Vector2(), condition, p);
	}
	
	public static float[] calcularMuzzle(Player p, boolean b) {
		Rectangle playerRectangle = p.getSprite().getBoundingRectangle();

		float rot = p.getRotation() + 90;
		float centerX = playerRectangle.getX() + playerRectangle.getWidth() / 2;
		float centerY = playerRectangle.getY() + playerRectangle.getHeight() / 2;
		float length = playerRectangle.getHeight() / 2f;
		float rad = (float) Math.toRadians(p.getRotation() + 90);
		float mx = centerX + (float) Math.cos(rad) * length;
		float my = centerY + (float) Math.sin(rad) * length;

		if (b) {
			return new float[] { mx, my, rot };
		}
		return new float[] { centerX, centerY, rot };
	}
	
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

	public static float calcularMuzzle(Vector2 position, boolean condition, Player p) {
		Rectangle r = p.getSprite().getBoundingRectangle();
		float rot = p.getRotation() + 90;
		
		float centerX = r.getX() + r.getWidth() / 2f;
		float centerY = r.getY() + r.getHeight() / 2f;
		Vector2 center = new Vector2(centerX, centerY);
		
		if (condition) {
			float length = r.getHeight() / 2f;
			
			position.set(length, 0).setAngleDeg(rot).add(center);
		} else {
			position.set(center);
		}
		
		return rot;
	}

	public void draw(SpriteBatch batch) {
		if (!isDestroyed()) {
			super.draw(batch);
		}
	}

	public int getDamage() {
		return damage;
	}

}