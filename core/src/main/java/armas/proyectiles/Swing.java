package armas.proyectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;

import managers.AssetManager;
import personajes.Player;

public class Swing extends Projectile {
	// tiempo que dura activo el golpe
    private float duracion = 0.25f;
    // Contador
    private float tiempoActivo = 0f;
    // para que desaparezca el swing	
    
    private float radio;

    public Swing(float x, float y, float rotation, float radio, Player p) {
    	super(x, y, new Sprite(AssetManager.getInstancia().getSwingHitboxTexture()), p);
    	
    	getSpr().setBounds(x, y, 120, 60);
        getSpr().setOriginCenter();
        getSpr().setRotation(rotation-90);
        getSpr().setPosition(x - getSpr().getWidth() / 2, y - getSpr().getHeight() / 2);
        
    	this.radio = radio;
    }
    
    @Override
    public void update(float delta, Player p) {
    	float muzzle[] = calcularMuzzle(p, false);
    	
        mover(muzzle[0], muzzle[1], muzzle[2]-90, radio);
        if (isDestroyed()) return;

        tiempoActivo += delta;
        if (tiempoActivo > duracion) {
            destroy();
        }
    }
    //TODO
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
}
