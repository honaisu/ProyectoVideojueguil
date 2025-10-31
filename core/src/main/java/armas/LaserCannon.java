package armas;

import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.LaserBeam;
import armas.proyectiles.Projectile;
import logica.GameWorld;
import managers.AssetManager;
import pantallas.juego.GameScreen;
import personajes.Player;

public class LaserCannon extends Weapon {
    // Configuración del pulso
    private final float anchoLaser = 20f;     // grosor visual/efectivo
    private final float duracionPulso = 0.25f; // duración del rayo por disparo (segundos)
    private final int estiloRayo = 2;         // usa el “num” para grosor/estilo en RayHitbox

    public LaserCannon() {
        // cadencia controla cada cuánto puede volver a disparar (cooldown)
        super(1.8f, 15);
        
        super.setNombre("Laser Cannon");
        super.setSoundBala(AssetManager.getInstancia().getLaserGunSound());
    }

	@Override
	public Projectile crearProyectil(Rectangle r, float rotation) {
		LaserBeam rayo = new LaserBeam(r, rotation);
		rayo.configurarPulso(duracionPulso);
		return rayo;
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
		// TODO Agregar Laser? 
		juego.getGameManager().getProyectilManager().add(rayo);
	}*/
}
