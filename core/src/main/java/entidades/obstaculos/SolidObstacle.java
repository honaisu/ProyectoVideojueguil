package entidades.obstaculos;

import com.badlogic.gdx.math.Vector2;
import entidades.Entity;
import enumeradores.recursos.texturas.EObstacleSkin;

public class SolidObstacle extends Entity {

    public SolidObstacle(float x, float y) {
        this(x, y, EObstacleSkin.BOX);
    }
    
    public SolidObstacle(float x, float y, EObstacleSkin skin) {
    	super(new Vector2(x, y), skin);
    	getSprite().setPosition(x, y);
    }

    @Override
    public void update(float delta) { /* No se mueve */ }
}