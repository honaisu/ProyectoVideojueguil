package factories;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import entidades.obstaculos.DamageHazard;
import entidades.obstaculos.SolidObstacle;
import entidades.obstaculos.DamageHazard.DamageType;
import enumeradores.recursos.EBackgroundType;
import enumeradores.recursos.EObstacleSkin;

public class ObstacleFactory {

	private Random r = new Random();

	private final int MARGIN = 100;

	// Creador de un obstaculo que hace daño, aleatoriamente y dpendiendo el nivel
	// pone charcos o no (por el momento solo el de veneno y lava)
	public DamageHazard createRandomHazard(EBackgroundType bgType) {
		int screenW = Gdx.graphics.getWidth();
		int screenH = Gdx.graphics.getHeight();
		float x = MARGIN + r.nextInt(screenW - 2 * MARGIN);
		float y = MARGIN + r.nextInt(screenH - 2 * MARGIN);

		// condiciones específicas con el tema de la skin de cada charco
		switch (bgType) {
		case ONE:
		default:
			return new DamageHazard(x, y, EObstacleSkin.GRASS_SPIKE, DamageType.SPIKE, 10);

		case TWO:
			if (r.nextBoolean()) {
				// (Daño 10, usa skin SPIKE)
				return new DamageHazard(x, y, EObstacleSkin.GRASS_SPIKE, DamageType.SPIKE, 10);
			} else {
				// (Daño 1, usa skin POISON_PUDDLE)
				return new DamageHazard(x, y, EObstacleSkin.POISON_PUDDLE, DamageType.PUDDLE, 1);
			}

		case THREE:
			return new DamageHazard(x, y, EObstacleSkin.WATER_SPIKE, DamageType.SPIKE, 10);

		case FOUR:
			if (r.nextBoolean()) {
				// (Daño 10, usa skin SPIKE)
				return new DamageHazard(x, y, EObstacleSkin.FIRE_SPIKE, DamageType.SPIKE, 10);
			} else {
				// (Daño 1, usa skin LAVA_PUDDLE)
				return new DamageHazard(x, y, EObstacleSkin.LAVA_PUDDLE, DamageType.PUDDLE, 1);
			}

		case FIVE:
			return new DamageHazard(x, y, EObstacleSkin.ICE_SPIKE, DamageType.SPIKE, 10);
		}
	}

	// Crea los bloques solidos aleatoriamente
	public SolidObstacle createRandomSolid(EBackgroundType bgType) {
		int screenW = Gdx.graphics.getWidth();
		int screenH = Gdx.graphics.getHeight();

		float x = MARGIN + r.nextInt(screenW - 2 * MARGIN);
		float y = MARGIN + r.nextInt(screenH - 2 * MARGIN);

		switch (bgType) {
		case THREE:
		case FIVE:
			return new SolidObstacle(x, y, EObstacleSkin.WATER_BOX);
		case TWO:
		case FOUR:
			return new SolidObstacle(x, y, EObstacleSkin.IRON_BOX);
		case ONE:
		default:
			return new SolidObstacle(x, y);
		}
	}
}