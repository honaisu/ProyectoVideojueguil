package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import enumeradores.EWeaponType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EPlayerSkin;
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

	// Lógica de Daño merge
	private float puddleCooldown = 0f; // Cooldown para el daño de charco (se reduce el culdawn de daño si no mal
	private boolean hurted = false;
	private float iFrames = 0f;
	private int hurtTime;
	// recuerdo)

	// Arma inicial o por defecto
	private IAttackable weapon;
	
	public Player(float x, float y) {
		// crea el player con skin original nomás
		this(x, y, EPlayerSkin.ORIGINAL);
	}

	public Player(float x, float y, EPlayerSkin skin) {
		super(new Vector2(x, y), skin, 100, true);
		
		this.weapon = WeaponFactory.create(EWeaponType.FLAMESHOT);
		// Lo pone al centro
		getPosition().set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		getSprite().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		getSprite().setRotation(this.getRotation());
		getSprite().setOriginCenter();

		animation = new AnimationHandler(AnimationFactory.createPlayer(skin), getSprite());
	}

	@Override
	public void update(float delta) {
		if (isMoving())
			animation.updateStateTime(delta);

		// Lógica de invulnerabilidad
		if (hurted) {
			hurtTime--; // Esto es un contador de frames para el parpadeo
			if (hurtTime <= 0)
				hurted = false;
		}
		if (iFrames > 0f) // Este es el temporizador de invulnerabilidad real
			iFrames -= delta;
		
        // Lógica del charco
		if (puddleCooldown > 0)
			puddleCooldown -= delta; 

		if (weapon != null) {
            weapon.update(delta);
        }

		getVelocity().limit(MAX_VELOCITY);
		Vector2 position = getPosition().cpy().add(getVelocity().cpy().scl(delta));
		this.setPosition(position);
        
		Entity.isInPlayableBounds(this, HUD_HEIGHT);

		getSprite().setPosition(getPosition().x, getPosition().y);
		getSprite().setRotation(getRotation());
		
		getHealthBar().update();

	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// Si está herido, parpadea (no se dibuja en algunos frames)
		if (hurted && hurtTime % 10 < 5)
			return; // Parpadeo

		animation.updateSprite(getSprite());
		animation.handle(batch, isMoving(), true);
	}
	
	/**
	 * Verifica si el jugador es invulnerable (ya sea por iFrames o por parpadeo)
	 */
	public boolean isHurt() {
		return hurted || iFrames > 0f; 
	}

	@Override
	public void takeDamage(int amount) {
		// Si ya estamos en iFrames, no recibimos más daño.
		if (isHurt()) {
			return; 
		}

		super.takeDamage(amount); 
		
		// Activa los iFrames y el parpadeo
		this.iFrames = 0.5f; 
		this.hurted = true; 
		this.hurtTime = 120;  
		
		// Suena el efecto de sonido
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
	
	public void onHazardCollision(DamageHazard hazard) {
		if (hazard.getDamageType() == DamageHazard.DamageType.SPIKE) {
			// PUAS (Golpe fuerte)
			takeDamage(hazard.getDamage());
		} else if (hazard.getDamageType() == DamageHazard.DamageType.PUDDLE) {
			// CHARCO (Daño leve con cooldown propio)
			if (puddleCooldown <= 0) {
                takeDamage(hazard.getDamage());

				this.puddleCooldown = 0.5f; // Cooldown específico del charco
			}
		}
	}

	public void bounce() {
        // Invierte la velocidad
		getVelocity().scl(-1); 

		// Empuja al jugador para "despegarlo" (evitar bug de quedarse atascado)
		// Se aumenta de 5.0f a 10 para probar 
		Vector2 pushVector = getVelocity().cpy().setLength(10.0f); 
        getPosition().add(pushVector);
	}
	
	//seter y getters ordenados
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
    
	public boolean hasWeapon() {
		if (weapon == null) return false;
		return weapon.isValid();
	}

}