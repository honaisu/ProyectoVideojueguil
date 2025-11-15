package entidades.proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entidades.Player;

//Clase que representa una balla dentro del juego

public class Bullet extends Projectile {
	
	private float xSpeed;
	private float ySpeed;
	
	private float lifespan = -1f;
	
	public Bullet(float x, float y, float width, float rotation, float speed, Player p, Sprite spr, boolean piercing) {
		super(x, y, spr, p, p.getWeapon().getDamage(), piercing);

		
		getSpr().setBounds(x, y, width, width);
        getSpr().setOriginCenter();
        getSpr().setRotation(rotation - 90);

        // Calcular velocidad en X e Y según el ángulo y la velocidad dadas
		float radians = (float) Math.toRadians(rotation);
        this.xSpeed = (float) Math.cos(radians) * speed;
        this.ySpeed = (float) Math.sin(radians) * speed;
	}
	
	//Para una explosión
	public Bullet(float x, float y, float width, float rotation, float speed, Player p, Sprite spr, boolean piercing, float lifespan) {
        this(x, y, width, rotation, speed, p, spr, piercing); 
        this.lifespan = lifespan; 
    }
	
    //movimiento de la bala y colision con el borde de la ventana
    @Override
    public void update(float delta, Player player) {
        if (isDestroyed()) return;
        
        
        if (lifespan > 0) {
            lifespan -= delta;
            if (lifespan <= 0) {
                destroy();
                return;
            }
        }

        Sprite spr = getSpr();
        
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);

        // Comprueba límites
        if(lifespan != -1f) {
        	if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
        			spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
        		destroy();
        	}        	
        }
    }

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	} 

}
