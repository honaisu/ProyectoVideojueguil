package armas.proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;
import hitboxes.BallHitbox;
import hitboxes.Hitbox;

//Clase que representa una balla dentro del juego

public class Bullet extends Projectile {
	// velocidad de la bala eje x
	private float xSpeed;
	// velocidad de la bala eje y
	private float ySpeed;
	// ver si la bala esta destruida
	private boolean destroyed = false;

	public Bullet(float x, float y, float width, float rotation, float speed) {
		super(new BallHitbox(x, y, width, rotation));
		float radians = (float) Math.toRadians(rotation);

        // Calcular velocidad en X e Y según el ángulo y la velocidad dadas
        this.xSpeed = (float) Math.cos(radians) * speed;
        this.ySpeed = (float) Math.sin(radians) * speed;
	}
	
    public Bullet(float x, float y, float rotacion, float speed, Texture tx) {
    	super(new BallHitbox(x, y, (int) (tx.getWidth() / 2)));

    	// Ajusta el origen y la rotación del sprite dentro del hitbox
        this.getHitbox().getSpr().setOriginCenter();
        this.getHitbox().getSpr().setRotation(rotacion);

        // Convertir rotación a radianes para calcular la dirección del movimiento
        float radians = (float) Math.toRadians(rotacion + 90);

        // Calcular velocidad en X e Y según el ángulo y la velocidad dadas
        this.xSpeed = (float) Math.cos(radians) * speed;
        this.ySpeed = (float) Math.sin(radians) * speed;
    }

    //movimiento de la bala y colision con el borde de la ventana
    public void update(float delta, float x, float y, float rotation) {
    	// Mueve el sprite del hitbox
        Sprite spr = getHitbox().getSpr();
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);

        // Comprueba si la bala ha salido de los límites de la pantalla
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }
    }

    @Override
    public void update(float delta, Rectangle r, float rotation) {
        // Mueve el sprite del hitbox
        Sprite spr = getHitbox().getSpr();
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);

        // Comprueba si la bala ha salido de los límites de la pantalla
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroy();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
    	getHitbox().draw(batch);
    }

    //colision con un asteroide
    public boolean checkCollision(Hitbox b2) {
    	if (getHitbox().checkCollision(b2)) {
            destroy();
            return true;
        }
        return false;
    }

    public boolean isDestroyed() { return destroyed; }
}
