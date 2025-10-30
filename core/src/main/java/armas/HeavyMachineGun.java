package armas;

import armas.proyectiles.Bullet;
import logica.GameWorld;
import managers.AssetManager;
import pantallas.juego.GameScreen;
import personajes.Player;

//Clase para un arma escopeta

public class HeavyMachineGun extends Weapon {

    public HeavyMachineGun() {
        super("Heavy Machine Gun",
        		0.2f,												// cadencia
        		30, 											// municion
        		AssetManager.getInstancia().getDisparoSound()); // sonido
    }
    
  //clase sobrescrita
    @Override
    public void disparar(Player nave, GameWorld juego, float delta) {
        actualizar(delta);
        
        //sin balas
        if (getMunicion() <= 0) return;
        
        //restar municion por cadencia
        restarMunicion(nave, juego, delta);
        getSoundBala().play(0.1f);
        
        if (puedeDisparar()) reiniciarCooldown();
    }
    
    //crea la bala de la metralleta con direccion respecto al jugador
    @Override
    public void crearProyectil(Player nave, GameWorld juego) {
        float radians = (float) Math.toRadians(nave.getRotation() + 90);
        float centerX = nave.getSpr().getX() + nave.getSpr().getWidth() / 2;
        float centerY = nave.getSpr().getY() + nave.getSpr().getHeight() / 2;
        float length = nave.getSpr().getHeight() / 2;

        float bulletX = centerX + (float) Math.cos(radians) * length;
        float bulletY = centerY + (float) Math.sin(radians) * length;

        Bullet bala = new Bullet(
        		bulletX, bulletY,								// posicion de la bala
        		nave.getRotation(),								// dirección de la bala
        		10f,											// velocidad levemente aleatoria
        		AssetManager.getInstancia().getBalaTexture());	// textura de la bala
        
        juego.getGameManager().getBulletManager().add(bala);
    }

}
