package armas;

import logica.GameWorld;
import logica.assets.AssetManager;
import personajes.Player;


public class RocketLauncher extends Weapon {

	public RocketLauncher() {
		super("Rocket Launcher",
				0.3f,
				4,
				AssetManager.getInstancia().getDisparoSound());
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
