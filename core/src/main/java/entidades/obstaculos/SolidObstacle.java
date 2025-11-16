package entidades.obstaculos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entidades.Entity;
import enumeradores.recursos.EObstacleSkin;
import factories.SpriteFactory;

public class SolidObstacle extends Entity {

    public SolidObstacle(float x, float y) {
        // Siempre usa la skin BLOCK // en vola poner uno fondo ma wonito (nachoid)
    	super(x, y, SpriteFactory.create(EObstacleSkin.BLOCK));
        
        getSpr().setPosition(x, y);
    }

    @Override
    public void update(float delta) { /* No se mueve */ }
    
    @Override
	public void draw(SpriteBatch batch) {
 		super.draw(batch);
	}
}