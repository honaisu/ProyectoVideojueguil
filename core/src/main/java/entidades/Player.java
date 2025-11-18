package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import armas.*;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EPlayerSkin;
import factories.AnimationFactory;
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

	// Arma inicial o por defecto
	private Weapon weapon = new FlameShot();


	public Player(float x, float y) {
		// crea el player con skin original nomás
		this(x, y, EPlayerSkin.ORIGINAL);
	}


	public Player(float x, float y, EPlayerSkin skin) {
		super(new Vector2(x, y), skin, 100, true);
		// Lo pone al centro
		position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		sprite.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		sprite.setRotation(rotation);
		sprite.setOriginCenter();

		animation = new AnimationHandler(AnimationFactory.createPlayer(skin), sprite);
	}

	@Override
	public void update(float delta) {
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
		if(weapon != null) {
			weapon.getState().update(delta);
		}

        // Lógica de física con Vector2
		velocity.limit(MAX_VELOCITY);
		Vector2 position = getPosition().cpy().add(velocity.cpy().scl(delta));
		this.position.set(position);
        
        // Lógica de rebote en bordes
		final float HUD_HEIGHT = 100f;
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
	
	public void shoot(ProjectileManager manager) {    	
    	weapon.attack(this, manager);
    	
    	if (weapon.getState().getAmmo() == 0)
    		weapon = new Melee();
    }
	
	
	//Este método ahora funciona bien porque llama al 'takeDamage' de Player (el que acabamos de corregir)
	public void onHazardCollision(DamageHazard hazard) {
		if (hazard.getDamageType() == DamageHazard.DamageType.SPIKE) {
			// PUAS (Golpe fuerte)
			// Esto ahora llama al 'takeDamage' de Player, no al de Creature
			takeDamage(hazard.getDamage()); 

		} else if (hazard.getDamageType() == DamageHazard.DamageType.PUDDLE) {
			// CHARCO (Daño leve, sin iFrames, pero con cooldown propio)
			if (puddleCooldown <= 0) {
                
                // Usa 'hp' directamente para saltarse los iFrames
				this.hp -= hazard.getDamage(); 
				if (hp < 0)
					hp = 0;

				this.puddleCooldown = 0.5f; // Cooldown específico del charco
			}
		}
	}
	
	

	public void bounce() {
        // Invierte la velocidad
		velocity.scl(-1); 

		// Empuja al jugador para "despegarlo" (evitar bug de quedarse atascado)
		// Se aumenta de 5.0f a 10 para probar 
		Vector2 pushVector = velocity.cpy().setLength(10.0f); 
        position.add(pushVector);
	}

	
	
	//seter y getters ordenados
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
		return weapon.getState().getAmmo() > 0 && !(weapon instanceof Melee);
	}

}