package entidades.proyectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import data.SwingData;
import entidades.Entity;
import factories.AnimationFactory;
import interfaces.IRenderizable;
import logica.AnimationHandler;

public class Swing extends Projectile implements IRenderizable {
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

		setRotation(shooter.getRotation() + 90);
		getSprite().setBounds(0, 0, data.width, data.height);
		getSprite().setOriginCenter();
		
		this.updatePosition();
		
		if (!isBeam)
			this.animation = new AnimationHandler(AnimationFactory.createSwing(), getSprite());
	}

	@Override
	public void update(float delta) {
		this.updatePosition();
		
		if (animation != null)
			animation.updateStateTime(delta);
		if (isDestroyed())
			return;
		
		activeTime += delta;
		if (activeTime > lifespan)
			destroy();
	}

	private void updatePosition() {
		setRotation(shooter.getRotation());

		float shooterWidth = shooter.getSprite().getWidth();
		float shooterHeight = shooter.getSprite().getHeight();

		offsetVector.set(shooter.getPosition().x + shooterWidth / 2f, shooter.getPosition().y + shooterHeight / 2f);

		this.mover(offsetVector, getRotation(), radius);

		if (isBeam)
			this.updateOffset();
	}

	private void updateOffset() {
		float forwardOffset = getSprite().getHeight() / 2f;

		offsetVector.set(1, 0);
		offsetVector.setAngleDeg(getRotation() + 90);
		offsetVector.setLength(forwardOffset);
		this.getPosition().add(offsetVector);

		getSprite().setPosition(getPosition().x - getSprite().getWidth() / 2, getPosition().y - getSprite().getHeight() / 2);
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
