package armas;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Arma {
	
	protected int daño;
	protected float cadencia;
	protected int municion;
	protected Sprite spr;
	
	public Arma(int daño, float cadencia, int municion, Sprite spr) {
		this.daño = daño;
		this.cadencia = cadencia;
		this.municion = municion;
		this.spr = spr;
	}
	
	protected int getDaño() {
		return daño;
	}
	protected float getCadencia() {
		return cadencia;
	}
	protected int getMunicion() {
		return municion;
	}
	protected Sprite getSpr() {
		return spr;
	}
	
	
}
