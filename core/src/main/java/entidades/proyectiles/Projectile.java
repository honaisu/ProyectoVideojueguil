package entidades.proyectiles;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import entidades.Enemy;
import entidades.Entity;
import entidades.Player;

public abstract class Projectile extends Entity {

	private int damage;
	private List<Enemy> enemiesHit; // revisar
	private boolean isPiercing;
	

	public Projectile(float x, float y, Sprite spr, Player player, int damage, boolean piercing) {
		super(x, y, spr);
		this.damage = damage;
		this.enemiesHit = new ArrayList<>();
		this.isPiercing = piercing;
	}

	public abstract void update(float delta, Player player);

	public boolean onHitEnemy(Enemy enemy) {
		if(isPiercing) {
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

	public static float[] calcularMuzzle(Player p, boolean b) {
		Rectangle playerRectangle = p.getSpr().getBoundingRectangle();

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

	public void draw(SpriteBatch batch) {
		if (!isDestroyed()) {
			super.draw(batch);
		}
	}

	public int getDamage() {
		return damage;
	}

}