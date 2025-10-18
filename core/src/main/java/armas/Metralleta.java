package armas;

import armas.proyectiles.Bullet;
import logica.assets.AssetManager;
import pantallas.juego.PantallaJuego;
import personajes.Jugador;

//Clase para un arma escopeta
public class Metralleta extends Arma {
    public Metralleta() {
        super(0.2f,												// cadencia
        		30, 											// municion
        		AssetManager.getInstancia().getDisparoSound()); // sonido
    }
    
  //clase sobrescrita
    @Override
    public void disparar(Jugador nave, PantallaJuego juego, float delta) {
        actualizar(delta);
        
        //sin balas
        if (this.getMunicion() <= 0) return;
        
        //cadencia
    	tiempoDesdeUltimoDisparo += delta;
        if (tiempoDesdeUltimoDisparo >= cadencia) {
            crearBala(nave, juego);
            municion--; 
            tiempoDesdeUltimoDisparo = 0;
        }
        
        if (puedeDisparar()) reiniciarCooldown();
    }
    
    //crea la bala de la metralleta con direccion respecto al jugador
    private void crearBala(Jugador nave, PantallaJuego juego) {
        float radians = (float) Math.toRadians(nave.getRotacion() + 90);
        float centerX = nave.spr.getX() + nave.spr.getWidth() / 2;
        float centerY = nave.spr.getY() + nave.spr.getHeight() / 2;
        float length = nave.spr.getHeight() / 2;

        float bulletX = centerX + (float) Math.cos(radians) * length;
        float bulletY = centerY + (float) Math.sin(radians) * length;

        Bullet bala = new Bullet(
        		bulletX, bulletY,									// posicion de la bala
        		nave.getRotacion(),									// dirección de la bala
        		10f,												// velocidad levemente aleatoria
        		AssetManager.getInstancia().getBalaTexture());	// textura de la bala
        
        juego.agregarBala(bala);
        soundBala.play(0.1f);
    }

}
