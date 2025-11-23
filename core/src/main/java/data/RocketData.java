package data;

public class RocketData extends ProjectileData {
	private float acceleration;
	private float maxSpeed;
	private BulletData explosionData;
	
	public RocketData() {}
	
	public BulletData getExplosionData() {
		return explosionData;
	}
	public float getMaxSpeed() {
		return maxSpeed;
	}
	public float getAcceleration() {
		return acceleration;
	}
}
