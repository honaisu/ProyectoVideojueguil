package armas.proyectiles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import hitboxes.ArcHitbox;
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
    
    //TODO borrar
    public ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Swing(float x, float y, Jugador nave){
    	
    	this.radio = 20 + nave.getSpr().getHeight() / 2; 					// distancia del ataque
        this.hitbox = new ArcHitbox(
        		x, y,													// posicion del ataque
        		nave.getRotacion(),										// la rotacion con respecto de la nave
        		AssetsLoader.getInstancia().getSwingHitboxTexture()); 	// textura del ataqque
        
        this.nave = nave;
    }

    public void update(float delta) {
    	
    	float centerX = nave.getSpr().getX() + nave.getSpr().getWidth() / 2;
        float centerY = nave.getSpr().getY() + nave.getSpr().getHeight() / 2;
    	
    	rot = nave.getRotacion();
    	
    	//actualiza el movimiento de la colision con el movimiento de la nave
    	hitbox.mover(centerX, centerY, rot, radio);
    	
        //un breve periodod de tiempo hasta que se destruye automaticamente
        tiempoActivo += delta;
        if (tiempoActivo > duracion) {
            destroyed = true;
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

	public ArcHitbox getHitbox() {
		return hitbox;
	}
}
