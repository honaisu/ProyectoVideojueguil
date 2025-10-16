package hitboxes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public abstract class Hitbox {
	private float x;
	private float y;
	
	public Hitbox(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Verifica la colision de un objeto de tipo BallHitbox
	 * @param BallHitbox -> puede ser un asteroide
	 * @return true en caso de colisionar con un BallHitbox, false en caso contrario
	 */
	public abstract boolean checkCollision(BallHitbox b2);
	
	/**
	 * Hace un poligono auxiliar para que la Hitbox al rotarce cumpla con su tamaño
	 * @param Sprite del objeto
	 * @return
	 */
	public Polygon getRotatedPolygon(Sprite spr) {
	    float[] vertices = new float[]{
	        0, 0,
	        spr.getWidth(), 0,
	        spr.getWidth(), spr.getHeight(),
	        0, spr.getHeight()
	    };

	    Polygon p = new Polygon(vertices);
	    p.setOrigin(spr.getOriginX(), spr.getOriginY());
	    p.setPosition(spr.getX(), spr.getY());
	    p.setRotation(spr.getRotation());
	    return p;
	}

	public float getX() { return x; }
	public void setX(float x) {	this.x = x;	}
	
	public float getY() { return y;	}
	public void setY(float y) {	this.y = y;	}
	
}
