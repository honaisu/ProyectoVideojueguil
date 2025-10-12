package armas;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import armas.proyectiles.Bullet;
import pantallas.PantallaJuego;
import personajes.Jugador;

public class Escopeta extends Arma {
	
	//private float tiempoDesdeUltimoDisparo = cadencia;

	public Escopeta() {
		super(3f, 8, new Texture(Gdx.files.internal("Rocket2.png")), Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
	}

	@Override
	public void disparar(Jugador nave, PantallaJuego juego, float delta) {
		actualizar(delta);
        
        if (municion <= 0) {
            return; // SIN BALAS
        }
        if (municion > 0) {
        	tiempoDesdeUltimoDisparo += delta;
            if (tiempoDesdeUltimoDisparo >= cadencia) {
                crearBala(nave, juego);
                municion--; 
                tiempoDesdeUltimoDisparo = 0;
            }
        }
        if (puedeDisparar()) {
            reiniciarCooldown();
        }
	}
	
	private void crearBala(Jugador nave, PantallaJuego juego) {
        float radians = (float) Math.toRadians(nave.getRotacion() + 90);
        float centerX = nave.spr.getX() + nave.spr.getWidth() / 2;
        float centerY = nave.spr.getY() + nave.spr.getHeight() / 2;
        float length = nave.spr.getHeight() / 2;

        float bulletX = centerX + (float) Math.cos(radians) * length;
        float bulletY = centerY + (float) Math.sin(radians) * length;
        
        Random r = new Random();
        int pellets = 8;
        int spread = 15; // grados de apertura

        for (int i = 0; i < pellets; i++) {
            // Ángulo con desviación aleatoria
            float angle = nave.getRotacion() + (r.nextFloat() * spread * 2 - spread); // -15 a +15 grados

            Bullet bala = new Bullet(
                bulletX, bulletY,
                angle,                     // dirección corregida
                10f + r.nextInt(4),        // velocidad levemente aleatoria
                txBala
            );
            juego.agregarBala(bala);
        }
        soundBala.play(0.1f);
    }
}
