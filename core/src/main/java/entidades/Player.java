package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import armas.*;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EPlayerSkin;
import factories.AnimationFactory;
import factories.SpriteFactory;
import managers.ProjectileManager;
import managers.assets.AssetManager;


public class Player extends Entity {
	private final float MAX_VELOCITY = 10.0f;
	// Estado básico 
	private int score = 0;
    private int round = 1;
    private int life = 100;

	//fisica de si
	private float xVel = 0f;
	private float yVel = 0f;
	private float rotation = 0f;
	
	//Visual y audio
	
	private Sound hurtSound = AssetManager.getInstancia().getSound(EGameSound.HURT);
	private Animation<TextureRegion> animation;
	private float stateTime = 0f;
		
	//Lógica de Daño merge
	private boolean hurted = false;
	private int hurtTime;           //TODO
	private float iFrames = 0f;
	  
	//Arma inicial o por defecto
	private Weapon weapon = new LaserCannon();
	
	public Player(float x, float y) {
		// crea el player con skin original nomás
		this(x, y, EPlayerSkin.ORIGINAL);
	}
	
	public Player(float x, float y, EPlayerSkin skin) {
		super(x, y, SpriteFactory.create(skin));
		animation = AnimationFactory.createPlayer(skin);

    	getSpr().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    	getSpr().setOriginCenter();
	}
	 
	@Override
	public void update(float delta) {
	    boolean isMoving = (Math.abs(xVel) > 0.1f || Math.abs(yVel) > 0.1f);
	    if (isMoving) stateTime += delta;

	    // Lógica de daño
	    if (hurted) {
	      hurtTime--;
	      if (hurtTime <= 0) hurted = false;
	    }
	    if (iFrames > 0f) iFrames -= delta;

	    xVel = MathUtils.clamp(xVel, -MAX_VELOCITY, MAX_VELOCITY);
	    yVel = MathUtils.clamp(yVel, -MAX_VELOCITY, MAX_VELOCITY);

	    float positionX = getSpr().getX() + xVel;
	    float positionY = getSpr().getY() + yVel;

	    // Logica de rebote 
	    borderBounce(positionX, positionY);
	    
	    getSpr().setPosition(positionX, positionY);
	    getSpr().setRotation(rotation);

	    weapon.actualizar(delta);
	  }

 /*TODO
	public void draw(SpriteBatch batch) {
	    TextureRegion currentFrame;
	    boolean isMoving = (Math.abs(xVel) > 0.1f || Math.abs(yVel) > 0.1f);

	    currentFrame = isMoving ? animation.getKeyFrame(stateTime, true)
	                            : animation.getKeyFrame(0f, true);

	    if (hurted) {
	      if ((hurtTime % 10) < 5) return; // Parpadeo
	    }

	    batch.draw(
	      currentFrame,
	      getSpr().getX(), getSpr().getY(),
	      getSpr().getOriginX(), getSpr().getOriginY(),
	      getSpr().getWidth(), getSpr().getHeight(),
	      getSpr().getScaleX(), getSpr().getScaleY(),
	      getSpr().getRotation()
	    );
	  }


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
	*/
	@Override
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
	
	//TODO revisar tema de la vida//
	public void takeDamage(int damage) {
		// Solo recibe daño si no está herido (invulnerable)
		if (hurted) return;
		
		this.life -= damage; //
		this.hurted = true;
		this.hurtTime = 120; // Invulnerable por 120 frames (aprox 2 segundos)
		
		if (hurtSound != null) {
			hurtSound.play();
		}
	}
	
	public boolean isHurt() {
		return hurted;
	}
	
	public boolean isDead() {
		return life <= 0;
	}
	//TODO revisar tema de la vida//

	public void rotate(float amount) {
        this.rotation += amount;
    }

    public void accelerate(float amount) {
        xVel -= (float) Math.sin(Math.toRadians(rotation)) * amount;
        yVel += (float) Math.cos(Math.toRadians(rotation)) * amount;
    }

    public void applyFriction(float friction) {
        xVel *= friction;
        yVel *= friction;
    }
    
    public void shoot(float delta, ProjectileManager manager) {    	
    	weapon.atacar(delta, this, manager);
    	
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
	
	public void setWeapon(Weapon newWeapon) {
        this.weapon = newWeapon;
    }
/*
  private void borderBounce(float positionX, float positionY) {
    // Rebote horizontal
    if (positionX < 0f || (positionX + getSpr().getWidth()) > Gdx.graphics.getWidth()) {
      xVel *= -1f;
      // Corrige posición para no salir
      if (positionX < 0f) positionX = 0f;
      else positionX = Gdx.graphics.getWidth() - getSpr().getWidth();
    }
    // Rebote vertical
    if (positionY < 0f || (positionY + getSpr().getHeight()) > Gdx.graphics.getHeight()) {
      yVel *= -1f;
      if (positionY < 0f) positionY = 0f;
      else positionY = Gdx.graphics.getHeight() - getSpr().getHeight();
    }
    // Aplica corrección local
    getSpr().setPosition(positionX, positionY);
  }
  */
/*
  // Llamado por el CollisionManager al detectar choque con Enemy
  public void onHitByEnemy(personajes.Enemy e) {
    if (iFrames > 0f) return;   // invulnerable
    damage(1);
    iFrames = 0.5f;             // medio segundo de invulnerabilidad
    hurted = true;
    hurtTime = 30;              // frames de parpadeo, ajusta si usas delta
    if (hurtSound != null) hurtSound.play();
  }*/

  public void damage(int amount) {
    life -= amount;
    if (life < 0) life = 0;
  }

  // Getters básicos
  public float getRotation() { return rotation; }
  public Weapon getWeapon() { return weapon; }
  public int getRound() { return round; }
  public int getScore() { return score; }
  public int getLife() { return life; }

  public boolean hasWeapon() {
    return weapon.getMunicion() > 0;
  }
}
