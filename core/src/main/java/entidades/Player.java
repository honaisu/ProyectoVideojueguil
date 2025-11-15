package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import armas.*;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EPlayerSkin;
import factories.AnimationFactory;
import logica.AnimationHandler;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class Player extends Creature {
	private final float MAX_VELOCITY = 250.0f;
	// Estado básico
	private int score = 0;
	private int round = 1;
	// Visual y audio
	private Sound hurtSound = AssetManager.getInstancia().getSound(EGameSound.HURT);
	private AnimationHandler animation;

	// Lógica de Daño merge
	private boolean hurted = false;
	private int hurtTime;
	private float iFrames = 0f;

	boolean isMoving = false;

	// Arma inicial o por defecto
	private Weapon weapon = new Melee();

	public Player(float x, float y) {
		// crea el player con skin original nomás
		this(x, y, EPlayerSkin.ORIGINAL);
	}

	public Player(float x, float y, EPlayerSkin skin) {
		super(new Vector2(x, y), skin, 100);
		// Lo pone al centro
		position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		sprite.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		sprite.setRotation(rotation);
		sprite.setOriginCenter();

		animation = new AnimationHandler(AnimationFactory.createPlayer(skin), sprite);
	}

	@Override
	public void update(float delta) {
		// el 0.1f es un margen de error
		isMoving = !velocity.isZero(0.1f);
		if (isMoving)
			animation.updateStateTime(delta);
		;

		// Lógica de daño
		if (hurted) {
			hurtTime--;
			if (hurtTime <= 0)
				hurted = false;
		}
		if (iFrames > 0f)
			iFrames -= delta;

		velocity.limit(MAX_VELOCITY);

		Vector2 position = getPosition().cpy().add(velocity.cpy().scl(delta));

		this.position.set(position);
		Entity.isInBounds(this);

		sprite.setPosition(position.x, position.y);
		sprite.setRotation(rotation);

		// weapon.update(delta);
	}

	@Override
	public void draw(SpriteBatch batch) {
		// Si está herido, se podría aplicar un efecto de parpadeo con el color del
		// batch
		if (hurted && hurtTime % 10 < 5)
			return;

		animation.updateSprite(getSprite());
		animation.handle(batch, isMoving, true);
	}

	// TODO revisar tema de la vida//
	public void takeDamage(int damage) {
		// Solo recibe daño si no está herido (invulnerable)
		if (hurted)
			return;

		this.hp -= damage;
		this.hurted = true;
		// Invulnerable por 120 frames (aprox 2 segundos)
		this.hurtTime = 120;

		if (hurtSound != null)
			hurtSound.play();
	}

	public boolean isHurt() {
		return hurted;
	}

	// TODO revisar tema de la vida//
	public void rotate(float amount) {
		this.rotation += amount;
	}

	public void accelerate(float amount) {
		Vector2 acceleration = new Vector2(0, 1);
		acceleration.setAngleDeg(rotation + 90);
		acceleration.scl(amount);

		velocity.add(acceleration);
	}

	public void applyFriction(float friction) {
		velocity.scl(friction);
	}

	public void shoot(float delta, ProjectileManager manager) {
		weapon.atacar(delta, this, manager);

		if (weapon.getState().getAmmo() == 0)
			weapon = new Melee();
	}

	public void setWeapon(Weapon newWeapon) {
		this.weapon = newWeapon;
	}

	// Getters básicos
	public float getRotation() {
		return rotation;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public int getRound() {
		return round;
	}

	public int getScore() {
		return score;
	}

	public boolean hasWeapon() {
		return weapon.getState().canShoot() && !(weapon instanceof Melee);
	}
}
