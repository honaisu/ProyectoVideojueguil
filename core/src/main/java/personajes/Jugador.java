package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import armas.*;
import hitboxes.BallHitbox;
import hitboxes.Hitbox;
import logica.AnimationManager;
import pantallas.PantallaJuego;

public class Jugador extends Hitbox {
	// Estado básico 
	private boolean destruida = false;
	private int vidas = 3;
	private float xVel = 0;
	private float yVel = 0;

	// Visual y audio
	public ShapeRenderer shapeRenderer = new ShapeRenderer();;
	public Sprite hitBox;
	//public Sprite spr;
	private Sound sonidoHerido = null /*TODO*/;
	
	// ENCARGADOS DE ANIMACIÓN
	private Animation<TextureRegion> animacion;
    private float stateTime = 0f;
    
	// Herido
	private boolean herido = false;
	private int tiempoHeridoMax = 50;
	private int tiempoHerido;

	// Rotación Jugador
	private float rotacion;

	// Armas
	private Weapon armaActual;


	public Jugador(int x, int y, float rotacion, Weapon armaActual, SkinJugador skin) {
		super(x,y);

	    this.armaActual = armaActual;
	    this.rotacion = rotacion;
	    
	    // Sprite del jugador
	    setSpr(skin.crearSprite());
	    //spr = skin.crearSprite();
	    getSpr().setBounds(x, y, 40, 40);
	    getSpr().scale(1f);
	    getSpr().setPosition(x, y);
	    getSpr().setOriginCenter();
	    
	    //setSpr(new Sprite());
	    //getSpr().setBounds(x, y, 40, 40);
	    //getSpr().setPosition(x, y);
	    //getSpr().setOriginCenter();

	    
		// IMPLEMENTACIÓN DE LA ANIMACIÓN
    	this.animacion = AnimationManager.createJugadorAnimation(skin);
	    
	}

	// Dibuja y actualiza; si paused, no procesa input, físicas ni disparo
	public void draw(SpriteBatch batch, PantallaJuego juego, boolean paused, float delta) {
	    float x = getSpr().getX();
	    float y = getSpr().getY();
	    
	    // NECESARIOS PARA ANIMACION
 		TextureRegion currentFrame;
 		boolean isMoving = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
 	    
 		/**
 		 * Parte encargada de cambiar el si se mueve o no el jugador
 		 */
 	    if (isMoving && !paused) {
 	        // Si se está moviendo, avanza el tiempo de la animación
 	        stateTime += delta; 
 	        currentFrame = animacion.getKeyFrame(stateTime, true);
 	    } else {
 	        // Si está quieto, muestra siempre el primer fotograma de la animación
 	        currentFrame = animacion.getKeyFrame(0, true);
 	    }

	    // Estado herido: parpadeo/temblor y contador
	    if (herido) {
	        if (!paused) {
	            getSpr().setX(getSpr().getX() + MathUtils.random(-2, 2));
	            getSpr().setX(x);
	            
	            getSpr().setX(getSpr().getX() + MathUtils.random(-2, 2));
	            getSpr().setX(x);
	            tiempoHerido--;
	            if (tiempoHerido <= 0) herido = false;
	        }
	        getSpr().draw(batch);
	        return;
	    }

	    if (!paused) {
	        // Rotación (LEFT/RIGHT) para el Jugador
	        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  rotacion += 5f;
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) rotacion -= 5f;
	        getSpr().setRotation(rotacion);
	        //getSpr().setRotation(rotacion);

	        // Acelerar (UP) y freno (DOWN) + fricción 0.9
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	            xVel -= (float) Math.sin(Math.toRadians(rotacion)) * 0.2f;
	            yVel += (float) Math.cos(Math.toRadians(rotacion)) * 0.2f;
	        } else {
	            xVel *= 0.9f;
	            yVel *= 0.9f;
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
	            xVel += (float) Math.sin(Math.toRadians(rotacion)) * 0.2f;
	            yVel -= (float) Math.cos(Math.toRadians(rotacion)) * 0.2f;
	        }

	        // Límites de velocidad para el Jugador
	        if (xVel > 10.0f)  xVel = 10.0f;
	        if (xVel < -10.0f) xVel = -10.0f;
	        if (yVel > 10.0f)  yVel = 10.0f;
	        if (yVel < -10.0f) yVel = -10.0f;

	        // Rebote en bordes
	        if (x + xVel < 0 || x + xVel + getSpr().getWidth() > Gdx.graphics.getWidth())  xVel *= -1;
	        if (y + yVel < 0 || y + yVel + getSpr().getHeight() > Gdx.graphics.getHeight()) yVel *= -1;

	        getSpr().setPosition(x + xVel, y + yVel);
	        //getSpr().setPosition(x + xVel, y + yVel);
	        
	        
	        // ENCARGADO DE MOSTRAR LA ANIMACIÓN
	        batch.draw(
	        		currentFrame, 						// Fotograma 
	                getSpr().getX(), getSpr().getY(), 			// Posición
	                getSpr().getOriginX(), getSpr().getOriginY(), // Punto de origen
	                getSpr().getWidth(), getSpr().getHeight(), 	// Dimensiones
	                getSpr().getScaleX(), getSpr().getScaleY(), 	// Escala
	                getSpr().getRotation()); 				// Rotación
	        
	        
	        
	        // Arma sin municion automaticamente cambia a ataque Melee
	        if (armaActual.getMunicion() == 0) {
	        	//TODO ver que no dispare dos veces
	            this.setArma(new Melee());
	        }
	        
	        // Disparo del arma actual (Z).
	        
            float dt = Gdx.graphics.getDeltaTime();
            armaActual.actualizar(dt);
            if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                armaActual.disparar(this, juego, dt);
            }
	    }
	}

	// Colisión con asteroide (rebotes + estados/sonido)
	public void reaccion(BallHitbox b) {
		if (checkCollision(b)) {
			// Rebote X
	        if (xVel == 0) xVel += b.getXSpeed() / 2f;
	        if (b.getXSpeed() == 0) b.setXSpeed(b.getXSpeed() + (int) (xVel / 2f));
	        xVel = -xVel;
	        b.setXSpeed(-b.getXSpeed());

	        // Rebote Y
	        if (yVel == 0) yVel += b.getySpeed() / 2f;
	        if (b.getySpeed() == 0) b.setySpeed(b.getySpeed() + (int) (yVel / 2f));
	        yVel = -yVel;
	        b.setySpeed(-b.getySpeed());
			
	        // Separación mínima para evitar solape
	        int cont = 0;
	        while (cont < Math.abs(xVel)) {
	        	//getSpr().setX(getSpr().getX() + Math.signum(xVel));
	            cont++;
	        }

	        // Herida/vidas y sonido con volúmenes globales
	        vidas--;
	        herido = true;
	        tiempoHerido = tiempoHeridoMax;

	        //if (sonidoHerido != null) sonidoHerido.play(sfxVolume);

	        if (vidas <= 0) destruida = true;
		}
	}

	// Getters y Setters 
	
	public boolean estaDestruido() { return (!herido && destruida); }

	public boolean estaHerido() { return herido; }

	public int getVidas() { return vidas; }

	public void setVidas(int v) { this.vidas = v; }

	public float getxVel() { return xVel; }

	public void setxVel(float xVel) { this.xVel = xVel; }

	public float getyVel() { return yVel; }

	public void setYVel(float yVel) { this.yVel = yVel; }

	public float getRotacion() { return rotacion; }

	public void setRotacion(float rotacion) {
	    this.rotacion = rotacion;
	    getSpr().setRotation(rotacion);
	}

	public float getCenterX() { return getSpr().getX() + getSpr().getWidth() / 2f; }

	public float getCenterY() { return getSpr().getY() + getSpr().getHeight() / 2f; }

	public Weapon getArma() { return armaActual; }

	public void setArma(Weapon arma) { this.armaActual = arma; }

	public void setTexture(Texture nueva) {
	    float x = getSpr().getX(), y = getSpr().getY();
	    float w = getSpr().getWidth(), h = getSpr().getHeight();
	    getSpr().setTexture(nueva);
	    getSpr().setBounds(x, y, w, h);
	    getSpr().setOriginCenter();
	    getSpr().setRotation(rotacion);
	}
}
