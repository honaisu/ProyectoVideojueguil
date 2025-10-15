package armas;

import com.badlogic.gdx.graphics.g2d.Sprite;

import pantallas.juego.PantallaJuego;
import personajes.Jugador;

public class Laser extends Arma {

	public Laser(int daño, float cadencia, int municion, Sprite spr) {
		super(daño, cadencia, municion, spr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void disparar(Jugador nave, PantallaJuego juego, float delta) {
		// TODO Auto-generated method stub
		
	}

}
