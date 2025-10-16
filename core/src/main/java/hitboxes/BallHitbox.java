package hitboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class BallHitbox extends Hitbox{
    private int xSpeed;
    private int ySpeed;
    public Sprite spr; // TODO
    
	public ShapeRenderer shapeRenderer = new ShapeRenderer();;

    public BallHitbox(float x, float y, int size, int xSpeed, int ySpeed, Texture tx) {
    	super(x,y);
    	
    	spr = new Sprite(tx);
    	spr.setBounds(x, y, size * 2, size * 2);
    	spr.setOriginCenter();

    	// Ajusta la posición para asegurar que no se salga de la pantalla al iniciar
        if (spr.getX() < 0) spr.setX(0);
        if (spr.getX() + spr.getWidth() > Gdx.graphics.getWidth()) spr.setX(Gdx.graphics.getWidth() - spr.getWidth());
        if (spr.getY() < 0) spr.setY(0);
        if (spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) spr.setY(Gdx.graphics.getHeight() - spr.getHeight());

        this.setXSpeed(xSpeed);
        this.setySpeed(ySpeed);
    	
    	
    	//this.x = x; 
 	
        //validar que borde de esfera no quede fuera
    	/*
    	if (x-size < 0) this.x = x+size;
    	if (x+size > Gdx.graphics.getWidth())this.x = x-size;
         
        this.y = y;
        //validar que borde de esfera no quede fuera
    	if (y-size < 0) this.y = y+size;
    	if (y+size > Gdx.graphics.getHeight())this.y = y-size;
    	
        spr.setPosition(x, y);
        this.setXSpeed(xSpeed);
        this.setySpeed(ySpeed);
        */
    }
    
    public void update() {
        spr.setPosition(spr.getX() + getXSpeed(), spr.getY() + getySpeed());

        if (spr.getX()+getXSpeed() < 0 || spr.getX()+getXSpeed()+spr.getWidth() > Gdx.graphics.getWidth())
        	setXSpeed(getXSpeed() * -1);
        if (spr.getY()+getySpeed() < 0 || spr.getY()+getySpeed()+spr.getHeight() > Gdx.graphics.getHeight())
        	setySpeed(getySpeed() * -1);
    }
    
    public Rectangle getArea() {
    	return spr.getBoundingRectangle();
    }
    public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    }
    
    @Override
    public boolean checkCollision(BallHitbox b2) {
    	return Intersector.overlapConvexPolygons(getRotatedPolygon(spr), getRotatedPolygon(b2.spr));
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
            
        	/*
            if (getXSpeed() ==0) setXSpeed(getXSpeed() + b2.getXSpeed()/2);
            if (b2.getXSpeed() ==0) b2.setXSpeed(b2.getXSpeed() + getXSpeed()/2);
        	setXSpeed(- getXSpeed());
            b2.setXSpeed(-b2.getXSpeed());
            
            if (getySpeed() ==0) setySpeed(getySpeed() + b2.getySpeed()/2);
            if (b2.getySpeed() ==0) b2.setySpeed(b2.getySpeed() + getySpeed()/2);
            setySpeed(- getySpeed());
            b2.setySpeed(- b2.getySpeed());*/
        	
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
	public Sprite getSpr() {
        return spr;
    }
}
