package armas;

import com.badlogic.gdx.graphics.g2d.Sprite;

import logica.assets.AssetManager;
import pantallas.juego.GameScreen;
import personajes.Player;

public class Flamethrower extends Weapon {
	public Flamethrower(int daño, float cadencia, int municion, Sprite spr) {
		super("Flamethrower", cadencia, municion, AssetManager.getInstancia().getDisparoSound());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void disparar(Player nave, GameScreen juego, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void crearProyectil(Player nave, GameScreen juego) {
		// TODO Auto-generated method stub
		
	}

}
