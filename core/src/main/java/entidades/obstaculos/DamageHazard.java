package entidades.obstaculos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import entidades.Entity;
import enumeradores.recursos.EObstacleSkin;
//import factories.SpriteFactory;

public class DamageHazard extends Entity {

    public enum DamageType {
        SPIKE,  // Pua (daño de una)
        PUDDLE  // Charco (daño "constante" y silencioso)
    }

    private DamageType type;
    private int damageAmount;

    public DamageHazard(float x, float y, EObstacleSkin skin, DamageType type, int damageAmount) {
        // Usa la skin específica que le pasa la fábrica
    	super(new Vector2(x, y), skin); //pero ahora adaptado al vector 2 u_u
        
        this.type = type;
        this.damageAmount = damageAmount;
        
        // getSpr().setAlpha(0.8f); // sirve para poner trsparente los obstaculos en caso de
        this.sprite.setPosition(x, y);
    }

    // Getters para que el Player pueda preguntar
    public DamageType getDamageType() { return type; }
    public int getDamage() { return damageAmount; }

    @Override
    public void update(float delta) { /* No se mueve */ }
    
    @Override
	public void draw(SpriteBatch batch) {
 		super.draw(batch);
	}
}