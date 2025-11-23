package armas.proyectiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import data.FlameData;
import entidades.Entity;
import interfaces.IRenderizable;

public class Flame extends Projectile implements IRenderizable {
	private float maxDistance;
	private float maxScale;
	private Vector2 startPosition;
	
	public Flame(FlameData data, Entity shooter) {
		super(Projectile.calcularMuzzle(Vector2.Zero, shooter, data.isPiercing()), data);
		
		this.startPosition = getPosition().cpy();
		this.maxDistance = data.getMaxDistance();
		this.maxScale = data.getMaxScale();
		setRotation(shooter.getRotation() + 90);
		
		float spread = data.getSpread();
		// Setea un ángulo "aleatorio"
		float angleDeviation = MathUtils.random(-spread, spread);
		float speedVariation = MathUtils.random(0f, 2f);
		float finalAngle = getRotation() + angleDeviation;
		float finalSpeed = data.getVelocity() + speedVariation;
		
		setVelocity(new Vector2(0, finalSpeed));
		getVelocity().scl(data.getVelocity());
		getVelocity().setAngleDeg(finalAngle);
		
		getSprite().setRotation(finalAngle);
		getSprite().setOriginCenter();
		getSprite().setScale(data.getScale() / 100f);
	}

	/**
	 * movimiento de la bala, distancia y colision con el borde de la ventana
	 */
	@Override
	public void update(float delta) {
		if (isDestroyed())
			return;
		
		getPosition().add(getVelocity().cpy().scl(delta));
		getSprite().setPosition(getPosition().x - getSprite().getOriginX(), getPosition().y - getSprite().getOriginY());
		
		// distancia
		float distance = getPosition().dst(startPosition);
		if (distance >= maxDistance) {
			destroy();
			return;
		}
		
		float travelPercent = distance / maxDistance;
		float currentScale = 1.0f + (maxScale - 1.0f) * travelPercent;
		getSprite().setScale(currentScale);
        
        if (!Entity.isInBounds(this)) {
            destroy();
        }
;	}
}
