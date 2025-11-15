package entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

/**
 * Clase abstracta que define una <i>entidad</i> dentro de nuestro juego.
 * <p>
 * Cada entidad va a ser como un "objeto" que existe en el plano (pantalla),
 * veáse ejemplos como Jugador, Bala, Enemigo...
 */
public abstract class Entity {
	private float x;
	private float y;
	private Sprite sprite;
	
	private boolean destroyed = false;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Entity(float x, float y, Sprite sprite) {
		this(x, y);
		this.sprite = sprite;
	}
	
	public void mover(float centerX, float centerY, float rot, float radio) {
		float radians = (float) Math.toRadians(rot + 90);

	    float nuevoX = centerX + (float) Math.cos(radians) * radio;
	    float nuevoY = centerY + (float) Math.sin(radians) * radio;

	    //getSpr().setPosition(nuevoX - sprite.getWidth() / 2, nuevoY - sprite.getHeight() / 2);
	    getSpr().setPosition(nuevoX - getSpr().getOriginX(), nuevoY - getSpr().getOriginY());
	    getSpr().setRotation(rot);
	}
	
	/**
	 * Verifica la colision de un objeto de tipo BallHitbox
	 * @param BallHitbox -> puede ser un asteroide
	 * @return true en caso de colisionar con un BallHitbox, false en caso contrario
	 */
	public boolean checkCollision(Entity e) {
		return Intersector.overlapConvexPolygons(getRotatedPolygon(sprite), getRotatedPolygon(e.getSpr()));
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
	
	/**
     * Actualiza la lógica de la entidad
     */
    public abstract void update(float delta); 
    
    public void destroy() { destroyed = true; }
	public boolean isDestroyed() { return destroyed; }

	public float getX() { return x; }
	public void setX(float x) {	this.x = x;	}
	
	public float getY() { return y;	}
	public void setY(float y) {	this.y = y;	}

	public Sprite getSpr() { return sprite; }
	public void setSpr(Sprite spr) { this.sprite = spr; }
}
