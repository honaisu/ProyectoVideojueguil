package hitboxes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

public abstract class Hitbox {
	private float x;
	private float y;
	private Sprite sprite;
	
	public Hitbox(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Hitbox(float x, float y, Sprite sprite) {
		this(x, y);
		this.sprite = sprite;
	}
	
	public void mover(float centerX, float centerY, float rot, float radio) {
		float radians = (float) Math.toRadians(rot + 90);

	    float nuevoX = centerX + (float) Math.cos(radians) * radio;
	    float nuevoY = centerY + (float) Math.sin(radians) * radio;

	    getSpr().setPosition(nuevoX - sprite.getWidth() / 2, nuevoY - sprite.getHeight() / 2);
	    getSpr().setRotation(rot);
	}
	
	/**
	 * Verifica la colision de un objeto de tipo BallHitbox
	 * @param BallHitbox -> puede ser un asteroide
	 * @return true en caso de colisionar con un BallHitbox, false en caso contrario
	 */
	public boolean checkCollision(Hitbox b2) {
		return Intersector.overlapConvexPolygons(getRotatedPolygon(sprite), getRotatedPolygon(b2.getSpr()));
	}
	
	public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
    }
	
	/**
	 * Hace un poligono auxiliar para que la Hitbox al rotar cumpla con su tamaño
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

	public Sprite getSpr() { return sprite; }
	public void setSpr(Sprite spr) { this.sprite = spr; }
}
