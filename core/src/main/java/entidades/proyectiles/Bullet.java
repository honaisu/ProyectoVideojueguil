package entidades.proyectiles;

import entidades.Entity;
import entidades.Player;
import enumeradores.recursos.EProjectileType;

//Clase que representa una balla dentro del juego

public class Bullet extends Projectile {
	public Bullet(float x, float y, float width, float rotation, float speed, int damage, boolean piercing) {
		super(x, y, EProjectileType.HOLLOWPOINT, damage, piercing);

		sprite.setBounds(x, y, width, width);
		sprite.setOriginCenter();
		sprite.setRotation(rotation - 90);

		float radians = (float) Math.toRadians(rotation);
		velocity.scl(speed);
		velocity.setAngleRad(radians);
	}

	// movimiento de la bala y colision con el borde de la ventana
	@Override
	public void update(float delta, Player player) {
		if (destroyed) return;

		// Mueve el sprite (que es ESTA entidad)
		sprite.setPosition(sprite.getX() + velocity.x, sprite.getY() + velocity.y);
		
		// Comprueba límites
		if (!Entity.isInBounds(this)) destroy();
	}

	@Override
	public void update(float delta) {
		if (destroyed) return;

		// Mueve el sprite (que es ESTA entidad)
		sprite.setPosition(sprite.getX() + velocity.x, sprite.getY() + velocity.y);

		// Comprueba límites
		if (!Entity.isInBounds(this)) destroy();
	}
}
