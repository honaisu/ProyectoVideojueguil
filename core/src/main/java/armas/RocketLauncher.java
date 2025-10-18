package armas;

import logica.AssetsLoader;
import pantallas.PantallaJuego;
import personajes.Jugador;


public class RocketLauncher extends Weapon {

	public RocketLauncher() {
		super("Rocket Launcher",
				0.3f,
				4,
				AssetsLoader.getInstancia().getDisparoSound());
	}

	@Override
	public void disparar(Jugador nave, PantallaJuego juego, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void crearProyectil(Jugador nave, PantallaJuego juego) {
		// TODO Auto-generated method stub
		
	}
}
