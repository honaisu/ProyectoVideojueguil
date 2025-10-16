package hitboxes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;

//Clase que se encarga de crear una colision en forma de semicirculo
public class ArcHitbox extends Hitbox{
    private Sprite spr;				// sprite del ataque
    
    public ShapeRenderer shapeRenderer = new ShapeRenderer();

    public ArcHitbox(float x, float y, float rotacion, Texture tx) {
    	super(x,y);
    	
        spr = new Sprite(tx);
        spr.setBounds(x, y, 200, 100);
        spr.setOriginCenter();
        spr.setRotation(rotacion);
        spr.setPosition(x - spr.getWidth() / 2, y - spr.getHeight() / 2);
        
    }
    //@Override
    public boolean checkCollision(BallHitbox b2) {
    	return Intersector.overlapConvexPolygons(getRotatedPolygon(spr), getRotatedPolygon(b2.spr));
    }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
    
	//actualiza el movimiento del sprite con el movimiento de la nave
	public void mover(float centerX, float centerY, float rot, float radio) {
		float radians = (float) Math.toRadians(rot + 90);

	    float nuevoX = centerX + (float) Math.cos(radians) * radio;
	    float nuevoY = centerY + (float) Math.sin(radians) * radio;

	    spr.setPosition(nuevoX - spr.getWidth() / 2, nuevoY - spr.getHeight() / 2);
	    spr.setRotation(rot);
	}
	
	
	public Sprite getSpr() { return spr; }
	
	
	
	public void setPos(float x, float y){
		setX(x);
		setY(y);
		spr.setPosition(x, y);
	}
}