package armas;

import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.Bullet;
import armas.proyectiles.Projectile;

/**
 * Clase para un arma escopeta
 */
public class HeavyMachineGun extends Weapon {
    public HeavyMachineGun() {
    	super(0.2f, 30);
        super.setNombre("Heavy Machine Gun");
    }
    
    public HeavyMachineGun(float cadencia, int municionMax) {
    	super(cadencia, municionMax);
    	super.setNombre("Heavy Machine Gun");
    }
    
    //crea la bala de la metralleta con direccion respecto al jugador
    @Override
    public Projectile crearProyectil(Rectangle r, float rotation) {
    	float radians = (float) Math.toRadians(rotation);
        float centerX = r.getX() + r.getWidth() / 2;
        float centerY = r.getY() + r.getHeight() / 2;
        float length = r.getHeight() / 2;

        float bulletX = centerX + (float) Math.cos(radians) * length;
        float bulletY = centerY + (float) Math.sin(radians) * length;

        // TODO Arreglar valores hardcodeados...
        Bullet bala = new Bullet(bulletX, bulletY, 20f, rotation, 10f);
        
        return bala;
    }
}
