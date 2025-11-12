package managers;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import personajes.WeaponDrop;

public class DropManager {

    private List<WeaponDrop> drops;

    public DropManager() {
        drops = new ArrayList<>();
    }

    public void add(WeaponDrop drop) {
        drops.add(drop);
    }

    public void update(float delta) {
        for (WeaponDrop drop : drops) {
            drop.update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        for (WeaponDrop drop : drops) {
            drop.draw(batch);
        }
    }

    public List<WeaponDrop> getDrops() {
        return drops;
    }

    public void clear() {
        drops.clear();
    }
}