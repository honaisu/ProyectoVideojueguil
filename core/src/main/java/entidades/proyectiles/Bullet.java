package entidades.proyectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import data.BulletData;
import entidades.Entity;
import factories.AnimationFactory;
import logica.AnimationHandler;

/**
 * Clase que representa una balla dentro del juego
 */
public class Bullet extends Projectile {
	private float lifespan = -1f;
	private AnimationHandler animation;

	public Bullet(BulletData data, Entity shooter) {
		this(data, shooter, 0f, 0f);
	}
	
	/**
	 * Constructor que permite a una entidad poder disparar la bala correspondiente.
	 */
	public Bullet(BulletData data, Entity shooter, float angle, float speed) {
		super(Projectile.calcularMuzzle(Vector2.Zero, shooter, data.piercing), data);

		this.lifespan = data.lifespan;
		float finalRotation = shooter.getRotation() + 90 + angle;
        float finalVelocity = data.velocity + speed;
        
        // Configuramos la velocidad de la ENTIDAD (no solo del sprite)
        this.velocity.set(finalVelocity, 0);
        this.velocity.setAngleDeg(finalRotation);
        this.rotation = finalRotation - 90; 

        // Configurar visuales
        this.setupSprite(data, this.rotation);
	}

	/**
	 * Constructor utilizado para spawnear la bala en un sector específico (vector).
	 */
	public Bullet(BulletData data, Vector2 spawn) {
		super(spawn, data);

		this.lifespan = data.lifespan;
		// Las explosiones no giran
		this.rotation = 0;

		// Configuración especial para explosiones o spawns estáticos
        sprite.setBounds(position.x, position.y, data.scale, data.scale);
        sprite.setOriginCenter();
        // Centrar el sprite en la coordenada de spawn
        sprite.setPosition(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2);
        this.animation = new AnimationHandler(AnimationFactory.createExplosion(), sprite);
	}

	private void setupSprite(BulletData data, float spriteRotation) {
		sprite.setScale(data.scale);
        sprite.setOriginCenter();
        sprite.setRotation(rotation);
		this.setSpritePosition();
	}

	/**
	 * movimiento de la bala y colision con el borde de la ventana
	 */
	@Override
	public void update(float delta) {
		if (destroyed)
			return;
		
		if (animation != null)
			animation.updateStateTime(delta);

		if (lifespan > 0) {
			lifespan -= delta;
			if (lifespan <= 0) {
				destroy();
				return;
			}
		} else if (!Entity.isInBounds(this)) {
            destroy();
        }
		
		position.add(velocity.x, velocity.y);
		this.setSpritePosition();
	}
	
	private void setSpritePosition() {
        sprite.setPosition(
            position.x - sprite.getWidth() / 2, 
            position.y - sprite.getHeight() / 2
        );
    }
	
	@Override
	public void destroy() {
		super.destroy();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if (animation != null)
			animation.handle(batch, false);
		else
			super.draw(batch);
	}
}
