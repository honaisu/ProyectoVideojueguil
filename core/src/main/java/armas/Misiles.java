package armas;

import com.badlogic.gdx.graphics.g2d.Sprite;

import pantallas.PantallaJuego;
import personajes.Jugador;

public class Misiles extends Arma {

	public Misiles(int daño, float cadencia, int municion, Sprite spr) {
		super(daño, cadencia, municion, spr);
	}

	@Override
	public void disparar(Jugador nave, PantallaJuego juego, float delta) {
		// TODO Auto-generated method stub
		
	}
}
