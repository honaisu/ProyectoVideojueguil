package armas.proyectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hitboxes.ArcHitbox;
import hitboxes.BallHitbox;
import logica.AnimationFactory;
import logica.assets.AssetManager;
import personajes.Jugador;

public class Swing {
    private ArcHitbox hitbox;			// la colision es de tipo arqueada
    private float duracion = 0.25f;     // tiempo que dura activo el golpe
    private float tiempoActivo = 0f;  	// Contador
    private boolean destroyed = false;	// para que desaparezca el swing
    
    private Jugador jugador;
    private float xVel;
    private float yVel;
    private float rot;

    public Swing(float x, float y, float radio, Jugador nave){
        this.hitbox = new ArcHitbox(
        		x, y,													// posicion del ataque
        		radio,													// radio del ataque
        		nave.getRotacion(),										// la rotacion con respecto de la nave
        		AssetManager.getInstancia().getSwingHitboxTexture()); 	// textura del ataqque
        
        this.jugador = nave;
    }

    public void update(float delta) {
    	xVel = jugador.getxVel();
    	yVel = jugador.getyVel();
    	rot = jugador.getRotacion();
    	
    	//actualiza el movimiento de la colision con el movimiento de la nave
    	hitbox.mover(xVel,yVel,rot);
    	
        stateTime += delta;
        
        //un breve periodod de tiempo hasta que se destruye automaticamente
        tiempoActivo += delta;
        if (tiempoActivo > duracion) {
            destroyed = true;
        }
    }

    
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animacion.getKeyFrame(stateTime, false);
        
    	batch.draw(
                currentFrame,
                hitbox.getX(), 
                hitbox.getY()
            );
    }

    public boolean checkCollision(BallHitbox b2) {
        return hitbox.colisionaCon(b2);
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
