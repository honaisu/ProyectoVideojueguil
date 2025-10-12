package armas.proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.ArcHitbox;
import hitboxes.Ball2;
import personajes.Jugador;

public class Swing {
    private ArcHitbox hitbox;
    private float duracion = 0.25f;      // Tiempo que dura activo el golpe
    private float tiempoActivo = 0f;  // Contador
    private boolean destroyed = false;
    
    private Jugador nave;
    private float xVel;
    private float yVel;

    public Swing(float x, float y, float radio, Jugador nave){//,float angulo , float xVel, float yVel) {
        this.hitbox = new ArcHitbox(x, y, radio, nave.getRotacion(), new Texture(Gdx.files.internal("semicirculo2.png"))); // Puedes pasar textura si quieres ver debug
        
        this.nave = nave;
        this.xVel = nave.getxVel();
        this.yVel = nave.getyVel();
    }

    public void update(float delta) {
    	
    	hitbox.mover(xVel,yVel);
    	
    	xVel = nave.getxVel();
    	yVel = nave.getyVel();
        
        tiempoActivo += delta;
        if (tiempoActivo > duracion) {
            destroyed = true;
        }
    }

    public void draw(SpriteBatch batch) {
        hitbox.draw(batch); // Solo si visible = true
    }

    public boolean checkCollision(Ball2 b2) {
        return hitbox.colisionaCon(b2);
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
