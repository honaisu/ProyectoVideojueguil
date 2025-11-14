package armas.proyectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;

import entidades.Player;

public class Swing extends Projectile {
	// tiempo que dura activo el golpe
    private float duracion = 0.25f;
    // Contador
    private float tiempoActivo = 0f;
    // para que desaparezca el swing	
    
    private float radio;
    
    public Swing(float muzzle[], float radio, Player p, Sprite spr, 
            float width, float height, float duracion, boolean isBeam, boolean piercing) {
	
		super(muzzle[0], muzzle[1], spr, p, p.getWeapon().getDamage(), piercing); 
		
		this.duracion = duracion;
		this.radio = radio;
		
		getSpr().setBounds(muzzle[0], muzzle[1], width, height); // Usa el ancho y alto recibidos
	   
		if (isBeam){//Lasers
			getSpr().setOrigin(width / 2f, radio);
		} else{//Melee
	   		getSpr().setOriginCenter();
		}
		
	   	getSpr().setRotation(muzzle[2]-90);
	   	getSpr().setPosition(muzzle[0] - getSpr().getOriginX(), muzzle[1] - getSpr().getOriginY());
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
