package entidades.proyectiles;

import com.badlogic.gdx.math.Vector2;

import entidades.Entity;
import enumeradores.recursos.EProjectileType;

public class Flame extends Projectile {

	private float xSpeed;
	private float ySpeed;

	private float startX;
	private float startY;
	private float maxDistance;
	private float finalScale;

	public Flame(Entity e, int damage, float rotation, float speed, float scale, boolean piercing, float maxDistance,
			float finalScale) {
		super(Projectile.calcularMuzzle(new Vector2(), e, false), EProjectileType.FLAME, damage, piercing);

		sprite.setBounds(position.x, position.y, scale, scale);
		sprite.setOriginCenter();
		sprite.setRotation(rotation - 90);

		// Calcular velocidad en X e Y según el ángulo y la velocidad dadas
		float radians = (float) Math.toRadians(rotation);
		this.xSpeed = (float) Math.cos(radians) * speed;
		this.ySpeed = (float) Math.sin(radians) * speed;

		this.startX = position.x;
		this.startY = position.y;
		this.maxDistance = maxDistance;
		this.finalScale = finalScale;
	}

	// movimiento de la bala y colision con el borde de la ventana
	@Override
	public void update(float delta) {
		if (isDestroyed())
			return;

		sprite.setPosition(sprite.getX() + xSpeed, sprite.getY() + ySpeed);

		float currentX = sprite.getX();
		float currentY = sprite.getY();

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
		sprite.setScale(currentScale);

		if (!Entity.isInBounds(this))
			destroy();
	}

}
