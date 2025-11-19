package entidades.proyectiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import data.FlameData;
import entidades.Entity;

public class Flame extends Projectile {
	private float maxDistance;
	private float maxScale;
	private Vector2 startPosition;
	
	public Flame(FlameData data, Entity shooter) {
		super(Projectile.calcularMuzzle(Vector2.Zero, shooter, data.piercing), data);
		
		this.startPosition = position.cpy();
		this.maxDistance = data.maxDistance;
		this.maxScale = data.maxScale;
		this.rotation = shooter.getRotation() + 90;
		
		float spread = data.spread;
		// Setea un ángulo "aleatorio"
		float angleDeviation = MathUtils.random(-spread, spread);
		float speedVariation = MathUtils.random(0f, 2f);
		float finalAngle = rotation + angleDeviation;
		float finalSpeed = data.velocity + speedVariation;
		
		this.velocity = new Vector2(0, finalSpeed);
		this.velocity.scl(data.velocity);
		this.velocity.setAngleDeg(finalAngle);
		
		sprite.setRotation(finalAngle);
        sprite.setOriginCenter();
        sprite.setScale(data.scale / 100f);
	}

	/**
	 * movimiento de la bala y colision con el borde de la ventana
	 */
	@Override
	public void update(float delta) {
		if (isDestroyed())
			return;
		
		position.add(velocity.cpy().scl(delta));
		sprite.setPosition(position.x - sprite.getOriginX(), position.y - sprite.getOriginY());
		
		float distance = position.dst(startPosition);
		if (distance >= maxDistance) {
			destroy();
			return;
		}
		
		float travelPercent = distance / maxDistance;
		float currentScale = 1.0f + (maxScale - 1.0f) * travelPercent;
        sprite.setScale(currentScale);
        
        if (!Entity.isInBounds(this)) {
            destroy();
        }
;	}
}
