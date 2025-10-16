package armas.proyectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.ArcHitbox;
import hitboxes.BallHitbox;
import logica.AssetsLoader;
import personajes.Jugador;

public class Swing {
    private ArcHitbox hitbox;			// la colision es de tipo arqueada
    
    private float duracion = 0.25f;     // tiempo que dura activo el golpe
    private float tiempoActivo = 0f;  	// Contador
    private boolean destroyed = false;	// para que desaparezca el swing
    
    private Jugador nave;
    private float rot;
    private float radio;

    public Swing(float x, float y, Jugador nave){
    	
    	this.radio = 40 + nave.spr.getHeight() / 2; 					// distancia del ataque
        this.hitbox = new ArcHitbox(
        		x, y,													// posicion del ataque
        		nave.getRotacion(),										// la rotacion con respecto de la nave
        		AssetsLoader.getInstancia().getSwingHitboxTexture()); 	// textura del ataqque
        
        this.nave = nave;
    }

    public void update(float delta) {
    	
    	float centerX = nave.spr.getX() + nave.spr.getWidth() / 2;
        float centerY = nave.spr.getY() + nave.spr.getHeight() / 2;
    	
    	rot = nave.getRotacion();
    	
    	//actualiza el movimiento de la colision con el movimiento de la nave
    	hitbox.mover(centerX, centerY, rot, radio);
    	
        //un breve periodod de tiempo hasta que se destruye automaticamente
        tiempoActivo += delta;
        if (tiempoActivo > duracion) {
            destroyed = true;
        }
    }

    
    public void draw(SpriteBatch batch) {
        hitbox.draw(batch);
    }

    public boolean checkCollision(BallHitbox b2) {
        return hitbox.checkCollision(b2);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

	public ArcHitbox getHitbox() {
		return hitbox;
	}
}
