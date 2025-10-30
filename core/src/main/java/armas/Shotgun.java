package armas;

import java.util.Random;

import armas.proyectiles.Bullet;
import logica.GameWorld;
import managers.AssetManager;
import pantallas.juego.GameScreen;
import pantallas.juego.PantallaJuego;
import personajes.Jugador;
import personajes.Player;

public class Shotgun extends Weapon {

	public Shotgun() {
		super("Shotgun",
				3f, 											// cadencia
				8, 												// municion
				AssetManager.getInstancia().getDisparoSound());	// sonido
	}

	@Override
	public void disparar(Player nave, GameWorld juego, float delta) {
		actualizar(delta);
        
		// sin balas
        if (getMunicion() <= 0) return;
        
        // restar municion por cadencia
        restarMunicion(nave, juego, delta);
        
        if (puedeDisparar()) reiniciarCooldown();
	}
	
	//crea la dispersion de las balas de la escopeta con direccion respecto al jugador
	@Override
	public void crearProyectil(Player nave, GameWorld juego) {
        float radians = (float) Math.toRadians(nave.getRotation() + 90);
        float centerX = nave.getSpr().getX() + nave.getSpr().getWidth() / 2;
        float centerY = nave.getSpr().getY() + nave.getSpr().getHeight() / 2;
        float length = nave.getSpr().getHeight() / 2;

        float bulletX = centerX + (float) Math.cos(radians) * length;
        float bulletY = centerY + (float) Math.sin(radians) * length;
        
        //simula una dispersion de balas aleatorias
        Random r = new Random();
        
        int pellets = 8;	// perdigones
        int spread = 15; 	// grados de apertura

        for (int i = 0; i < pellets; i++) {
            // Ángulo con desviación aleatoria
            float angle = nave.getRotation() + (r.nextFloat() * spread * 2 - spread); // -15 a +15 grados

            Bullet bala = new Bullet(
                bulletX, bulletY,								// posicion de la bala
                angle,                     						// dirección de la bala
                10f + r.nextInt(4),        						// velocidad levemente aleatoria
                AssetManager.getInstancia().getBalaTexture()	// textura de la bala
            );
            
            // TODO Agregar Bala...
            //juego.agregarBala(bala);
        }
        getSoundBala().play(0.1f);
    }
}
