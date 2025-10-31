package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import armas.*;
import enumeradores.ESkinJugador;
import hitboxes.Hitbox;
import logica.AnimationFactory;
import managers.AssetManager;
import managers.ProjectileManager;

public class Player extends Hitbox {
	private final float MAX_VELOCITY = 10.0f;
	// Estado básico 
	private int score = 0;
    private int round = 1;
    private int life; // ANTES: lives

    
	private float xVel;
	private float yVel;
	private float rotation;

	// Visual y audio
	private Sound hurtSound = AssetManager.getInstancia().getHurtSound();
	
	// ENCARGADOS DE ANIMACIÓN
	private Animation<TextureRegion> animation;
    private float stateTime = 0f;
    
	// Herido
	private boolean hurted = false;
	private int hurtTime;
	
	// Armas
	private Weapon weapon = new HeavyMachineGun();
	
	public Player(float x, float y) {
		super(x, y, ESkinJugador.JUGADOR_ORIGINAL.crearSprite());
		
		this.life = 3;
		this.xVel = 0f;
		this.yVel = 0f;
		this.rotation = 0f;
	    
		// IMPLEMENTACIÓN DE LA ANIMACIÓN
    	this.animation = AnimationFactory.createJugadorAnimation(ESkinJugador.JUGADOR_ORIGINAL);
    	
    	getSpr().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    	getSpr().setOriginCenter();
	}
	
	public void update(float delta) {
        boolean isMoving = (Math.abs(xVel) > 0.1 || Math.abs(yVel) > 0.1);
        if (isMoving) stateTime += delta;

	    if (hurted) {
            hurtTime--;
            if (hurtTime <= 0) hurted = false;
	    }

        // Límites de velocidad
        xVel = MathUtils.clamp(xVel, -MAX_VELOCITY, MAX_VELOCITY);
        yVel = MathUtils.clamp(yVel, -MAX_VELOCITY, MAX_VELOCITY);

        // Rebote en bordes
        float positionX = getSpr().getX() + xVel;
    	float positionY = getSpr().getY() + yVel;
        this.borderBounce(positionX, positionY);

        // Aplicar velocidad a la posición
        getSpr().setPosition(positionX, positionY);
        getSpr().setRotation(rotation);

        // Actualizar el arma
        weapon.actualizar(delta);
    }
	
	public void draw(SpriteBatch batch) {
 		TextureRegion currentFrame;
 		boolean isMoving = (Math.abs(xVel) > 0.1 || Math.abs(yVel) > 0.1);

 	    if (isMoving) {
 	        currentFrame = animation.getKeyFrame(stateTime, true);
 	    } else {
 	        currentFrame = animation.getKeyFrame(0, true);
 	    }
         
        // Si está herido, se podría aplicar un efecto de parpadeo con el color del batch
        if (hurted) {
        	hurtSound.play();
            // Lógica simple de parpadeo
            if (hurtTime % 10 < 5) {
                return;
            }
        }

	    batch.draw(currentFrame,
	    		getSpr().getX(), getSpr().getY(),
	    		getSpr().getOriginX(), getSpr().getOriginY(),
	    		getSpr().getWidth(), getSpr().getHeight(),
	    		getSpr().getScaleX(), getSpr().getScaleY(),
	    		getSpr().getRotation());
	}
	
	public void rotate(float amount) {
        if (hurted) return;
        this.rotation += amount;
    }

    public void accelerate(float amount) {
        if (hurted) return;
        xVel -= (float) Math.sin(Math.toRadians(rotation)) * amount;
        yVel += (float) Math.cos(Math.toRadians(rotation)) * amount;
    }

    public void applyFriction(float friction) {
        xVel *= friction;
        yVel *= friction;
    }
    
    public void shoot(float delta, ProjectileManager manager) {
    	if (hurted) return;
    	
    	weapon.atacar(delta, getSpr().getBoundingRectangle(), getSpr().getRotation() + 90f, manager);
    	
    	if (weapon.getMunicion() == 0) {
    		weapon = new Melee();
    	}
    }
    
    private void borderBounce(float positionX, float positionY) {
    	if ((positionX) < 0 || 
    		(positionX + getSpr().getWidth()) > Gdx.graphics.getWidth()) {
    		xVel *= -1;
    	}
        if ((positionY) < 0 || 
        	(positionY + getSpr().getHeight()) > Gdx.graphics.getHeight()) {
        	yVel *= -1;
        }
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
	
	public int getLife() {
		return life;
	}

	public boolean hasWeapon() {
		if (weapon.getMunicion() > 0) return true;
		return false;
	}
}
