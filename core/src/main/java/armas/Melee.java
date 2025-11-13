package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import armas.proyectiles.Projectile;
import armas.proyectiles.Swing;
import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import managers.ProjectileManager;
import managers.assets.AssetManager;

//Clase para el arma cuerpo a cuerpo
public class Melee extends Weapon {
	//usa un proyectil arqueado
    private Swing swingActual;

    public Melee() {
		super("Melee",
				50,												// daño
				1f, 											// cadencia
				99999); 											// municion
	}
    
    public Swing getSwingActual() {
        return swingActual;
    }

	@Override
	public void crearProyectil(Player p, ProjectileManager manager) {
        float length = p.getSpr().getBoundingRectangle().getHeight() / 2 + 20; 
        float[] muzzle = Projectile.calcularMuzzle(p, true);
        
        swingActual = new Swing(muzzle[0], muzzle[1], muzzle[2], length, p);
        manager.add(swingActual);
	}
	
	@Override
    public Texture getDropTexture() {
        return AssetManager.getInstancia().getTexture(EDropType.MELEE);
    }

    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getSound(EGameSound.DROP_MELEE);
    }
}

