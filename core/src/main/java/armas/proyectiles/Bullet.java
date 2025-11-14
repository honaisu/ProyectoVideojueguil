package armas.proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entidades.Player;
import enumeradores.recursos.EProjectileType;
import factories.SpriteFactory;

//Clase que representa una balla dentro del juego

public class Bullet extends Projectile {
	// velocidad de la bala eje x
	private float xSpeed;
	// velocidad de la bala eje y
	private float ySpeed;

	public Bullet(float x, float y, float width, float rotation, float speed, Player p) {
		super(x, y, SpriteFactory.create(EProjectileType.BULLET_TEMPLATE), p);
		
		getSpr().setBounds(x, y, width, width);
        getSpr().setOriginCenter();
        getSpr().setRotation(rotation);

        // Calcular velocidad en X e Y según el ángulo y la velocidad dadas
		float radians = (float) Math.toRadians(rotation);
        this.xSpeed = (float) Math.cos(radians) * speed;
        this.ySpeed = (float) Math.sin(radians) * speed;
	}
	
    //movimiento de la bala y colision con el borde de la ventana
    @Override
    public void update(float delta, Player player) {
        if (isDestroyed()) return;

        // Mueve el sprite (que es ESTA entidad)
        Sprite spr = getSpr(); // getSpr() es heredado de Entity
        
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);

        // Comprueba límites
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroy();
        }
    }

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	} 

}
