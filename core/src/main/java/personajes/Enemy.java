package personajes;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import hitboxes.BallHitbox;
import managers.AssetManager;

public class Enemy extends BallHitbox {
  // Velocidad local (no modifica BallHitbox internamente)
  private float vx = 0f, vy = 0f;

  public Enemy(float x, float y, float size) {
    super(x, y, size, 0f);
    // Conservar bounds que puso BallHitbox y solo cambiar la textura
    float bx = getSpr().getX(), by = getSpr().getY();
    float bw = getSpr().getWidth(), bh = getSpr().getHeight();
    setSpr(new Sprite(AssetManager.getInstancia().getAsteroideTexture()));
    getSpr().setBounds(bx, by, bw, bh);
    getSpr().setOriginCenter();
  }

  // Setters de velocidad local
  public void setVelocityLocal(float vx, float vy) { this.vx = vx; this.vy = vy; }
  public void setPolarVelocityLocal(float speed, float angleDeg) {
    float rad = (float) Math.toRadians(angleDeg);
    setVelocityLocal((float) Math.cos(rad) * speed, (float) Math.sin(rad) * speed);
  }

  @Override
  public void update() {
    float dt = com.badlogic.gdx.Gdx.graphics.getDeltaTime();

    // mover por velocidad local
    float nx = getSpr().getX() + vx * dt;
    float ny = getSpr().getY() + vy * dt;

    // límites considerando el tamaño del sprite
    float w = getSpr().getWidth();
    float h = getSpr().getHeight();
    int sw = com.badlogic.gdx.Gdx.graphics.getWidth();
    int sh = com.badlogic.gdx.Gdx.graphics.getHeight();

    // rebote horizontal
    if (nx < 0f) {
      nx = 0f;
      vx = -vx; // invierte dirección
    } else if (nx + w > sw) {
      nx = sw - w;
      vx = -vx;
    }

    // rebote vertical
    if (ny < 0f) {
      ny = 0f;
      vy = -vy;
    } else if (ny + h > sh) {
      ny = sh - h;
      vy = -vy;
    }

    // aplicar posición corregida
    getSpr().setX(nx);
    getSpr().setY(ny);

    // luego deja que BallHitbox haga su lógica (dibujos, bounds extra)
    super.update();
  }
}
