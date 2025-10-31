package hitboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import managers.AssetManager;

public class BallHitbox extends Hitbox {
    private int xSpeed = 0;
    private int ySpeed = 0;
    
	public BallHitbox(float x, float y, float size, float rotation) {
		super(x,y, new Sprite(AssetManager.getInstancia().getBalaTexture()));
		
		//getSpr().setScale(size);
    	getSpr().setRotation(rotation);
    	//float width = getSpr().getWidth() * size;
        //float height = getSpr().getHeight() * size; 
        getSpr().setBounds(x, y, size, size);
        getSpr().setOriginCenter();
    	checkBounds();
	}
	
	private void checkBounds() {
		// Ajusta la posición para asegurar que no se salga de la pantalla al iniciar
		if (getSpr().getX() < 0) getSpr().setX(0);
		if (getSpr().getY() < 0) getSpr().setY(0);
		
        if (getSpr().getX() + getSpr().getWidth() > Gdx.graphics.getWidth()) 
        	getSpr().setX(Gdx.graphics.getWidth() - getSpr().getWidth());
        
        if (getSpr().getY() + getSpr().getHeight() > Gdx.graphics.getHeight()) 
        	getSpr().setY(Gdx.graphics.getHeight() - getSpr().getHeight());
	}
    
    public void update() {
    	float newX = getSpr().getX() + xSpeed;
        float newY = getSpr().getY() + ySpeed;
        
        // Setea los limites de la velocidad (?)
        if (newX < 0 || newX + getSpr().getWidth() > Gdx.graphics.getWidth()) {
        	xSpeed *= -1;
        	newX = getSpr().getX() + xSpeed;
        }
        if (newY < 0 || newY + getSpr().getHeight() > Gdx.graphics.getHeight()) {
        	ySpeed *= -1;
        	newY = getSpr().getY() + ySpeed;
        }
        getSpr().setPosition(newX, newY);
    }
    
    public Rectangle getArea() {
    	return getSpr().getBoundingRectangle();
    }
    
    public void rebote(BallHitbox entity) {
        if(checkCollision(entity)){
        	// Lógica de rebote simple
            int tempXSpeed = xSpeed;
            int tempYSpeed = ySpeed;
            
            xSpeed = entity.getXSpeed();
            ySpeed = entity.getYSpeed();
            
            entity.setXSpeed(tempXSpeed);
            entity.setYSpeed(tempYSpeed);
        }
    }
    
	public int getXSpeed() {
		return xSpeed;
	}
	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
	public int getYSpeed() {
		return ySpeed;
	}
	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
}
