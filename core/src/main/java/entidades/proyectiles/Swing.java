package entidades.proyectiles;

import com.badlogic.gdx.math.Vector2;

import entidades.Entity;
import enumeradores.recursos.EProjectileType;

public class Swing extends Projectile {
	/**
	 *  Tiempo que dura activo el golpe
	 */
	private float lifespan = 0.25f;
	/**
	 *  Contador
	 */
	private float activeTime = 0f;
	/**
	 * distancia de la que saldrá el swing
	 */
	private float radius;
	/**
	 * para que identifique si es un laser o no
	 */
	private boolean isBeam;
	
	private final Entity entity;

	public Swing(Entity e, int damage, float duration, EProjectileType type, int width, int height, float radius, boolean piercing, boolean isBeam) {
		//this(e, type, width, height, damage, duration, piercing);
		super(Projectile.calcularMuzzle(new Vector2(), e, isBeam), type, damage, piercing);
		this.entity = e;
		this.lifespan = duration;
		this.rotation = e.getRotation();
		
		this.isBeam = isBeam;
		this.radius = radius;
		
		sprite.setBounds(position.x - sprite.getOriginX(), position.y - sprite.getOriginY(), width, height);
		sprite.setRotation(rotation);
		
		if (isBeam){//Lasers
			sprite.setOrigin(width / 2f, radius);
		} else{//Melee
			sprite.setOriginCenter();
		}
		
	}

	@Override
	public void update(float delta) {
		this.rotation = entity.getRotation();
		Vector2 muzzle= Projectile.calcularMuzzle(position, entity, isBeam);
		mover(muzzle, this.rotation, radius);
		
		if (destroyed) return;
		activeTime += delta;
		if (activeTime > lifespan)
			destroy();
	}
}
