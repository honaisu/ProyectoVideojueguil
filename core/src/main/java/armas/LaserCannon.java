package armas;

import armas.proyectiles.LaserBeam;
import logica.AssetsLoader;
import pantallas.PantallaJuego;
import personajes.Jugador;

public class LaserCannon extends Weapon {

    // Configuración del pulso
    private final float anchoLaser = 20f;     // grosor visual/efectivo
    private final float duracionPulso = 0.25f; // duración del rayo por disparo (segundos)
    private final int estiloRayo = 2;         // usa el “num” para grosor/estilo en RayHitbox

    public LaserCannon() {
        // cadencia controla cada cuánto puede volver a disparar (cooldown)
        super("Laser Cannon",
        		1.8f,
        		15,
        		AssetsLoader.getInstancia().getLaserGunSound());
    }

    @Override
    public void disparar(Jugador nave, PantallaJuego pantalla, float delta) {
        // avanza cooldown base
        actualizar(delta);

        // tiro a tiro: solo dispara cuando el gate de cadencia lo permite
        if (!puedeDisparar() || getMunicion() <= 0) return;

        restarMunicion(nave, pantalla, delta);
        // feedback y consumo
        getSoundBala().play(0.1f);

        // entra en cooldown; aunque se mantenga Z, no se disparará de nuevo hasta que termine
        reiniciarCooldown();
    }

	@Override
	public void crearProyectil(Jugador nave, PantallaJuego juego) {
		LaserBeam rayo = new LaserBeam(nave, anchoLaser, AssetsLoader.getInstancia().getLaserContTexture(), estiloRayo);
		
		rayo.configurarPulso(duracionPulso); // fija TTL del pulso
		juego.agregarLaser(rayo);
		
	}
    
    
}
