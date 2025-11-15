package armas;

import com.badlogic.gdx.audio.Sound;

import entidades.proyectiles.Swing;
import entidades.Entity;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import interfaces.ITexture;
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
        float duration = 0.25f;
        float ratio = 50f;
        
        Swing swing = new Swing(p, state.getDamage(), ratio, duration, false);
        
        manager.add(swing);
	}
	
	@Override
    public ITexture getDropTexture() {
        return EDropType.MELEE;
    }

    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getSound(EGameSound.DROP_MELEE);
    }
}