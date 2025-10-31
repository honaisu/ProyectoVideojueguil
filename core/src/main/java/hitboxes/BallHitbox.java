package hitboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import managers.AssetManager;

public class BallHitbox extends Hitbox {
    private int xSpeed = 0;
    private int ySpeed = 0;
    
	public ShapeRenderer shapeRenderer = new ShapeRenderer();

    public BallHitbox(float x, float y, int size) {
    	super(x,y, new Sprite(AssetManager.getInstancia().getBalaTexture()));
    	
    	getSpr().setBounds(x, y, size * 2, size * 2);
    	getSpr().setOriginCenter();

    	// Ajusta la posición para asegurar que no se salga de la pantalla al iniciar
        if (getSpr().getX() < 0) getSpr().setX(0);
        if (getSpr().getX() + getSpr().getWidth() > Gdx.graphics.getWidth()) getSpr().setX(Gdx.graphics.getWidth() - getSpr().getWidth());
        if (getSpr().getY() < 0) getSpr().setY(0);
        if (getSpr().getY() + getSpr().getHeight() > Gdx.graphics.getHeight()) getSpr().setY(Gdx.graphics.getHeight() - getSpr().getHeight());
    }
    
    public void update() {
        getSpr().setPosition(getSpr().getX() + getXSpeed(), getSpr().getY() + getySpeed());

        if (getSpr().getX()+getXSpeed() < 0 || getSpr().getX()+getXSpeed()+getSpr().getWidth() > Gdx.graphics.getWidth())
        	setXSpeed(getXSpeed() * -1);
        if (getSpr().getY()+getySpeed() < 0 || getSpr().getY()+getySpeed()+getSpr().getHeight() > Gdx.graphics.getHeight())
        	setySpeed(getySpeed() * -1);
    }
    
    public Rectangle getArea() {
    	return getSpr().getBoundingRectangle();
    }
    
    public void rebote(BallHitbox b2) {
        if(checkCollision(b2)){
        	// Lógica de rebote simple
            int tempXSpeed = this.getXSpeed();
            int tempYSpeed = this.getySpeed();
            
            this.setXSpeed(b2.getXSpeed());
            this.setySpeed(b2.getySpeed());
            
            b2.setXSpeed(tempXSpeed);
            b2.setySpeed(tempYSpeed);
        }
    }
    
	public int getXSpeed() {
		return xSpeed;
	}
	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
	public int getySpeed() {
		return ySpeed;
	}
	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
}
