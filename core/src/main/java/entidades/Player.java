package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import enumeradores.EPlayerSkin;
import enumeradores.EWeaponType;
import enumeradores.recursos.EGameSound;
import factories.AnimationFactory;
import factories.WeaponFactory;
import interfaces.IAttackable;
import logica.AnimationHandler;
import managers.ProjectileManager;
import managers.assets.AssetManager;
import entidades.obstaculos.DamageHazard;

public class Player extends Creature {
	private final float MAX_VELOCITY = 300.0f;

	// Estado básico
	private int score = 0;
	private int round = 1;

	// Visual y audio
	private Sound hurtSound = AssetManager.getInstancia().getSound(EGameSound.HURT);
	private AnimationHandler animation;

	// Lógica de Daño
	private float puddleCooldown = 0f;
	private boolean hurted = false;
	private float iFrames = 0f;
	private int hurtTime;

	// Lógica de movimiento local
	boolean isMoving = false;

	// Daño progresivo para el charco (PUDDLE)
	private final float PUDDLE_ESCALATION_TIME = 3.50f;
	private float continuousPuddleTime = 0f;
	private boolean isCurrentlyInPuddle = false;
	private final float PUDDLE_DAMAGE_TICK = 0.5f;

	// Arma inicial o por defecto
	private IAttackable weapon;

	public Player(float x, float y, EPlayerSkin skin) {
		super(new Vector2(x, y), skin, 100, true);
		
		this.weapon = WeaponFactory.create(EWeaponType.ROCKET_LAUNCHER);
		// Lo pone al centro
		// Posicionamiento inicial
		getPosition().set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		getSprite().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		getSprite().setRotation(this.getRotation());
		getSprite().setOriginCenter();

		animation = new AnimationHandler(AnimationFactory.createPlayer(skin), getSprite());
	}

	@Override
	public void update(float delta) {
		// Lógica del Puddle
		if (isCurrentlyInPuddle) {
			continuousPuddleTime += delta;
		} else {
			continuousPuddleTime = 0f;
		}

		// Reseteamos el flag para el siguiente ciclo de físicas
		isCurrentlyInPuddle = false;

		// Lógica de Movimiento
		isMoving = !getVelocity().isZero(0.1f);

		if (isMoving) {
			animation.updateStateTime(delta);
		}

		// Lógica de invulnerabilidad
		if (hurted) {
			hurtTime--;
			if (hurtTime <= 0)
				hurted = false;
		}
		if (iFrames > 0f)
			iFrames -= delta;

		// Lógica cooldown charco
		if (puddleCooldown > 0)
			puddleCooldown -= delta;

		// Lógica de Armas
		if (weapon != null) {
			weapon.update(delta);
		}

		getVelocity().limit(MAX_VELOCITY);
		Vector2 position = getPosition().cpy().add(getVelocity().cpy().scl(delta));
		this.setPosition(position);

		// Lógica de rebote en bordes y HUD
		Entity.isInBounds(this);

		// Actualización visual
		getSprite().setPosition(getPosition().x, getPosition().y);
		getSprite().setRotation(getRotation());

		getHealthBar().update();
	}

	@Override
	public void draw(SpriteBatch batch) {
		// Parpadeo por daño
		if (hurted && hurtTime % 10 < 5)
			return;

		animation.updateSprite(getSprite());
		animation.handle(batch, isMoving, true);
	}

	public boolean isHurt() {
		return hurted || iFrames > 0f;
	}

	@Override
	public void takeDamage(int amount) {
		if (isHurt()) {
			return;
		}

		super.takeDamage(amount);

		this.iFrames = 0.5f;
		this.hurted = true;
		this.hurtTime = 120;

		if (hurtSound != null) {
			hurtSound.play();
		}
	}

	public void rotate(float amount) {
		this.setRotation(getRotation() + amount);
	}

	public void accelerate(float amount) {
		Vector2 acceleration = new Vector2(0, 1);
		acceleration.setAngleDeg(getRotation() + 90);
		acceleration.scl(amount);

		getVelocity().add(acceleration);
	}

	public void applyFriction(float friction) {
		getVelocity().scl(friction);
	}

	public void shoot(ProjectileManager manager) {
		weapon.attack(this, manager);

		if (!hasWeapon())
			weapon = WeaponFactory.create(EWeaponType.MELEE);
	}

	// Manejo de colisiones con Hazards
	public void onHazardCollision(DamageHazard hazard) {
		if (hazard.getDamageType() == DamageHazard.DamageType.SPIKE) {
			// Golpe directo
			takeDamage(hazard.getDamage());

		} else if (hazard.getDamageType() == DamageHazard.DamageType.PUDDLE) {
			// Charco con daño progresivo
			isCurrentlyInPuddle = true;

			if (puddleCooldown <= 0) {
				// Cálculo de daño escalado
				int damageMultiplier = (int) (continuousPuddleTime / PUDDLE_ESCALATION_TIME) + 1;
				int finalDamage = (int) (hazard.getDamage() * damageMultiplier);

				super.takeDamage(finalDamage);

				this.puddleCooldown = PUDDLE_DAMAGE_TICK;
			}
		}
	}

	public void bounce() {
		getVelocity().scl(-0.7f);

		Vector2 pushVector = getVelocity().cpy().setLength(10.0f);
		getPosition().add(pushVector);
	}

	// Setters y Getters

	public void setWeapon(IAttackable newWeapon) {
		this.weapon = newWeapon;
	}

	public IAttackable getWeapon() {
		return weapon;
	}

	public int getRound() {
		return round;
	}

	public int getScore() {
		return score;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public boolean hasWeapon() {
		if (weapon == null)
			return false;
		return weapon.isValid();
	}
}