package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import armas.proyectiles.Projectile;
import armas.proyectiles.Swing;
import entidades.Player;
import enumeradores.recursos.EDropType;
import enumeradores.recursos.EGameSound;
import enumeradores.recursos.EProjectileType;
import factories.SpriteFactory;
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
				AssetManager.getInstancia().getSound(EGameSound.SHOOT));
    }
    
    public Swing getSwingActual() {
        return swingActual;
    }

	@Override
	public void crearProyectil(Player p, ProjectileManager manager) {
        float length = p.getSpr().getBoundingRectangle().getHeight() / 2 + 20; 
        float[] muzzle = Projectile.calcularMuzzle(p, true);
        
        float width = 120;
        float height = 60;
        float duration = 0.25f;
        
        swingActual = new Swing(muzzle,
        		length,
        		p,
        		SpriteFactory.create(EProjectileType.SWING, 96, 64),
        		width, height, 
        		duration,
        		false,
        		true);
        
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