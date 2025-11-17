package armas;

import com.badlogic.gdx.audio.Sound;

import entidades.proyectiles.Swing;
import entidades.Entity;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import managers.ProjectileManager;
import managers.assets.AssetManager;

/**
 * Clase para el arma cuerpo a cuerpo
 */
public class Melee extends Weapon {
    public Melee(){
		super("Melee",
				50,
				1f,
				99999,
				AssetManager.getInstancia().getSound(EGameSound.SHOOT),
				EDropType.MELEE);
    }
    
	@Override
	public void crearProyectil(Entity p, ProjectileManager manager) {
		float length = p.getSprite().getBoundingRectangle().getHeight() / 2 + 20;
        float duration = 0.25f;
        int width = 120;
        int height = 60;
        
        Swing swing = new Swing(p, state.getDamage(), duration, EProjectileType.SWING, width, height, length, true, false);
        
        manager.add(swing);
	}

    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getSound(EGameSound.DROP_MELEE);
    }
}