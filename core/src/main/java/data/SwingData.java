package data;

public class SwingData extends ProjectileData {
	private boolean isBeam;
	private float duration;
	private float radius;
	private int width;
	private int height;

	public SwingData() {}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public float getRadius() {
		return radius;
	}

	public float getDuration() {
		return duration;
	}

	public boolean isBeam() {
		return isBeam;
	}
}
