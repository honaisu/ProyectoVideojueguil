package armas;

import com.badlogic.gdx.graphics.g2d.Sprite;

import logica.GameWorld;
import managers.AssetManager;
import personajes.Player;

public class Flamethrower extends Weapon {
	public Flamethrower(int daño, float cadencia, int municion, Sprite spr) {
		super("Flamethrower", cadencia, municion, AssetManager.getInstancia().getDisparoSound());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void disparar(Player nave, GameWorld juego, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void crearProyectil(Player nave, GameWorld juego) {
		// TODO Auto-generated method stub
		
	}

}
