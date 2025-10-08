package armas;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Hornet extends Arma {
	
	private float area;

	public Hornet(int daño, float cadencia, int municion, Sprite spr, float area) {
		super(daño, cadencia, municion, spr);
		
		this.setArea(area);
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

}
