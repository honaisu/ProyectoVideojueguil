package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import armas.*;
import enumeradores.EWeaponType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EPlayerSkin;
import factories.AnimationFactory;
import factories.WeaponFactory;
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
	private boolean hurted = false;
	private int hurtTime;
	private float iFrames = 0f;
	private float puddleCooldown = 0f; // Cooldown para el daño de charco (se reduce el culdawn de daño si no mal
	// recuerdo)

	boolean isMoving = false;

	// Daño progresivo para el charco (PUDDLE)
	private final float PUDDLE_ESCALATION_TIME = 2.0f; // Segundos para que el daño aumente 1x (antes estaba en 3)
	private float continuousPuddleTime = 0f; // Tiempo continuo en el charco
	private boolean isCurrentlyInPuddle = false; // Flag para rastrear la colisión
	private final float PUDDLE_DAMAGE_TICK = 0.5f; // Frecuencia de daño (0.5 segundos)

	// Arma inicial o por defecto
	//private Weapon weapon = new HeavyMachineGun();

	private Weapon weapon;
	
	public Player(float x, float y) {
		// crea el player con skin original nomás
		this(x, y, EPlayerSkin.ORIGINAL);
	}

	public Player(float x, float y, EPlayerSkin skin) {
		super(new Vector2(x, y), skin, 100, true);
		
		this.weapon = WeaponFactory.create(EWeaponType.MELEE);
		// Lo pone al centro
		position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		sprite.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		sprite.setRotation(rotation);
		sprite.setOriginCenter();

		animation = new AnimationHandler(AnimationFactory.createPlayer(skin), sprite);
	}

	@Override
	public void update(float delta) {

		// logica si esta encima del charco
		if (isCurrentlyInPuddle) { 
			continuousPuddleTime += delta;
		} else {
			continuousPuddleTime = 0f;
		}

		// Reseteamos el flag aquí. Esto prepara el estado para que CollisionManager lo cambie a TRUE
		// en la fase de colisiones que ocurre después de este método.
		isCurrentlyInPuddle = false; 

		// Revisa si hay movimiento
		isMoving = !velocity.isZero(0.1f);
		if (isMoving)
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

		// Lógica de armas
		if (weapon != null) {
			weapon.getState().update(delta);
		}

		// Lógica de física con Vector2
		velocity.limit(MAX_VELOCITY);
		Vector2 position = getPosition().cpy().add(velocity.cpy().scl(delta));
		this.position.set(position);
		
		
        //de nachoid, algo más específico y mejor
        // Lógica de rebote en bordes
		Entity.isInPlayableBounds(this, HUD_HEIGHT);
		//Entity.isInBounds(this); 

		sprite.setPosition(this.position.x, this.position.y);
		sprite.setRotation(rotation);
		
		getHealthBar().update();


	}

	@Override
	public void draw(SpriteBatch batch) {
		// Si está herido, parpadea (no se dibuja en algunos frames)
		if (hurted && hurtTime % 10 < 5)
			return; // Parpadeo

		animation.updateSprite(getSprite());
		animation.handle(batch, isMoving, true);
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

	/*public void shoot(ProjectileManager manager) {
		weapon.attack(this, manager);

		if (weapon.getState().getAmmo() == 0)
			weapon = new Melee();
	}*/

	public void shoot(ProjectileManager manager) {
		weapon.attack(this, manager);
		
		if (weapon.getState().getAmmo() == null) return;

		if (weapon.getState().getAmmo() == 0)
			weapon = WeaponFactory.create(EWeaponType.MELEE);
	}
	
	/*public void onHazardCollision(DamageHazard hazard) {
		if (hazard.getDamageType() == DamageHazard.DamageType.SPIKE) {
			// PUAS (Golpe fuerte)
			takeDamage(hazard.getDamage());
		} else if (hazard.getDamageType() == DamageHazard.DamageType.PUDDLE) {
			// CHARCO (Daño leve con cooldown propio)
			if (puddleCooldown <= 0) {
                
				this.hp -= hazard.getDamage(); 
>>>>>>> origin/noche
				if (hp < 0)
					hp = 0;

				this.puddleCooldown = PUDDLE_DAMAGE_TICK;
			}
		}
	}*/
	
	
	
	public void onHazardCollision(DamageHazard hazard) {
		if (hazard.getDamageType() == DamageHazard.DamageType.SPIKE) {
			//Logica de pua
			takeDamage(hazard.getDamage());

		} else if (hazard.getDamageType() == DamageHazard.DamageType.PUDDLE) {
			//Charco con daño progresivo
			isCurrentlyInPuddle = true; 

			// Comprueba el cooldown de daño 
			if (puddleCooldown <= 0) {

				//Si pasa más tiempo en el poso, realiza más daño 
				int damageMultiplier = (int) (continuousPuddleTime / PUDDLE_ESCALATION_TIME) + 1;

				int finalDamage = (int) (hazard.getDamage() * damageMultiplier);

				this.hp -= finalDamage; 
				if (hp < 0)
					hp = 0;

				this.puddleCooldown = PUDDLE_DAMAGE_TICK;
			}
		}
	}

	public void bounce() {
		// 1. Amortigua la velocidad (pierde el 30% de la energía)
		velocity.scl(-0.7f);

		// 2. Empuja lo suficiente para salir (5.0f es suficiente)
		Vector2 pushVector = velocity.cpy().setLength(10.0f);
		position.add(pushVector);
	}

	public void setWeapon(Weapon newWeapon) {
		this.weapon = newWeapon;
	}

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
		if (weapon.getState().getAmmo() == null) return false;
		return weapon.getState().getAmmo() > 0;
	}

}