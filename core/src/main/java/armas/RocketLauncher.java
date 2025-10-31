package armas;

<<<<<<< HEAD
import logica.GameWorld;
import logica.assets.AssetManager;
=======
import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.Projectile;
import logica.GameWorld;
import managers.AssetManager;
import pantallas.juego.GameScreen;
>>>>>>> origin/noche
import personajes.Player;


public class RocketLauncher extends Weapon {
	public RocketLauncher() {
		super("Rocket Launcher",
				0.3f,
				4,
				AssetManager.getInstancia().getDisparoSound());
	}

	@Override
<<<<<<< HEAD
	public void disparar(Player nave, GameWorld juego, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void crearProyectil(Player nave, GameWorld juego) {
		// TODO Auto-generated method stub
		
=======
	public Projectile crearProyectil(Rectangle r, float rotation) {
		// TODO Auto-generated method stub
		return null;
>>>>>>> origin/noche
	}
}
