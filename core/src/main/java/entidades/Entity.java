package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import factories.SpriteFactory;
import interfaces.ITexture;

/**
 * Clase abstracta que define una <i>entidad</i> dentro de nuestro juego.
 * <p>
 * Cada entidad va a ser como un "objeto" que existe en el plano (pantalla),
 * veáse ejemplos como Jugador, Bala, Enemigo...
 */
public abstract class Entity {
	protected Sprite sprite;
	protected Vector2 velocity = new Vector2();
	protected Vector2 position;
	protected float rotation = 0f;

	protected boolean destroyed = false;

	public Entity(Vector2 position, ITexture asset) {
		this.position = position;
		this.sprite = SpriteFactory.create(asset);
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
	 * Verifica la colision con una entidad
	 */
	public boolean checkCollision(Entity entity) {
		return Intersector.overlapConvexPolygons(getRotatedPolygon(this.sprite), getRotatedPolygon(entity.getSprite()));
	}

	public void draw(SpriteBatch batch) {
		this.sprite.draw(batch);
	}

	/**
	 * Hace un poligono auxiliar para que la Hitbox al rotar cumpla con su tamaño
	 */
	public Polygon getRotatedPolygon(Sprite spr) {
		float[] vertices = { 0, 0, spr.getWidth(), 0, spr.getWidth(), spr.getHeight(), 0, spr.getHeight() };

		Polygon p = new Polygon(vertices);
		p.setOrigin(spr.getOriginX(), spr.getOriginY());
		p.setPosition(spr.getX(), spr.getY());
		p.setRotation(spr.getRotation());
		return p;
	}

	/**
	 * Método estático encargado de poder verificar si una entidad se encuentra
	 * dentro de la "pantalla", modificando sus stats si es necesario.
	 */
	public static boolean isInBounds(Entity entity) {
		float w = entity.getSprite().getWidth(), h = entity.getSprite().getHeight();
		float maxX = Gdx.graphics.getWidth() - w;
		float maxY = Gdx.graphics.getHeight() - h;
		
		Vector2 copy = entity.getPosition().cpy();
		Vector2 position = entity.getPosition();
		Vector2 velocity = entity.getVelocity();

		position.x = MathUtils.clamp(position.x, 0, maxX);
		if (position.x != copy.x) {
			velocity.x = -velocity.x;
			return false;			
		}

		position.y = MathUtils.clamp(position.y, 0, maxY);
		if (position.y != copy.y) {
			velocity.y = -velocity.y;			
			return false;
		}
		
		return true;
	}

	/**
	 * Método abstracto encargado de poder actualizar la lógica de la entidad.
	 */
	public abstract void update(float delta);

	public void destroy() {
		destroyed = true;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(float speed, float angleDeg) {
		this.velocity.set(speed, 0);
		this.velocity.setAngleDeg(angleDeg);
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
}
