package entidades.proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entidades.Player;
import enumeradores.recursos.EProjectileType;
import factories.SpriteFactory;

public class Flame extends Projectile {

	private float xSpeed;
	private float ySpeed;

	private float startX;
	private float startY;
	private float maxDistance;
	private float finalScale;

	public Flame(float x, float y, float width, float rotation, float speed, Player p, boolean piercing,
			float maxDistance, float finalScale) {
		super(x, y, SpriteFactory.create(EProjectileType.FLAME), p, p.getWeapon().getDamage(), piercing);
		getSpr().setBounds(x, y, width, width);
		getSpr().setOriginCenter();
		getSpr().setRotation(rotation - 90);

		// Calcular velocidad en X e Y según el ángulo y la velocidad dadas
		float radians = (float) Math.toRadians(rotation);
		this.xSpeed = (float) Math.cos(radians) * speed;
		this.ySpeed = (float) Math.sin(radians) * speed;

		this.startX = x;
		this.startY = y;
		this.maxDistance = maxDistance;
		this.finalScale = finalScale;
	}

	// movimiento de la bala y colision con el borde de la ventana
	@Override
	public void update(float delta, Player player) {
		if (isDestroyed())
			return;

		Sprite spr = getSpr();

		spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);

		float currentX = spr.getX();
		float currentY = spr.getY();

		// Calcular la distancia maxima de la flama
		float distanceSq = (currentX - startX) * (currentX - startX) + (currentY - startY) * (currentY - startY);
		float maxDistanceSq = maxDistance * maxDistance;

		if (distanceSq >= maxDistanceSq) {
			destroy();
			return;
		}

		// Cambiar de tamaño
		float currentDistance = (float) Math.sqrt(distanceSq);
		float travelPercent = currentDistance / maxDistance;

		float currentScale = 1.0f + (finalScale - 1.0f) * travelPercent;
		spr.setScale(currentScale);

		if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() || spr.getY() < 0
				|| spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
			destroy();
		}
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

}
