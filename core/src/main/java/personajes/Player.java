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

  // Animación
  private Animation<TextureRegion> animation;
  private float stateTime = 0f;

  // Herido
  private boolean hurted = false;
  private int hurtTime;

  // Invulnerabilidad (i-frames) tras recibir daño
  private float iFrames = 0f; // en segundos

  // Armas
  private Weapon weapon = new HeavyMachineGun();

  public Player(float x, float y) {
    super(x, y, ESkinJugador.JUGADOR_ORIGINAL.crearSprite());

    this.life = 3;
    this.xVel = 0f;
    this.yVel = 0f;
    this.rotation = 0f;

    // Animación
    this.animation = AnimationFactory.createJugadorAnimation(ESkinJugador.JUGADOR_ORIGINAL);

    getSpr().setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
    getSpr().setOriginCenter();
  }

  public void update(float delta) {
    // Avanza animación solo si hay movimiento perceptible
    boolean isMoving = (Math.abs(xVel) > 0.1f || Math.abs(yVel) > 0.1f);
    if (isMoving) stateTime += delta;

    // Temporizadores de daño/iframes
    if (hurted) {
      hurtTime--;
      if (hurtTime <= 0) hurted = false;
    }
    if (iFrames > 0f) iFrames -= delta;

    // Límites de velocidad
    xVel = MathUtils.clamp(xVel, -MAX_VELOCITY, MAX_VELOCITY);
    yVel = MathUtils.clamp(yVel, -MAX_VELOCITY, MAX_VELOCITY);

    // Propuesta de nueva posición
    float positionX = getSpr().getX() + xVel;
    float positionY = getSpr().getY() + yVel;

    // Rebote en bordes
    borderBounce(positionX, positionY);

    // Aplicar posición y rotación
    getSpr().setPosition(positionX, positionY);
    getSpr().setRotation(rotation);

    // Actualizar arma
    weapon.actualizar(delta);
  }

  public void draw(SpriteBatch batch) {
    TextureRegion currentFrame;
    boolean isMoving = (Math.abs(xVel) > 0.1f || Math.abs(yVel) > 0.1f);

    currentFrame = isMoving ? animation.getKeyFrame(stateTime, true)
                            : animation.getKeyFrame(0f, true);

    if (hurted) {
      // Reproduce sonido una vez por “entrada” de estado herido
      if (hurtTime % 10 == 9 && hurtSound != null) hurtSound.play();
      // Parpadeo simple
      if ((hurtTime % 10) < 5) return;
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

  // Llamado por el CollisionManager al detectar choque con Enemy
  public void onHitByEnemy(personajes.Enemy e) {
    if (iFrames > 0f) return;   // invulnerable
    damage(1);
    iFrames = 0.5f;             // medio segundo de invulnerabilidad
    hurted = true;
    hurtTime = 30;              // frames de parpadeo, ajusta si usas delta
    if (hurtSound != null) hurtSound.play();
  }

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
