package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import armas.*;
import hitboxes.BallHitbox;
import logica.AnimationFactory;
import logica.assets.AssetManager;
import logica.assets.SkinJugador;
import pantallas.juego.PantallaJuego;

public class Jugador {
	// Estado básico 
	private boolean destruida = false;
	private int vidas = 3;
	private int score = 0;
    private int ronda = 0;
	
	private float xVel = 0;
	private float yVel = 0;

	// Visual y audio
	public Sprite spr;
	private Sound sonidoHerido = AssetManager.getInstancia().getHurtSound();
	
	// ENCARGADOS DE ANIMACIÓN
	private Animation<TextureRegion> animacion;
    private float stateTime = 0f;
    
	// Herido
	private boolean herido = false;
	private int tiempoHeridoMax = 50;
	private int tiempoHerido;
	
	private final float V_LIMITE = 5.0f;

	// Rotación Jugador
	private float rotacion = 0f;

	// Armas
	private Arma armaActual = new Melee();
	
	public Jugador() {
	    // Sprite del jugador
	    spr = SkinJugador.JUGADOR_ORIGINAL.crearSprite();
	    
		// IMPLEMENTACIÓN DE LA ANIMACIÓN
    	this.animacion = AnimationFactory.createJugadorAnimation(SkinJugador.JUGADOR_ORIGINAL);
    	
    	spr.scale(1f);
    	spr.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    	spr.setOriginCenter();
	}
	
	public Jugador(int x, int y) {
		super();
		spr.setPosition(x, y);
	}
	
	public Jugador(int x, int y, Arma armaActual) {
		this(x, y);
		
	    this.armaActual = armaActual;
	}
	
	public Jugador(int x, int y, Arma armaActual, SkinJugador skin) {
	    this(x, y, armaActual);
	    
	    // Sprite del jugador
	    spr = skin.crearSprite();
	    
		// IMPLEMENTACIÓN DE LA ANIMACIÓN
    	this.animacion = AnimationFactory.createJugadorAnimation(skin);
	    
    	spr.scale(1f);
    	
    	spr.setPosition(x, y);
    	spr.setOriginCenter();
	}

	public Jugador(int x, int y, float rotacion, Arma armaActual, SkinJugador skin) {
	    this.armaActual = armaActual;
	    this.rotacion = rotacion;
	    
	    // Sprite del jugador
	    spr = skin.crearSprite();
	    
		// IMPLEMENTACIÓN DE LA ANIMACIÓN
    	this.animacion = AnimationFactory.createJugadorAnimation(skin);
	    
    	spr.scale(1f);
    	spr.rotate90(false);
    	
    	spr.setPosition(x, y);
    	//spr.setBounds(x, y, 45, 45);
    	
    	spr.setOriginCenter();
	}

	// Dibuja y actualiza; si paused, no procesa input, físicas ni disparo
	public void draw(SpriteBatch batch, PantallaJuego juego, boolean paused, float delta) {		
		if (paused) {
			if (herido) spr.draw(batch);
			return;
		}
		
	    float x = spr.getX();
	    float y = spr.getY();
	    
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
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.setX(x);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
	        return;
	    }

        // Rotación (LEFT/RIGHT) para el Jugador
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  rotacion += 3f;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) rotacion -= 3f;

        // Acelerar (UP) y freno (DOWN) + fricción 0.9
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            xVel -= (float) Math.sin(Math.toRadians(rotacion)) * 0.2f;
            yVel += (float) Math.cos(Math.toRadians(rotacion)) * 0.2f;
        } 
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            xVel += (float) Math.sin(Math.toRadians(rotacion)) * 0.2f;
            yVel -= (float) Math.cos(Math.toRadians(rotacion)) * 0.2f;
        }
        else {
            xVel *= 0.9f;
            yVel *= 0.9f;
        }
        

        // Límites de velocidad para el Jugador
        if (xVel > V_LIMITE) xVel = V_LIMITE;
        else if (xVel < -V_LIMITE) xVel = -V_LIMITE;
        if (yVel > V_LIMITE) yVel = V_LIMITE;
        else if (yVel < -V_LIMITE) yVel = -V_LIMITE;
        
        // Rebote en bordes
        if (x + xVel < 0 || x + xVel + spr.getWidth() > Gdx.graphics.getWidth())  xVel *= -1;
        if (y + yVel < 0 || y + yVel + spr.getHeight() > Gdx.graphics.getHeight()) yVel *= -1;

        spr.setPosition(x + xVel, y + yVel);
        spr.setRotation(rotacion);
        
        // Arma sin municion automaticamente cambia a ataque Melee
        //if (armaActual.getMunicion() == 0) this.setArma(new Melee());
        
        // Disparo del arma actual (Z).
        armaActual.actualizar(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            armaActual.disparar(this, juego, delta);
        }

	    // ENCARGADO DE MOSTRAR LA ANIMACIÓN
	    
	    batch.draw(currentFrame, 		// Fotograma 
                spr.getX(), spr.getY(), // Posición
                spr.getOriginX(), spr.getOriginY(), // Punto de origen
                spr.getWidth(), spr.getHeight(), 	// Dimensiones
                spr.getScaleX(), spr.getScaleY(), 	// Escala
                spr.getRotation()); 	// Rotación
	}

	// Colisión con asteroide (rebotes + estados/sonido)
	public boolean checkCollision(BallHitbox b) {
		float sfxVolume = 0f;
		if (herido) return false;
		
		boolean colision = b.getArea().overlaps(spr.getBoundingRectangle());
		if (!colision) return false;
		
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
            spr.setX(spr.getX() + Math.signum(xVel));
            cont++;
        }

        // Herida/vidas y sonido con volúmenes globales
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;

        if (sonidoHerido != null) sonidoHerido.play(sfxVolume);

        if (vidas <= 0) destruida = true;
        return true;
	}

	// Getters y Setters 
	public boolean estaDestruido() { return (!herido && destruida); }
	public boolean estaHerido() { return herido; }
	public int getVidas() { return vidas; }
	public float getxVel() { return xVel; }
	public float getyVel() { return yVel; }
	public float getRotacion() { return rotacion; }
	public Sprite getSpr() { return spr; }
	public float getCenterX() { return spr.getX() + spr.getWidth() / 2f; }
	public float getCenterY() { return spr.getY() + spr.getHeight() / 2f; }
	public int getX() { return (int) spr.getX(); }
	public int getY() { return (int) spr.getY(); }
	public Arma getArma() { return armaActual; }
	public int getRonda() { return ronda; }
	public int getScore() { return score; }
	
	public void setVidas(int v) { this.vidas = v; }
	public void setxVel(float xVel) { this.xVel = xVel; }
	public void setYVel(float yVel) { this.yVel = yVel; }
	public void setRotacion(float rotacion) {
		this.rotacion = rotacion;
		spr.setRotation(rotacion);
	}
	public void setScore(int i) {
		score = i;
	}


	public void setArma(Arma arma) { this.armaActual = arma; }
	
	public void aumentarScore(int cantidad) {
		score += cantidad;
	}

	
}
