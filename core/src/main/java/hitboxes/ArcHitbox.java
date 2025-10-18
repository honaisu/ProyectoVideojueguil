package hitboxes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

//Clase que se encarga de crear una colision en forma de semicirculo
public class ArcHitbox extends Hitbox{

    public ArcHitbox(float x, float y, float rotacion, Texture tx) {
    	super(x,y);
    	
    	setSpr(new Sprite(tx));
        getSpr().setBounds(x, y, 120, 60);
        getSpr().setOriginCenter();
        getSpr().setRotation(rotacion);
        getSpr().setPosition(x - getSpr().getWidth() / 2, y - getSpr().getHeight() / 2);
        
    }
    
	//actualiza el movimiento del sprite con el movimiento de la nave
	public void mover(float centerX, float centerY, float rot, float radio) {
		float radians = (float) Math.toRadians(rot + 90);

	    float nuevoX = centerX + (float) Math.cos(radians) * radio;
	    float nuevoY = centerY + (float) Math.sin(radians) * radio;

	    getSpr().setPosition(nuevoX - getSpr().getWidth() / 2, nuevoY - getSpr().getHeight() / 2);
	    getSpr().setRotation(rot);
	}
	
	public void setPos(float x, float y){
		setX(x);
		setY(y);
		getSpr().setPosition(x, y);
	}
}