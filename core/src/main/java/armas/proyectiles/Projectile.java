package armas.proyectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.Entity;
import personajes.Player;

public abstract class Projectile extends Entity{
	
	public Projectile(float x, float y, Sprite spr, Player player) {
		super(x,y,spr);
	}
	
	public abstract void update(float delta, Player player);
	
	//TODO calcularMuzzle
	public static float[] calcularMuzzle(Player p, boolean b) {
		float rot = p.getRotation() + 90;
        float centerX = p.getSpr().getBoundingRectangle().getX() + p.getSpr().getBoundingRectangle().getWidth() / 2;
        float centerY = p.getSpr().getBoundingRectangle().getY() + p.getSpr().getBoundingRectangle().getHeight() / 2;
        float length = p.getSpr().getBoundingRectangle().getHeight() / 2f;
        float rad = (float) Math.toRadians(p.getRotation() + 90);
        float mx = centerX + (float) Math.cos(rad) * length;
        float my = centerY + (float) Math.sin(rad) * length;
        
        if (b) {
        	return new float[]{mx, my, rot};        	
        }
        return new float[]{centerX, centerY, rot}; 
        
    }
	
	public  void draw(SpriteBatch batch) {
		if (!isDestroyed()) {
            super.draw(batch);
        }
	}
	
}