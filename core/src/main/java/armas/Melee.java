package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import armas.proyectiles.Swing;
import managers.AssetManager;
import managers.ProjectileManager;

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
	public void crearProyectil(Rectangle r, float rotation, ProjectileManager manager) {
		float radians = (float) Math.toRadians(rotation);
		float centerX = r.getX() + r.getWidth() / 2;
        float centerY = r.getY() + r.getHeight() / 2;
        // distancia delante del jugador
        float length = r.getHeight() / 2 + 20; 
        float swingX = centerX + (float) Math.cos(radians) * length;
        float swingY = centerY + (float) Math.sin(radians) * length;
        swingActual = new Swing(swingX, swingY, rotation, length);
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

