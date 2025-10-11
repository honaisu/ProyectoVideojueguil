package hitboxes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ArcHitbox {

    private float x;
    private float y;
    private float radio;
    private float angulo; 
    private Sprite spr;
    private boolean visible = true;

    public ArcHitbox(float x, float y, float radio, float angulo, Texture tx) {
        this.x = x;
        this.y = y;
        this.radio = radio;
        this.angulo = angulo; 

        spr = new Sprite(tx);
        spr.setSize(radio * 2, radio * 2);
        spr.setOriginCenter();
        spr.setPosition(x - radio, y - radio);
        spr.setRotation(angulo);
    }

    public void draw(SpriteBatch batch) {
        if (visible) {
            spr.draw(batch);
        }
    }

    public boolean colisionaCon(Ball2 b2) {
        Rectangle rect = b2.getArea();

        float cx = rect.x + rect.width / 2;
        float cy = rect.y + rect.height / 2;

        float dx = cx - x;
        float dy = cy - y;
        float distancia = (float) Math.sqrt(dx * dx + dy * dy);

        
        if (distancia > radio) return false;

        
        float anguloObjetivo = (float) Math.toDegrees(Math.atan2(dy, dx));
        if (anguloObjetivo < 0) anguloObjetivo += 360;

        
        float anguloAlineado = angulo + 90; // Ajustamos 90 grados

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
        this.angulo = angulo;
        spr.setRotation(angulo);
    }

	public Sprite getSpr() {
		return spr;
	}
	
	public void mover(float xVel, float yVel) {
		spr.setPosition(spr.getX()+xVel, spr.getY()+yVel);

	}
}