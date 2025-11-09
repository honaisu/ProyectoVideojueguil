package armas.proyectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import hitboxes.ArcHitbox;

public class Swing extends Projectile {
	// tiempo que dura activo el golpe
    private float duracion = 0.25f;     
    // Contador
    private float tiempoActivo = 0f;  	
    // para que desaparezca el swing	
    
    private float radio;

    public Swing(float x, float y, float rotation, float radio) {
    	// la colision es de tipo arqueada
    	super(new ArcHitbox(x, y, rotation));
    	// distancia del ataque
    	this.radio = radio;
    }
    
    @Override
    public void update(float delta, Rectangle r, float rotation) {
    	float centerX = r.getX() + r.getWidth() / 2;
        float centerY = r.getY() + r.getHeight() / 2;
    	
    	//actualiza el movimiento de la colision con el movimiento de la nave
    	getHitbox().mover(centerX, centerY, rotation, radio);
        
        //un breve periodo de tiempo hasta que se destruye automaticamente
        tiempoActivo += delta;
        if (tiempoActivo > duracion) {
            destroy();
        }
    }
	@Override
	public void draw(SpriteBatch batch) {
		getHitbox().draw(batch);
	}
}
