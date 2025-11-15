package entidades.proyectiles;

import com.badlogic.gdx.math.Vector2;

import entidades.Entity;
import enumeradores.recursos.EProjectileType;

public class Swing extends Projectile {
	/**
	 *  Tiempo que dura activo el golpe
	 */
	private float duration = 0.25f;
	/**
	 *  Contador
	 */
	private float activeTime = 0f;
	/**
	 * para que desaparezca el swing
	 */
	private float ratio;
	/**
	 * Entidad de la que saldrá el swing
	 */
	private final Entity entity;

	public Swing(Entity e, int damage, float ratio, float duration, boolean piercing) {
		this(e, EProjectileType.SWING, damage, ratio, duration, piercing);
	}
	
	public Swing(Entity e, EProjectileType type, int damage, float radio, float duration, boolean piercing) {
		super(new Vector2(e.getPosition().x, e.getPosition().y), type, damage, piercing);
		this.entity = e;
		this.duration = duration;
		this.ratio = radio;
		this.rotation = e.getRotation();

		Projectile.calcularMuzzle(position, e, piercing);

		sprite.setRotation(rotation);
		sprite.setPosition(position.x + sprite.getOriginX(), position.y + sprite.getOriginY());
	}

	@Override
	public void update(float delta) {
		this.rotation = entity.getRotation();
		Projectile.calcularMuzzle(position, entity, piercing);
		sprite.setRotation(this.rotation);
		sprite.setPosition(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2);
		
		if (destroyed) return;
		activeTime += delta;
		if (activeTime > duration)
			destroy();
	}
}
