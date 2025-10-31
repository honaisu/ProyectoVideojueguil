package armas;

import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.LaserBeam;
import armas.proyectiles.Projectile;
import logica.GameWorld;
import managers.AssetManager;
import personajes.Player;

public class Laser extends Weapon {
    // Parámetros del “pulso” (un disparo = un rayo breve)
    private final float anchoLaser = 10f;     // grosor del rayo
    private final float ttlPulso   = 0.12f;   // duración de cada pulso en segundos

    // misma idea que el metralleta (literal es igual en todos creo xd)
    public Laser() {
        super(0.08f, 60);
        super.setNombre("Laser Gun");
    	super.setSoundBala(AssetManager.getInstancia().getLaserContSound());
    }
    
    public Laser(float cadencia, int municionMax) {
    	super(cadencia, municionMax);
    	
    	super.setNombre("Laser Gun");
    	super.setSoundBala(AssetManager.getInstancia().getLaserContSound());
    }
    // Disparo “pulso” como dijo benjoid
    /*
    @Override
    public void disparar(Player nave, GameWorld juego, float delta) {
    	// avanzar cooldown
        this.actualizar(delta);
        // mismas verificaciones que otras armas
        if (!puedeDisparar()) return;
        if (getMunicion() <= 0) return;

        // consumir 1 unidad
        restarMunicion(nave, juego, delta);
        // crear el rayo y registrarlo como un “disparo” breve
        crearProyectil(nave, juego);

        // sonido y reinicio de cadencia
        getSoundBala().play(0.35f);
        
        reiniciarCooldown();
    }*/

    //Construcción del proyectil, similar a crearBala() de metralla (ashprita si benjoid, ojala sirva u_u)
    
    /*@Override
    public void crearProyectil(Player nave, GameWorld juego) {    	
        LaserBeam pulso = new LaserBeam(nave, anchoLaser, AssetManager.getInstancia().getLaserContTexture(), 1);
        pulso.configurarPulso(ttlPulso); // se apaga solo al vencer el TTL
        
        juego.getGameManager().getProyectilManager().add(pulso);
    }*/
    @Override
    public Projectile crearProyectil(Rectangle r, float rotation) {    	
        //LaserBeam pulso = new LaserBeam(nave, anchoLaser, AssetManager.getInstancia().getLaserContTexture(), 1);
        LaserBeam pulso = new LaserBeam(r, rotation);
    	pulso.configurarPulso(ttlPulso); // se apaga solo al vencer el TTL
        
        return pulso;
    }
}
