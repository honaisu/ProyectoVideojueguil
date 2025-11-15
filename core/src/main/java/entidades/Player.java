package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import armas.HeavyMachineGun;
import armas.Melee;
import armas.Weapon;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EPlayerSkin;
import factories.AnimationFactory;
import factories.SpriteFactory;
import logica.AnimationHandler;
import managers.ProjectileManager;
import managers.assets.AssetManager;


public class Player extends Entity {
	private final float MAX_VELOCITY = 250.0f;
	// Estado básico
	private int score = 0;
    private int round = 1;
    private int life = 100; // ANTES: lives

	//fisica de si
    private Vector2 velocity = new Vector2(0, 0);
	private float rotation = 90f;
	
	//Visual y audio
	private Sound hurtSound = AssetManager.getInstancia().getSound(EGameSound.HURT);
	private AnimationHandler animation;
		
	//Lógica de Daño merge
	private boolean hurted = false; // benjoid
	private int hurtTime;           //TODO
	private float iFrames = 0f;     // si
	
	boolean isMoving = false;
	  
	//Arma inicial o por defecto
	private Weapon weapon = new HeavyMachineGun();
	
	public Player(float x, float y) {
		// crea el player con skin original nomás
		this(x, y, EPlayerSkin.ORIGINAL);
	}
	
	public Player(float x, float y, EPlayerSkin skin) {
		super(x, y, SpriteFactory.create(skin));
		getPosition().set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		
		getSpr().scale(1f);
    	getSpr().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    	getSpr().setRotation(rotation);
    	getSpr().setOriginCenter();
    	
    	animation = new AnimationHandler(AnimationFactory.createPlayer(skin), getSpr());
	}
	 
	@Override
	public void update(float delta) {
		// el 0.1f es un margen de error
	    isMoving = !velocity.isZero(0.1f);
		if (isMoving) animation.updateStateTime(delta);;

	    // Lógica de daño fusionada //o queda bien o se jode todo
	    if (hurted) {
	      hurtTime--;
	      if (hurtTime <= 0) hurted = false;
	    }
	    if (iFrames > 0f) iFrames -= delta;
	    
	    velocity.limit(MAX_VELOCITY);
	    
	    Vector2 position = getPosition().cpy().add(velocity.cpy().scl(delta));
	    
	    // Logica de rebote
	    borderBounce(position);
	    getPosition().set(position);
	    
	    getSpr().setPosition(position.x, position.y);
	    getSpr().setRotation(rotation);

	    weapon.actualizar(delta);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
        // Si está herido, se podría aplicar un efecto de parpadeo con el color del batch
        if (hurted && hurtTime % 10 < 5) return;
        
        animation.updateSprite(getSpr());
        animation.handle(batch, isMoving, true);
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
    	Vector2 acceleration = new Vector2(1, 0);
    	acceleration.setAngleDeg(rotation);
    	acceleration.scl(amount);
    	
    	velocity.add(acceleration);
    }

    public void applyFriction(float friction) {
    	velocity.scl(friction);
    }
    
    public void shoot(float delta, ProjectileManager manager) {    	
    	weapon.atacar(delta, this, manager);
    	
    	if (weapon.getMunicion() == 0) 
    		weapon = new Melee();
    }
    
    private void borderBounce(Vector2 position) {
    	if (position.x < 0) {
    		// Invierte velocidad X
    		velocity.x *= -1; 
    		// Fija la posición al borde
    		position.x = 0;        
    	}
        else if ((position.x + getSpr().getWidth()) > Gdx.graphics.getWidth()) {
        	velocity.x *= -1;
    		position.x = Gdx.graphics.getWidth() - getSpr().getWidth();
    	}
    	
    	if (position.y < 0) {
        	velocity.y *= -1; // Invierte velocidad Y
        	position.y = 0;        // Fija la posición al borde
        }
        else if ((position.y + getSpr().getHeight()) > Gdx.graphics.getHeight()) {
        	velocity.y *= -1;
        	position.y = Gdx.graphics.getHeight() - getSpr().getHeight();
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
