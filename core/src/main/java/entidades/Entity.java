package entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase abstracta que define una <i>entidad</i> dentro de nuestro juego.
 * <p>
 * Cada entidad va a ser como un "objeto" que existe en el plano (pantalla),
 * veáse ejemplos como Jugador, Bala, Enemigo...
 */
public abstract class Entity {
	private Vector2 position;
	private Sprite sprite;
	private int totalHp;
	private int hp;

	private boolean destroyed = false;

	public Entity(float x, float y) {
		position = new Vector2(x, y);
	}

	public Entity(float x, float y, Sprite sprite) {
		this(x, y);
		this.sprite = sprite;
	}

	public Entity() {

	}

	public void mover(Vector2 center, float rotation, float radio) {
		Vector2 offset = new Vector2(radio, 0);
		offset.setAngleDeg(rotation + 90);
		Vector2 newPosition = center.add(offset);

		sprite.setPosition(newPosition.x - sprite.getWidth() / 2, newPosition.y - sprite.getHeight() / 2);
		sprite.setRotation(rotation);
	}

	public void mover(float centerX, float centerY, float rotation, float radio) {
		this.mover(new Vector2(centerX, centerY), rotation, radio);
	}

	/**
	 * Verifica la colision de un objeto de tipo BallHitbox
	 * 
	 * @param BallHitbox -> puede ser un asteroide
	 * @return true en caso de colisionar con un BallHitbox, false en caso contrario
	 */
	public boolean checkCollision(Entity entity) {
		return Intersector.overlapConvexPolygons(getRotatedPolygon(sprite), getRotatedPolygon(entity.getSpr()));
	}

	public void draw(SpriteBatch batch) {
		this.sprite.draw(batch);
	}

	/**
	 * Hace un poligono auxiliar para que la Hitbox al rotar cumpla con su tamaño
	 * 
	 * @param Sprite del objeto
	 * @return
	 */
	public Polygon getRotatedPolygon(Sprite spr) {
		float[] vertices = new float[] { 0, 0, spr.getWidth(), 0, spr.getWidth(), spr.getHeight(), 0, spr.getHeight() };

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

	public void destroy() {
		destroyed = true;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public float getX() {
		return position.x;
	}

	public void setX(float x) {
		position.x = x;
	}

	public float getY() {
		return position.y;
	}

	public void setY(float y) {
		position.y = y;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Sprite getSpr() {
		return sprite;
	}

	public void setSpr(Sprite spr) {
		this.sprite = spr;
	}
}
