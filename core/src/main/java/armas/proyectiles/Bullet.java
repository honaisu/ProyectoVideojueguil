package armas.proyectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import data.BulletData;
import entidades.Entity;
import factories.AnimationFactory;
import interfaces.IRenderizable;
import logica.AnimationHandler;

/**
 * Clase que representa una balla dentro del juego
 */
public class Bullet extends Projectile implements IRenderizable {
	private float lifespan = -1f;
	private AnimationHandler animation;

	public Bullet(BulletData data, Entity shooter) {
		this(data, shooter, 0f, 0f);
	}
	
	/**
	 * Constructor que permite a una entidad poder disparar la bala correspondiente.
	 */
	public Bullet(BulletData data, Entity shooter, float angle, float speed) {
		super(Projectile.calcularMuzzle(Vector2.Zero, shooter, data.isPiercing()), data);

		this.lifespan = data.getLifespan();
		float finalRotation = shooter.getRotation() + 90 + angle;
        float finalVelocity = data.getVelocity() + speed;
        
        // Configuramos la velocidad de la ENTIDAD (no solo del sprite)
        this.getVelocity().set(finalVelocity, 0);
        this.getVelocity().setAngleDeg(finalRotation);
        setRotation(finalRotation - 90); 

        // Configurar visuales
        this.setupSprite(data, getRotation());
	}

	/**
	 * Constructor utilizado para spawnear la bala en un sector específico (vector).
	 */
	public Bullet(BulletData data, Vector2 spawn) {
		super(spawn, data);

		this.lifespan = data.getLifespan();
		// Las explosiones no giran
		setRotation(0);

		// Configuración especial para explosiones o spawns estáticos
        getSprite().setBounds(getPosition().x, getPosition().y, data.getScale(), data.getScale());
        getSprite().setOriginCenter();
        // Centrar el sprite en la coordenada de spawn
        getSprite().setPosition(getPosition().x - getSprite().getWidth()/2, getPosition().y - getSprite().getHeight()/2);
        this.animation = new AnimationHandler(AnimationFactory.createExplosion(), getSprite());
	}

	private void setupSprite(BulletData data, float spriteRotation) {
		getSprite().setScale(data.getScale());
        getSprite().setOriginCenter();
        getSprite().setRotation(spriteRotation);
		this.setSpritePosition();
	}

	/**
	 * movimiento de la bala y colision con el borde de la ventana
	 */
	@Override
	public void update(float delta) {
		if (isDestroyed())
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
		
		getPosition().add(getVelocity().x, getVelocity().y);
		this.setSpritePosition();
	}
	
	private void setSpritePosition() {
        getSprite().setPosition(
            getPosition().x - getSprite().getWidth() / 2, 
            getPosition().y - getSprite().getHeight() / 2
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
