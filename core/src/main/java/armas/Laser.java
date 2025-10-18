package armas;

import armas.proyectiles.LaserBeam;
import logica.AssetsLoader;
import pantallas.PantallaJuego;
import personajes.Jugador;


public class Laser extends Weapon {
    
    // Parámetros del “pulso” (un disparo = un rayo breve)
    private final float anchoLaser = 10f;     // grosor del rayo
    private final float ttlPulso   = 0.12f;   // duración de cada pulso en segundos


    // misma idea que el metralleta (literal es igual en todos creo xd)
    public Laser() {
        super("Laser Gun",
        		0.08f,
        		60,
        		AssetsLoader.getInstancia().getLaserContSound());
    }
    // Disparo “pulso” como dijo benjoid
    @Override
    public void disparar(Jugador nave, PantallaJuego pantalla, float delta) {
    	// avanzar cooldown
        this.actualizar(delta);

        // mismas verificaciones que otras armas
        if (!puedeDisparar()) return;
        if (getMunicion() <= 0) return;

        // consumir 1 unidad
        restarMunicion(nave, pantalla, delta);

        // crear el rayo y registrarlo como un “disparo” breve
        crearProyectil(nave, pantalla);

        // sonido y reinicio de cadencia
        getSoundBala().play(0.35f);
        
        reiniciarCooldown();
    }

    //Construcción del proyectil, similar a crearBala() de metralla (ashprita si benjoid, ojala sirva u_u)
    @Override
    public void crearProyectil(Jugador nave, PantallaJuego juego) {    	
        LaserBeam pulso = new LaserBeam(nave, anchoLaser, AssetsLoader.getInstancia().getLaserContTexture(), 1);
        pulso.configurarPulso(ttlPulso); // se apaga solo al vencer el TTL
        
        juego.agregarLaser(pulso); //TODO revisar esto
        
    }


}
