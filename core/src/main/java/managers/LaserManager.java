package managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.proyectiles.LaserBeam;
import interfaces.MomentaneumManager;

public class LaserManager extends BaseManager implements MomentaneumManager {
	private final List<LaserBeam> lasers = new ArrayList<>();

    public List<LaserBeam> getLasers() {
        return lasers;
    }

    public void clear() {
        lasers.clear();
    }

}
