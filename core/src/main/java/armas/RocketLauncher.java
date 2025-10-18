package armas;

import logica.assets.AssetManager;
import pantallas.juego.GameScreen;
import personajes.Player;


public class RocketLauncher extends Weapon {

	public RocketLauncher() {
		super("Rocket Launcher",
				0.3f,
				4,
				AssetManager.getInstancia().getDisparoSound());
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
