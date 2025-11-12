package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import armas.proyectiles.Swing;
import managers.AssetManager;
import managers.ProjectileManager;
import personajes.Player;

//Clase para el arma cuerpo a cuerpo
public class Melee extends Weapon {
	//usa un proyectil arqueado
    private Swing swingActual;

    public Melee() {
		super("Melee",
				50,												// daño
				1f, 											// cadencia
				99999, 											// municion
				AssetManager.getInstancia().getDisparoSound());	// sonido
	}
    
    public Swing getSwingActual() {
        return swingActual;
    }

	@Override
	public void crearProyectil(Player p, ProjectileManager manager) {
		float rotation = p.getRotation() + 90;
		float radians = (float) Math.toRadians(rotation + 90);
        float centerX = p.getSpr().getBoundingRectangle().getX() + p.getSpr().getBoundingRectangle().getWidth() / 2;
        float centerY = p.getSpr().getBoundingRectangle().getY() + p.getSpr().getBoundingRectangle().getHeight() / 2;
        // distancia delante del jugador
        float length = p.getSpr().getBoundingRectangle().getHeight() / 2 + 20; 
        float swingX = centerX + (float) Math.cos(radians) * length;
        float swingY = centerY + (float) Math.sin(radians) * length;
        swingActual = new Swing(swingX, swingY, rotation, length, p);
        manager.add(swingActual);
	}
	
	@Override
    public Texture getDropTexture() {
        return AssetManager.getInstancia().getMTexture();
    }

    @Override
    public Sound getPickupSound() {
        return AssetManager.getInstancia().getMSound();
    }
}

