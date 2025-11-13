package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import armas.proyectiles.LaserBeam;
import armas.proyectiles.Projectile;
import managers.AssetManager;
import managers.ProjectileManager;
import personajes.Player;

public class LaserGun extends Weapon {
    // Parámetros del “pulso” (un disparo = un rayo breve)

    // misma idea que el metralleta (literal es igual en todos creo xd)
    public LaserGun() {
    	super("Laser Gun",
				2,													// daño
				0.08f, 												// cadencia
				6000, 												// municion
				AssetManager.getInstancia().getLaserContSound());	// sonido
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
    public void crearProyectil(Player p, ProjectileManager manager) {
    	float muzzle[] = Projectile.calcularMuzzle(p, true);
		float width = 20f;     // grosor visual/efectivo
		float duracionPulso = 0.25f; // duración del rayo por disparo (segundos)
        //LaserBeam pulso = new LaserBeam(nave, anchoLaser, AssetManager.getInstancia().getLaserContTexture(), 1);
		LaserBeam rayo = new LaserBeam(muzzle[0],
				muzzle[1],
				width,
				muzzle[2],
				true,
				new Sprite(AssetManager.getInstancia().getLaserContTexture()),
				p);
		
    	rayo.configurarPulso(duracionPulso); // se apaga solo al vencer el TTL
        
    	manager.add(rayo);
    }

	@Override
	public Texture getDropTexture() {
		return AssetManager.getInstancia().getLGTexture(); //TODO
	}

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getLGSound();
	}
}
