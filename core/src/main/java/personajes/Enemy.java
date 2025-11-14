package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.BallHitbox;
import managers.AssetManager;

public class Enemy extends BallHitbox {
  // Velocidad local 
  //mio
  private float vx = 0f, vy = 0f;
  //benjoid
  private float rareDrop;
  private int healthPoints;
  private float damage;

  public Enemy(float x, float y, float size, float rareDrop, int healthPoints, int damage) {
    super(x, y, size, 0f);
    // Conservar bounds que puso BallHitbox, solo cambiamos la textura
    float bx = getSpr().getX(), by = getSpr().getY();
    float bw = getSpr().getWidth(), bh = getSpr().getHeight();
    setSpr(new Sprite(AssetManager.getInstancia().getEnemyTexture())); 
    getSpr().setBounds(bx, by, bw, bh);
    getSpr().setOriginCenter();
    
    //agregado de benjoid
    this.rareDrop = rareDrop;
    this.healthPoints = healthPoints;
    this.damage = damage;
  }

  public void setVelocityLocal(float vx, float vy) { this.vx = vx; this.vy = vy; }
  public void setPolarVelocityLocal(float speed, float angleDeg) {
    float rad = (float) Math.toRadians(angleDeg);
    setVelocityLocal((float) Math.cos(rad) * speed, (float) Math.sin(rad) * speed);
  }

  @Override
  public void update() {
    float dt = Gdx.graphics.getDeltaTime();

    // Mover con velocidad local y rebotar contra los bordes
    //mio
    float nx = getSpr().getX() + vx * dt;
    float ny = getSpr().getY() + vy * dt;

    float w = getSpr().getWidth(), h = getSpr().getHeight();
    int sw = Gdx.graphics.getWidth(), sh = Gdx.graphics.getHeight();

    if (nx < 0f) { nx = 0f; vx = -vx; }
    else if (nx + w > sw) { nx = sw - w; vx = -vx; }

    if (ny < 0f) { ny = 0f; vy = -vy; }
    else if (ny + h > sh) { ny = sh - h; vy = -vy; }

    getSpr().setX(nx);
    getSpr().setY(ny);

    // Lógica adicional de BallHitbox 
    super.update();
  }

	
	/**
	 * Dibuja al enemigo. 
	 * Player tiene una lógica compleja de animación, pero Enemy puede ser simple.
	 */
	@Override
	public void draw(SpriteBatch batch) {
		getSpr().draw(batch);
	}
	
	/**
	 * Reduce la vida del enemigo al recibir daño.
	 */
	public void takeDamage(int amount) {
		this.healthPoints -= amount;
	}
	
	/**
	 * Verifica si el enemigo ha sido derrotado.
	 */
	public boolean isDead() {
		return this.healthPoints <= 0;
	}

	// --- Getters ---
	
	public float getDamage() {
		return damage;
	}
	
	public float getRareDropProbability() {
		return rareDrop;
	}

}
