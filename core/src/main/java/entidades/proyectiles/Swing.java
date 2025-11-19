package entidades.proyectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import data.SwingData;
import entidades.Entity;
import enumeradores.recursos.EProjectileType;
import factories.AnimationFactory;
import logica.AnimationHandler;

public class Swing extends Projectile {
	// Contador
	private float activeTime = 0f;
	// Tiempo que dura activo el golpe
	private float lifespan;
	// distancia de la que saldrá el swing
	private float radius;
	// para que identifique si es un laser o no
	private boolean isBeam;

	private final Entity shooter;
	private final Vector2 offsetVector = new Vector2();
	private AnimationHandler animation;

	public Swing(SwingData data, Entity shooter) {
		super(shooter.getPosition().cpy(), data);
		this.shooter = shooter;

		this.lifespan = data.duration;
		this.isBeam = data.isBeam;
		this.radius = data.radius;

		this.rotation = shooter.getRotation() + 90;

		sprite.setBounds(0, 0, data.width, data.height);

		sprite.setOriginCenter();
		this.updatePosition();
		
		if (!isBeam)
			this.animation = new AnimationHandler(AnimationFactory.createSwing(), sprite);
	}

	@Override
	public void update(float delta) {
		this.updatePosition();
		
		if (animation != null)
			animation.updateStateTime(delta);

		if (destroyed)
			return;
		activeTime += delta;
		if (activeTime > lifespan)
			destroy();
	}

	private void updatePosition() {
		this.rotation = shooter.getRotation();

		float shooterWidth = shooter.getSprite().getWidth();
		float shooterHeight = shooter.getSprite().getHeight();

		offsetVector.set(shooter.getPosition().x + shooterWidth / 2f, shooter.getPosition().y + shooterHeight / 2f);

		this.mover(offsetVector, this.rotation, radius);

		if (isBeam)
			this.updateOffset();
	}

	private void updateOffset() {
		float forwardOffset = sprite.getHeight() / 2f;

		offsetVector.set(1, 0);
		offsetVector.setAngleDeg(rotation + 90);
		offsetVector.setLength(forwardOffset);
		this.position.add(offsetVector);

		sprite.setPosition(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if (animation != null) {
			animation.handle(batch, false); 
		} else {
			super.draw(batch);
		}
	}
}
