package armas.proyectiles;

import entidades.Player;
import enumeradores.recursos.EProjectileType;
import factories.SpriteFactory;

public class Swing extends Projectile {
	// tiempo que dura activo el golpe
    private float duracion = 0.25f;     
    // Contador
    private float tiempoActivo = 0f;  	
    // para que desaparezca el swing	
    
    private float radio;

    public Swing(float x, float y, float rotation, float radio, Player p) {
    	super(x, y, SpriteFactory.create(EProjectileType.SWING, 96, 64), p);
    	
    	getSpr().setBounds(x, y, 120, 60);
        getSpr().setOriginCenter();
        getSpr().setRotation(rotation-90);
        getSpr().setPosition(x - getSpr().getWidth() / 2, y - getSpr().getHeight() / 2);
        
    	this.radio = radio;
    }
    
    @Override
    public void update(float delta, Player p) {
        float centerX = p.getSpr().getBoundingRectangle().getX() + p.getSpr().getBoundingRectangle().getWidth() / 2;
        float centerY = p.getSpr().getBoundingRectangle().getY() + p.getSpr().getBoundingRectangle().getHeight() / 2;
    	
    	
        mover(centerX, centerY, p.getRotation(), radio);
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
