/*package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


import armas.Arma;
import hitboxes.Ball2;
import pantallas.PantallaJuego;

public class Nave4 {
	private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;

	public Sprite spr;		//REVISAR ESTO
    private Sound sonidoHerido;
    public Texture txBala;  //REVISAR ESTO
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
	private float rotacion;
	
	private Arma armaActual;
    
    public Nave4(int x, int y, float rotacion, Texture tx, Sound soundChoque, Arma armaActual) {
    	sonidoHerido = soundChoque;
    	this.armaActual = armaActual;
    	this.rotacion = rotacion;
    	
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, 45, 45);
    	spr.setOriginCenter();

    }
    
    public void draw(SpriteBatch batch, PantallaJuego juego){
        float x =  spr.getX();
        float y =  spr.getY();
        
        if (herido) {
        	spr.setX(spr.getX()+MathUtils.random(-2,2));
        	spr.draw(batch); 
        	spr.setX(x);
        	tiempoHerido--;
        	if (tiempoHerido<=0) herido = false;
        	return;
        }
        
    	if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) rotacion += 5f;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) rotacion -= 5f;
        spr.setRotation(rotacion);
        
        float friccion = 0.97f;
        		
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
        	xVel -= Math.sin(Math.toRadians(rotacion)) * 0.2f;
        	yVel += Math.cos(Math.toRadians(rotacion)) * 0.2f;
        	//System.out.println(rotacion+" - "+Math.sin(Math.toRadians(rotacion))+" - "+Math.cos(Math.toRadians(rotacion))) ;    
        }else {
        	xVel *= friccion;
        	yVel *= friccion;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
        	xVel += Math.sin(Math.toRadians(rotacion)) * 0.2f;
        	yVel -= Math.cos(Math.toRadians(rotacion)) * 0.2f;
        }
        
        if (xVel >= 10.0f) {
        	xVel = 10.0f;
        }
        
        if (yVel >= 10.0f) {
        	yVel = 10.0f;
        }
        
        // que se mantenga dentro de los bordes de la ventana
        if (x+xVel < 0 || x+xVel+spr.getWidth() > Gdx.graphics.getWidth())
        	xVel*=-1;
        if (y+yVel < 0 || y+yVel+spr.getHeight() > Gdx.graphics.getHeight())
        	yVel*=-1;
        
        spr.setPosition(x+xVel, y+yVel);
	    spr.draw(batch);
    

        // disparo
	    
	    //armaActual.actualizar(Gdx.graphics.getDeltaTime());
	    if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
	        armaActual.disparar(this, juego, Gdx.graphics.getDeltaTime());
        	

        }
    }
      
    public boolean checkCollision(Ball2 b) {
        if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
        	// rebote
            if (xVel ==0) xVel += b.getXSpeed()/2;
            if (b.getXSpeed() ==0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
            xVel = - xVel;
            b.setXSpeed(-b.getXSpeed());
            
            if (yVel ==0) yVel += b.getySpeed()/2;
            if (b.getySpeed() ==0) b.setySpeed(b.getySpeed() + (int)yVel/2);
            yVel = - yVel;
            b.setySpeed(- b.getySpeed());
            // despegar sprites
            int cont = 0;
            while (b.getArea().overlaps(spr.getBoundingRectangle()) && cont<xVel) {
               spr.setX(spr.getX()+Math.signum(xVel));
            }
        	//actualizar vidas y herir
            vidas--;
            herido = true;
  		    tiempoHerido=tiempoHeridoMax;
  		    sonidoHerido.play();
            if (vidas<=0) 
          	    destruida = true; 
            return true;
        }
        return false;
    }
    
    
    
    public boolean estaDestruido() {
       return (!herido && destruida);
    }
    
    public boolean estaHerido() {
 	   return herido;
    }
    
    public int getVidas() {
    	return vidas;
    }
    //public boolean isDestruida() { return destruida;}
    public int getX() {
    	return (int) spr.getX();
    }
    public int getY() {
    	return (int) spr.getY();
    }
	public void setVidas(int vidas2) {
		vidas = vidas2;
	}
	public float getRotacion() {
		return rotacion;
	}
	public void setArma(Arma arma) {
		armaActual = arma;
		
	}
	public Arma getArma() {
		return armaActual;
	}
	
	public float getxVel() {
		return xVel;
	}
	public float getyVel() {
		return yVel;
	}
	
}*/
