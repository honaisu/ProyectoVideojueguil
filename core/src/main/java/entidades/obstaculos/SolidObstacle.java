package entidades.obstaculos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entidades.Entity;
import enumeradores.recursos.EObstacleSkin;
//import factories.SpriteFactory; ya no se ocupa creo

public class SolidObstacle extends Entity {

    public SolidObstacle(float x, float y) {
        // Siempre usa la skin BLOCK // en vola poner uno fondo ma wonito (nachoid)
    	super(new Vector2(x, y), EObstacleSkin.BLOCK);
        
    	this.sprite.setPosition(x, y);
    }

    @Override
    public void update(float delta) { /* No se mueve */ }
    
    @Override
	public void draw(SpriteBatch batch) {
 		super.draw(batch);
	}
}