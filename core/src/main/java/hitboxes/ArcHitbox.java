package hitboxes;

import com.badlogic.gdx.graphics.g2d.Sprite;

import managers.AssetManager;

//Clase que se encarga de crear una colision en forma de semicirculo
public class ArcHitbox extends Hitbox{
    public ArcHitbox(float x, float y, float rotacion) {
    	super(x,y, new Sprite(AssetManager.getInstancia().getSwingHitboxTexture()));
    	
    	getSpr().setBounds(x, y, 120, 60);
        getSpr().setOriginCenter();
        getSpr().setRotation(rotacion);
        getSpr().setPosition(x - getSpr().getWidth() / 2, y - getSpr().getHeight() / 2);
        
    }
	
	public void setPos(float x, float y){
		setX(x);
		setY(y);
		getSpr().setPosition(x, y);
	}
}