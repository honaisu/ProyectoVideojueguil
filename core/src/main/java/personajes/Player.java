package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import armas.*;
import logica.AnimationFactory;
import logica.assets.AssetManager;
import logica.assets.SkinJugador;

public class Player extends Entity {
	private final float MAX_VELOCITY = 10.0f;
	// Estado básico 
	private int score = 0;
    private int round = 1;

	// Visual y audio
	public Sprite spr;
	private Sound hurtSound = AssetManager.getInstancia().getHurtSound();
	
	// ENCARGADOS DE ANIMACIÓN
	private Animation<TextureRegion> animation;
    private float stateTime = 0f;
    
	// Herido
	private boolean hurted = false;
	private int maxHurtTime = 50;
	private int hurtTime;
	
	// Armas
	private Arma weapon = new Melee();
	
	public Player() {
		super();
	    // Sprite del jugador
	    spr = SkinJugador.JUGADOR_ORIGINAL.crearSprite();
	    
		// IMPLEMENTACIÓN DE LA ANIMACIÓN
    	this.animation = AnimationFactory.createJugadorAnimation(SkinJugador.JUGADOR_ORIGINAL);
    	
    	spr.scale(1f);
    	spr.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    	spr.setOriginCenter();
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
        float positionX = spr.getX() + xVel;
    	float positionY = spr.getY() + yVel;
        this.borderBounce(positionX, positionY);

        // Aplicar velocidad a la posición
        spr.setPosition(positionX, positionY);
        spr.setRotation(rotation);

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
                return; // No dibujar en algunos frames para crear parpadeo
            }
        }

	    batch.draw(currentFrame,
                spr.getX(), spr.getY(),
                spr.getOriginX(), spr.getOriginY(),
                spr.getWidth(), spr.getHeight(),
                spr.getScaleX(), spr.getScaleY(),
                spr.getRotation());
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
    
    public void shoot(float delta) {
    	if (hurted) return;
    }
    
    private void borderBounce(float positionX, float positionY) {
    	if ((positionX) < 0 || 
    		(positionX + spr.getWidth()) > Gdx.graphics.getWidth()) {
    		xVel *= -1;
    	}
        if ((positionY) < 0 || 
        	(positionY + spr.getHeight()) > Gdx.graphics.getHeight()) {
        	yVel *= -1;
        }
    }
}
