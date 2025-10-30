package armas.proyectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.ArcHitbox;
import interfaces.Proyectil;
import managers.AssetManager;
import personajes.Player;

public class Swing implements Proyectil {
    private ArcHitbox hitbox;			// la colision es de tipo arqueada
    
    private float duracion = 0.25f;     // tiempo que dura activo el golpe
    private float tiempoActivo = 0f;  	// Contador
    private boolean destroyed = false;	// para que desaparezca el swing
    
    private Player player;
    private float rot;
    private float radio;

    public Swing(float x, float y, Player player){
    	
    	this.radio = 20 + player.getSpr().getHeight() / 2; 					// distancia del ataque
        this.hitbox = new ArcHitbox(
        		x, y,													// posicion del ataque
        		player.getRotation(),										// la rotacion con respecto de la nave
        		AssetManager.getInstancia().getSwingHitboxTexture()); 	// textura del ataqque
        
        this.player = player;
    }

    @Override
    public void update(float delta) {
    	float centerX = player.getSpr().getX() + player.getSpr().getWidth() / 2;
        float centerY = player.getSpr().getY() + player.getSpr().getHeight() / 2;
    	
    	rot = player.getRotation();
    	
    	//actualiza el movimiento de la colision con el movimiento de la nave
    	hitbox.mover(centerX, centerY, rot, radio);
    	
        //stateTime += delta;
        
        //un breve periodod de tiempo hasta que se destruye automaticamente
        tiempoActivo += delta;
        if (tiempoActivo > duracion) {
            destroyed = true;
        }
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        /*
    	TextureRegion currentFrame = animacion.getKeyFrame(stateTime, false);
        
    	batch.draw(
                currentFrame,
                hitbox.getX(), 
                hitbox.getY()
            );*/
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

	public ArcHitbox getHitbox() {
		return hitbox;
	}
}
