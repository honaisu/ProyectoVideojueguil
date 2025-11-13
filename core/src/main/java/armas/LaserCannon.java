package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.LaserBeam;
import armas.proyectiles.Projectile;
import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import factories.SpriteFactory;
import managers.ProjectileManager;
import managers.assets.AssetManager;

public class LaserCannon extends Weapon {
    // Configuración del pulso
    private final int estiloRayo = 2;         // usa el “num” para grosor/estilo en RayHitbox

    public LaserCannon() {
    	super("Laser Cannon",
				25,													// daño
				0.9f, 												// cadencia
				15, 												// municion
				AssetManager.getInstancia().getSound(EGameSound.SHOOT_LASER_CANNON));	// sonido
    }

	@Override
	public void crearProyectil(Player p, ProjectileManager manager) {
		float muzzle[] = Projectile.calcularMuzzle(p, true);
		float width = 20f;     // grosor visual/efectivo
		float duracionPulso = 0.25f; // duración del rayo por disparo (segundos)
		
		LaserBeam rayo = new LaserBeam(muzzle[0],
				muzzle[1],
				width,
				muzzle[2],
				true,
				SpriteFactory.create(EProjectileType.LASER_GUN),
				p);
		rayo.configurarPulso(duracionPulso);
		
		manager.add(rayo);
		
	}

	@Override
	public Texture getDropTexture() {
		return AssetManager.getInstancia().getTexture(EDropType.LASER_CANNON); //TODO
	}

	@Override
	public Sound getPickupSound() {
		return AssetManager.getInstancia().getSound(EGameSound.DROP_LC);
	}

    /*
    @Override
    public void disparar(Player nave, GameWorld juego, float delta) {
        // avanza cooldown base
        actualizar(delta);
        // tiro a tiro: solo dispara cuando el gate de cadencia lo permite
        if (!puedeDisparar() || getMunicion() <= 0) return;
        restarMunicion(nave, juego, delta);
        // feedback y consumo
        getSoundBala().play(0.1f);
        // entra en cooldown; aunque se mantenga Z, no se disparará de nuevo hasta que termine
        reiniciarCooldown();
    }*/

    /*
	@Override
>>>>>>> origin/noche
	public void crearProyectil(Player nave, GameWorld juego) {
		LaserBeam rayo = new LaserBeam(nave, anchoLaser, AssetManager.getInstancia().getLaserContTexture(), estiloRayo);
		rayo.configurarPulso(duracionPulso); // fija TTL del pulso
<<<<<<< HEAD
		
		juego.getGameManager().getLaserManager().add(rayo);
	}
=======
		// TODO Agregar Laser? 
		juego.getGameManager().getProyectilManager().add(rayo);
	}*/

}
