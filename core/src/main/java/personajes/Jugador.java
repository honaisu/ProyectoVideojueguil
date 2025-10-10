package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import armas.proyectiles.Bullet;
import enemigos.Ball2;
import pantallas.PantallaJuego;

public class Jugador {
	
	private boolean destruida = false;
	private int vidas = 3;
	private float xVel = 0;
	private float yVel = 0;
	private Sprite spr;
	private Sound sonidoHerido;
	private Sound soundBala;
	private Texture txBala;
	private boolean herido = false;
	private int tiempoHeridoMax = 50;
	private int tiempoHerido;

	// REFERENCIA AL JUEGO para leer volúmenes globales
	private SpaceNavigation gameRef;

	// Actualizado: se agrega SpaceNavigation gameRef
	public Jugador(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala, SpaceNavigation gameRef) {
	    this.gameRef = gameRef;
	    sonidoHerido = soundChoque;
	    this.soundBala = soundBala;
	    this.txBala = txBala;
	    spr = new Sprite(tx);
	    spr.setPosition(x, y);
	    //spr.setOriginCenter();
	    spr.setBounds(x, y, 45, 45);
	}

	// NUEVO: cambiar la textura manteniendo posición/tamaño
	public void setTexture(Texture nueva) {
	    float x = spr.getX(), y = spr.getY();
	    float w = spr.getWidth(), h = spr.getHeight();
	    spr.setTexture(nueva);
	    spr.setBounds(x, y, w, h);
	    spr.setOriginCenter();
	}

	// NUEVO: draw con flag de pausa para no mover ni disparar cuando está pausado
	public void draw(SpriteBatch batch, PantallaJuego juego, boolean paused){
	    float x = spr.getX();
	    float y = spr.getY();

	    if (!herido) {
	        //esto es para probar si funciona el que el jugador se quede quieto en el menu de pausa
	        // Si está pausado, NO leer teclado ni mover la nave
	        if (!paused) {
	            // que se mueva con teclado
	            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))  xVel--;
	            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) xVel++;
	            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))  yVel--;
	            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))    yVel++;

	            // que se mantenga dentro de los bordes de la ventana
	            if (x + xVel < 0 || x + xVel + spr.getWidth() > Gdx.graphics.getWidth())
	                xVel *= -1;
	            if (y + yVel < 0 || y + yVel + spr.getHeight() > Gdx.graphics.getHeight())
	                yVel *= -1;

	            spr.setPosition(x + xVel, y + yVel);
	        }

	        spr.draw(batch);
	    } else {
	        // Temblor de "herido": si quieres congelarlo en pausa, protégelo con !paused
	        if (!paused) {
	            spr.setX(spr.getX() + MathUtils.random(-2, 2));
	            spr.draw(batch);
	            spr.setX(x);
	            tiempoHerido--;
	            if (tiempoHerido <= 0) herido = false;
	        } else {
	            // En pausa, solo dibujar sin animar
	            spr.draw(batch);
	        }
	    }

	    // disparo solo si no está pausado (y respeta volúmenes globales)
	    if (!paused && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
	        Bullet bala = new Bullet(
	            spr.getX() + spr.getWidth()/2 - 5,
	            spr.getY() + spr.getHeight() - 5,
	            0, 3, txBala
	        );
	        juego.agregarBala(bala);

	        // Volúmenes globales: master*sfx
	        float mv = gameRef.getMasterVolume();
	        float sfx = gameRef.getSfxVolume();
	        soundBala.play(mv * sfx);
	    }
	}

	public boolean checkCollision(Ball2 b) {
	    if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
	        // rebote
	        if (xVel == 0) xVel += b.getXSpeed()/2;
	        if (b.getXSpeed() == 0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
	        xVel = - xVel;
	        b.setXSpeed(-b.getXSpeed());

	        if (yVel == 0) yVel += b.getySpeed()/2;
	        if (b.getySpeed() == 0) b.setySpeed(b.getySpeed() + (int)yVel/2);
	        yVel = - yVel;
	        b.setySpeed(- b.getySpeed());

	        //actualizar vidas y herir
	        vidas--;
	        herido = true;
	        tiempoHerido = tiempoHeridoMax;

	        // Volúmenes globales: master*sfx
	        float mv = gameRef.getMasterVolume();
	        float sfx = gameRef.getSfxVolume();
	        sonidoHerido.play(mv * sfx);

	        if (vidas <= 0)
	            destruida = true;
	        return true;
	    }
	    return false;
	}

	public boolean estaDestruido() {
	   return !herido && destruida;
	}
	public boolean estaHerido() {
	    return herido;
	}

	public int getVidas() { return vidas; }
	public int getX() { return (int) spr.getX(); }
	public int getY() { return (int) spr.getY(); }
	public void setVidas(int vidas2) { vidas = vidas2; }

}
