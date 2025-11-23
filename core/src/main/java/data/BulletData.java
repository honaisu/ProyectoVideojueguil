package data;

public class BulletData extends ProjectileData {
	private float lifespan = -1f;
	private int width;
	private int scale = 1;
	private int spread = 0;
	private int pellets = 1;

	public BulletData() {}

	public int getPellets() {
		return pellets;
	}

	public int getSpread() {
		return spread;
	}

	public int getScale() {
		return scale;
	}

	public int getWidth() {
		return width;
	}

	public float getLifespan() {
		return lifespan;
	}
}
