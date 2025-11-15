package armas;

import com.badlogic.gdx.audio.Sound;

import entidades.proyectiles.Swing;
import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import interfaces.ITexture;
import managers.ProjectileManager;
import managers.assets.AssetManager;

//Clase para el arma cuerpo a cuerpo
public class Melee extends Weapon {
	//usa un proyectil arqueado
    private Swing swingActual;

    public Melee(){
		super("Melee",
				50,
				1f,
				99999,
				AssetManager.getInstancia().getSound(EGameSound.SHOOT),
				EDropType.MELEE);
    }
    
    public Swing getSwingActual() {
        return swingActual;
    }

	@Override
	public void crearProyectil(Player p, ProjectileManager manager) {
        float duration = 0.25f;
        float ratio = 50f;
        
        swingActual = new Swing(p.getSprite().getBoundingRectangle(),
        	stats.getDamage(), ratio, p.getRotation(), duration, false);
        
        manager.add(swingActual);
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