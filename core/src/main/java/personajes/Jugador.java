package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import armas.proyectiles.Bullet;
import hitboxes.Ball2;
import pantallas.PantallaJuego;

public class Jugador {
	
	// Estado
	private boolean destruida = false;
	private int vidas = 3;
	private boolean herido = false;
	private int tiempoHeridoMax = 50;
	private int tiempoHerido;

	// Movimiento (idéntico a Nave4)
	private float xVel = 0f;
	private float yVel = 0f;
	private float rotacion = 0f;
	private final float rotacionPaso = 5f;   // grados por frame con LEFT/RIGHT
	private final float thrust = 0.2f;       // empuje por frame
	private final float friccion = 0.97f;    // desaceleración
	private final float vMax = 10.0f;        // límite de velocidad

	// Visual
	private Sprite spr;


	// Audio/recursos
	private Sound sonidoHerido;
	private Sound soundBala;      // sonido del disparo
	private Texture txBala;       // textura de la bala

	// Volúmenes globales
	private SpaceNavigation gameRef;

	public Jugador(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala, SpaceNavigation gameRef) {
	    this.gameRef = gameRef;
	    this.sonidoHerido = soundChoque;
	    this.soundBala = soundBala;
	    this.txBala = txBala;

	    spr = new Sprite(tx);
	    spr.setPosition(x, y);
	    spr.setBounds(x, y, 45, 45);
	    spr.setOriginCenter();
	}

	// Cambiar skin manteniendo posición/tamaño/origen
	public void setTexture(Texture nueva) {
	    float x = spr.getX(), y = spr.getY();
	    float w = spr.getWidth(), h = spr.getHeight();
	    spr.setTexture(nueva);
	    spr.setBounds(x, y, w, h);
	    spr.setOriginCenter();
	}

	// Dibuja y actualiza; respeta pausa
	public void draw(SpriteBatch batch, PantallaJuego juego, boolean paused) {
	    float x = spr.getX();
	    float y = spr.getY();

	    if (herido) {
	        if (!paused) {
	            spr.setX(spr.getX() + MathUtils.random(-2, 2));
	            spr.draw(batch);
	            spr.setX(x);
	            tiempoHerido--;
	            if (tiempoHerido <= 0) herido = false;
	        } else {
	            spr.draw(batch);
	        }
	        return;
	    }

	    if (!paused) {
	        // Rotación
	        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  rotacion += rotacionPaso;
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) rotacion -= rotacionPaso;
	        spr.setRotation(rotacion);

	        // Thrust y freno (UP/DOWN) + fricción
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	            xVel -= (float) Math.sin(Math.toRadians(rotacion)) * thrust;
	            yVel += (float) Math.cos(Math.toRadians(rotacion)) * thrust;
	        } else {
	            xVel *= friccion;
	            yVel *= friccion;
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
	            xVel += (float) Math.sin(Math.toRadians(rotacion)) * thrust;
	            yVel -= (float) Math.cos(Math.toRadians(rotacion)) * thrust;
	        }

	        // Límites de velocidad
	        if (xVel > vMax)  xVel = vMax;
	        if (xVel < -vMax) xVel = -vMax;
	        if (yVel > vMax)  yVel = vMax;
	        if (yVel < -vMax) yVel = -vMax;

	        // Rebote en bordes
	        if (x + xVel < 0 || x + xVel + spr.getWidth() > Gdx.graphics.getWidth())  xVel *= -1;
	        if (y + yVel < 0 || y + yVel + spr.getHeight() > Gdx.graphics.getHeight()) yVel *= -1;

	        spr.setPosition(x + xVel, y + yVel);

	        // Disparo estilo Nave4 con tecla Z: desde la punta
	        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
	            float radians = (float) Math.toRadians(rotacion + 90f);

	            float centerX = spr.getX() + spr.getWidth() / 2f;
	            float centerY = spr.getY() + spr.getHeight() / 2f;
	            float length  = spr.getHeight() / 2f;

	            float bulletX = centerX + (float) Math.cos(radians) * length;
	            float bulletY = centerY + (float) Math.sin(radians) * length;

	            Bullet bala = new Bullet(bulletX, bulletY, rotacion, 10f, txBala);
	            juego.agregarBala(bala);

	            float vol = gameRef.getMasterVolume() * gameRef.getSfxVolume();
	            if (soundBala != null) soundBala.play(vol);
	        }
	    }

	    spr.draw(batch);
	}

	public boolean checkCollision(Ball2 b) {
	    if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
	        // Rebotes basados en Nave4
	        if (xVel == 0) xVel += b.getXSpeed()/2f;
	        if (b.getXSpeed() == 0) b.setXSpeed(b.getXSpeed() + (int)(xVel/2f));
	        xVel = -xVel;
	        b.setXSpeed(-b.getXSpeed());

	        if (yVel == 0) yVel += b.getySpeed()/2f;
	        if (b.getySpeed() == 0) b.setySpeed(b.getySpeed() + (int)(yVel/2f));
	        yVel = -yVel;
	        b.setySpeed(-b.getySpeed());

	        // despegar sprites
	        int cont = 0;
	        while (b.getArea().overlaps(spr.getBoundingRectangle()) && cont < Math.abs(xVel)) {
	            spr.setX(spr.getX() + Math.signum(xVel));
	            cont++;
	        }

	        // Herida/vidas
	        vidas--;
	        herido = true;
	        tiempoHerido = tiempoHeridoMax;

	        float vol = gameRef.getMasterVolume() * gameRef.getSfxVolume();
	        if (sonidoHerido != null) sonidoHerido.play(vol);

	        if (vidas <= 0) destruida = true;
	        return true;
	    }
	    return false;
	}

	public boolean estaDestruido() { return (!herido && destruida); }
	public boolean estaHerido() { return herido; }
	public int getVidas() { return vidas; }
	public int getX() { return (int) spr.getX(); }
	public int getY() { return (int) spr.getY(); }
	public void setVidas(int v) { vidas = v; }
	protected float getRotacion() { return rotacion; }



}
