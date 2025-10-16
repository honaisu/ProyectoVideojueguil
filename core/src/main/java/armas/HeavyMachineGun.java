package armas;

import armas.proyectiles.Bullet;
import logica.AssetsLoader;
import pantallas.PantallaJuego;
import personajes.Jugador;

//Clase para un arma escopeta

public class HeavyMachineGun extends Weapon {

    public HeavyMachineGun() {
        super(0.2f,															// cadencia
        		30, 														// municion
        		AssetsLoader.getInstancia().getDisparoSound()); 	// sonido
    }
    
  //clase sobrescrita
    @Override
    public void disparar(Jugador nave, PantallaJuego pantalla, float delta) {
        actualizar(delta);
        
        //sin balas
        if (municion <= 0) return;
        
        //cadencia
    	tiempoDesdeUltimoDisparo += delta;
        if (tiempoDesdeUltimoDisparo >= cadencia) {
            crearBala(nave, pantalla);
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
        		0.1f,												// velocidad levemente aleatoria
        		AssetsLoader.getInstancia().getBalaTexture());	// textura de la bala
        
        juego.agregarBala(bala);
        soundBala.play(0.1f);
    }

}
