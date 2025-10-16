package armas.proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import hitboxes.BallHitbox;

//Clase que representa una balla dentro del juego

public class Bullet {
	private BallHitbox hitbox;
	
	private float xSpeed;					// velocidad de la bala eje x
	private float ySpeed;					// velocidad de la bala eje y
	private boolean destroyed = false;		// ver si la bala esta destruida
	
	
	public ShapeRenderer shapeRenderer = new ShapeRenderer();;
	    
    public Bullet(float x, float y, float rotacion, float speed, Texture tx) {
    	
    	this.hitbox = new BallHitbox(x, y, (int) (tx.getWidth() / 2), 0, 0, tx);
    	
    	// Ajusta el origen y la rotación del sprite dentro del hitbox
        this.hitbox.getSpr().setOriginCenter();
        this.hitbox.getSpr().setRotation(rotacion);

        // Convertir rotación a radianes para calcular la dirección del movimiento
        float radians = (float) Math.toRadians(rotacion + 90);

        // Calcular velocidad en X e Y según el ángulo y la velocidad dadas
        this.xSpeed = (float) Math.cos(radians) * speed;
        this.ySpeed = (float) Math.sin(radians) * speed;
    	
    	/*
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setRotation(rotacion);
    	
    	// Convertir rotación a radianes
        float radians = (float) Math.toRadians(rotacion+90);

        // Calcular velocidad en X e Y según el ángulo
        this.xSpeed = (float) Math.cos(radians) * speed;
        this.ySpeed = (float) Math.sin(radians) * speed;
        */
    }
	
    //movimiento de la bala y colision con el borde de la ventana
    public void update() {
    	// Mueve el sprite del hitbox
        Sprite spr = hitbox.getSpr();
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);

        // Comprueba si la bala ha salido de los límites de la pantalla
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }
    	/*
        spr.setPosition(spr.getX()+xSpeed, spr.getY()+ySpeed);
        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
            destroyed = true;
        }
        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
        	destroyed = true;
        }
        */
    }
    
    public void draw(SpriteBatch batch) {
    	hitbox.draw(batch);
    }
    
    //colision con un asteroide
    public boolean checkCollision(BallHitbox b2) {
    	if (hitbox.checkCollision(b2)) {
            this.destroyed = true; // La bala se destruye al colisionar
            return true;
        }
        return false;
        
    	/*
        if(spr.getBoundingRectangle().overlaps(b2.getArea())){
        	// Se destruyen ambos
            this.destroyed = true;
            return true;

        }
        return false;
        */
    }
    
    public boolean isDestroyed() { return destroyed; }

	public BallHitbox getHitbox() {
		return hitbox;
	}
    
}
