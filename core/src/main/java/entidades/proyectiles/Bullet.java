package entidades.proyectiles;

import com.badlogic.gdx.math.Vector2;

import entidades.Entity;
import enumeradores.recursos.EProjectileType;

//Clase que representa una balla dentro del juego

public class Bullet extends Projectile {

	private float lifespan = -1f;

    public Bullet(Entity e, EProjectileType type, int damage, float speed, int scale, boolean piercing) {
        this(e, type, damage, speed, scale, e.getRotation() + 90, piercing);
    }
    
    // bala con dispercion
    public Bullet(Entity e, EProjectileType type, int damage, float speed, int scale, float rotation,
            boolean piercing) {
        super(Projectile.calcularMuzzle(new Vector2(), e, false), type, damage, piercing);
        this.rotation = rotation;
        
        sprite.setPosition(position.x, position.y);
        sprite.setOriginCenter();
        
        sprite.setBounds(position.x - sprite.getOriginX(), position.y - sprite.getOriginY(), scale, scale);
        sprite.setRotation(rotation - 90);

        float radians = (float) Math.toRadians(rotation);
        velocity.set(position.cpy().nor());
        velocity.scl(speed);
        
        velocity.setAngleRad(radians);
        

    }

    // para la explosion
    public Bullet(Entity e, EProjectileType type, int damage, float speed, int scale, boolean piercing,
            float lifespan) {
        this(e, type, damage, speed, scale, piercing);
        this.lifespan = lifespan;
    }

	/**
	 * movimiento de la bala y colision con el borde de la ventana
	 */
	@Override
	public void update(float delta) {
		if (destroyed)
			return;

		if (lifespan > 0) {
			lifespan -= delta;
			if (lifespan <= 0) {
				destroy();
				return;
			}
		}

		// Mueve el sprite (que es ESTA entidad)
		sprite.setPosition(sprite.getX() + velocity.x, sprite.getY() + velocity.y);

		// Comprueba límites
		if (lifespan < 0 && !Entity.isInBounds(this))
			destroy();
	}
}
