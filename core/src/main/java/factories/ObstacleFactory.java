package factories;

import java.util.Random;
import com.badlogic.gdx.Gdx;
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
		// Nivel 2 (Veneno)
		if (bgType == EBackgroundType.TWO) {
			// 50% Pua, 50% Charco de Veneno
			if (r.nextBoolean()) {
				// (Daño 10, usa skin SPIKE)
				return new DamageHazard(x, y, EObstacleSkin.SPIKE, DamageType.SPIKE, 10);
			} else {
				// (Daño 1, usa skin POISON_PUDDLE)
				return new DamageHazard(x, y, EObstacleSkin.POISON_PUDDLE, DamageType.PUDDLE, 1);
			}

			// Nivel 4 (Lava)
		} else if (bgType == EBackgroundType.FOUR) {
			// 50% Pua, 50% Charco de Lava
			if (r.nextBoolean()) {
				// (Daño 10, usa skin SPIKE)
				return new DamageHazard(x, y, EObstacleSkin.SPIKE, DamageType.SPIKE, 10);
			} else {
				// (Daño 1, usa skin LAVA_PUDDLE)
				return new DamageHazard(x, y, EObstacleSkin.LAVA_PUDDLE, DamageType.PUDDLE, 1);
			}

		} else {
			// Nivel 1 - 3 - 5
			// SOLO pueden tener Puas por el momento
			return new DamageHazard(x, y, EObstacleSkin.SPIKE, DamageType.SPIKE, 10);
		}
	}

	// Crea los bloques solidos aleatoriamente
	public SolidObstacle createRandomSolid(EBackgroundType bgType) {

		int screenW = Gdx.graphics.getWidth();
		int screenH = Gdx.graphics.getHeight();

		float x = MARGIN + r.nextInt(screenW - 2 * MARGIN);
		float y = MARGIN + r.nextInt(screenH - 2 * MARGIN);

		return new SolidObstacle(x, y);
	}
}