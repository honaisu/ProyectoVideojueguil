package armas.proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.BallHitbox;

//Clase que representa una balla dentro del juego

public class Bullet {

	private float xSpeed;					// velocidad de la bala eje x
	private float ySpeed;					// velocidad de la bala eje y
	private boolean destroyed = false;		// ver si la bala esta destruida
	private Sprite spr;						// sprite de la bala
	    
    public Bullet(float x, float y, float rotacion, float speed, Texture tx) {
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setRotation(rotacion);
    	
    	// Convertir rotación a radianes
        float radians = (float) Math.toRadians(rotacion+90);

        // Calcular velocidad en X e Y según el ángulo
        this.xSpeed = (float) Math.cos(radians) * speed;
        this.ySpeed = (float) Math.sin(radians) * speed;
    }
    
    //movimiento de la bala y colision con el borde de la ventana
    public void update() {
        spr.setPosition(spr.getX()+xSpeed, spr.getY()+ySpeed);
        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
            destroyed = true;
        }
        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
        	destroyed = true;
        }
        
    }
    
    public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    }
    
    //colision con un asteroide
    public boolean checkCollision(BallHitbox b2) {
        if(spr.getBoundingRectangle().overlaps(b2.getArea())){
        	// Se destruyen ambos
            this.destroyed = true;
            return true;

        }
        return false;
    }
    
    public boolean isDestroyed() {return destroyed;}

}
