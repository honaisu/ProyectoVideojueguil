package armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import armas.proyectiles.Bullet;
import pantallas.PantallaJuego;
import personajes.Nave4;

public class Metralleta extends Arma {

    public Metralleta() {
        super(0.2f, 30, new Texture(Gdx.files.internal("semicirculo.png")), Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))); 
    }

    @Override
    public void disparar(Nave4 nave, PantallaJuego juego, float delta) {
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

    private void crearBala(Nave4 nave, PantallaJuego juego) {
        float radians = (float) Math.toRadians(nave.getRotacion() + 90);
        float centerX = nave.spr.getX() + nave.spr.getWidth() / 2;
        float centerY = nave.spr.getY() + nave.spr.getHeight() / 2;
        float length = nave.spr.getHeight() / 2;

        float bulletX = centerX + (float) Math.cos(radians) * length;
        float bulletY = centerY + (float) Math.sin(radians) * length;

        Bullet bala = new Bullet(bulletX, bulletY, nave.getRotacion(), 10f, new Texture(Gdx.files.internal("Rocket2.png")));
        juego.agregarBala(bala);
        soundBala.play(0.1f);
    }

}
