package entidades.proyectiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import entidades.Player;
import enumeradores.recursos.EProjectileType;

public class Swing extends Projectile {
	// tiempo que dura activo el golpe
	private float duracion = 0.25f;
	// Contador
	private float tiempoActivo = 0f;
	// para que desaparezca el swing

	private float radio;

	public Swing(Rectangle r, int damage, float radio, float rotation, float duration, boolean piercing) {
		super(new Vector2(r.x, r.y), EProjectileType.SWING, damage, piercing);
		this.duracion = duration;
		this.radio = radio;

		Projectile.calcularMuzzle(position, r, rotation, piercing);

		sprite.setRotation(rotation);
		sprite.setPosition(position.x + sprite.getOriginX(), position.y + sprite.getOriginY());
	}
	
	public Swing(Rectangle r, EProjectileType type, int damage, float radio, float rotation, float duration, boolean piercing) {
		super(new Vector2(r.x, r.y), type, damage, piercing);
		this.duracion = duration;
		this.radio = radio;

		Projectile.calcularMuzzle(position, r, rotation, piercing);

		sprite.setRotation(rotation);
		sprite.setPosition(position.x + sprite.getOriginX(), position.y + sprite.getOriginY());
	}

	public Swing(float muzzle[], float radio, Player p, float width, float height, float duracion,
			boolean isBeam, boolean piercing) {

		super(muzzle[0], muzzle[1], EProjectileType.SWING, p.getWeapon().getStats().getDamage(), piercing);

		this.duracion = duracion;
		this.radio = radio;

		sprite.setBounds(muzzle[0], muzzle[1], width, height); // Usa el ancho y alto recibidos

		if (isBeam) {// Lasers
			sprite.setOrigin(width / 2f, radio);
		} else {// Melee
			sprite.setOriginCenter();
		}

		sprite.setRotation(muzzle[2] - 90);
		sprite.setPosition(muzzle[0] - getSprite().getOriginX(), muzzle[1] - getSprite().getOriginY());
	}

	@Override
	public void update(float delta, Player p) {
		float rotation = calcularMuzzle(getPosition(), false, p);

		mover(position, rotation - 90, radio);
		if (isDestroyed())
			return;

		tiempoActivo += delta;
		if (tiempoActivo > duracion) {
			destroy();
		}
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
}
