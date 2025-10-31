package armas;

import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.Bullet;
import armas.proyectiles.Projectile;
import logica.GameWorld;
import managers.AssetManager;
import pantallas.juego.GameScreen;
import personajes.Player;

//Clase para un arma escopeta

public class HeavyMachineGun extends Weapon {
    public HeavyMachineGun() {
    	super(0.2f, 30);
        super.setNombre("Heavy Machine Gun");
    }
    
    public HeavyMachineGun(float cadencia, int municionMax) {
    	super(cadencia, municionMax);
    	super.setNombre("Heavy Machine Gun");
    }
    
    //clase sobrescrita
    /*
    @Override
    public void disparar(Player nave, GameWorld juego, float delta) {
        actualizar(delta);
        
        //sin balas
        if (getMunicion() <= 0) return;
        
        //restar municion por cadencia
        restarMunicion(nave);
        getSoundBala().play(0.1f);
        
        if (puedeDisparar()) reiniciarCooldown();
    }*/
    
    //crea la bala de la metralleta con direccion respecto al jugador
    @Override
    public Projectile crearProyectil(Rectangle r, float rotation) {
    	float radians = (float) Math.toRadians(rotation);
        float centerX = r.getX() + r.getWidth() / 2;
        float centerY = r.getY() + r.getHeight() / 2;
        float length = r.getHeight() / 2;

        float bulletX = centerX + (float) Math.cos(radians) * length;
        float bulletY = centerY + (float) Math.sin(radians) * length;

        Bullet bala = new Bullet(
        		// posicion de la bala
        		bulletX, bulletY,								
        		// dirección de la bala
        		rotation,							
        		// velocidad levemente aleatoria
        		10f,											
        		// textura de la bala
        		AssetManager.getInstancia().getBalaTexture());	
        
        return bala;
    }
}
