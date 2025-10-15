package hitboxes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//Clase que se encarga de crear una colision en forma de semicirculo
public class ArcHitbox {
    private float x;				// posicion en eje x
    private float y;				// posicion en eje y
    private float radio;			// radio del semicirculo
    private float rotacion; 		// rotacion del semicirculo
    private Sprite spr;				// sprite del semicirculo
    private boolean visible = true;	// visibilidad del semicirculo

    public ArcHitbox(float x, float y, float radio, float rotacion, Texture tx) {
        this.x = x;
        this.y = y;
        this.radio = radio;
        this.rotacion = rotacion; 
        
        //inicializa el sprite
        spr = new Sprite(tx);
        spr.setSize(radio * 2, radio * 2);
        spr.setOriginCenter();
        spr.setPosition(x - radio, y - radio);
        spr.setRotation(rotacion);
    }

    public void draw(SpriteBatch batch) {
        if (visible) {
            spr.draw(batch);
        }
    }
    
    //verifica la colision dentro del area del semicirculo
    public boolean colisionaCon(BallHitbox b2) {
        Rectangle rect = b2.getArea();

        float cx = rect.x + rect.width / 2;
        float cy = rect.y + rect.height / 2;

        float dx = cx - x;
        float dy = cy - y;
        float distancia = (float) Math.sqrt(dx * dx + dy * dy);

        
        if (distancia > radio) return false;

        
        float anguloObjetivo = (float) Math.toDegrees(Math.atan2(dy, dx));
        if (anguloObjetivo < 0) anguloObjetivo += 360;

        
        float anguloAlineado = rotacion + 90;

        float inicio = anguloAlineado - 90;
        float fin = anguloAlineado + 90;

        
        if (inicio < 0) inicio += 360;
        if (fin > 360) fin -= 360;

        if (inicio < fin) {
            return anguloObjetivo >= inicio && anguloObjetivo <= fin;
        } else {
            return anguloObjetivo >= inicio || anguloObjetivo <= fin;
        }
    }

    public void setVisible(boolean visible) { this.visible = visible; }

    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
        spr.setPosition(x - radio, y - radio);
    }

    public void setAngulo(float angulo){
        this.rotacion = angulo;
        spr.setRotation(angulo);
    }

	public Sprite getSpr() {
		return spr;
	}
	
	//actualiza el movimiento del sprite con el movimiento de la nave
	public void mover(float xVel, float yVel, float rot) {
		spr.setPosition(spr.getX()+xVel, spr.getY()+yVel);
		spr.setRotation(rot);
		
		this.x = spr.getX() + radio;  // Centro X
	    this.y = spr.getY() + radio;  // Centro Y
	    this.rotacion = rot;

	}
}