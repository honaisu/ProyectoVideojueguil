package data;

public class FlameData extends ProjectileData {
	private int spread;
	private float scale;
	private float maxScale;
	private float maxDistance;

	public FlameData() {}

	public float getMaxDistance() {
		return maxDistance;
	}

	public float getMaxScale() {
		return maxScale;
	}

	public float getScale() {
		return scale;
	}

	public int getSpread() {
		return spread;
	}
}
