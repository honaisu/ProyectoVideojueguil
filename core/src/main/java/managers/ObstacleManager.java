package managers;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entidades.obstaculos.DamageHazard;
import entidades.obstaculos.SolidObstacle;
import enumeradores.recursos.EBackgroundType;
import factories.ObstacleFactory;

public class ObstacleManager {

    private final List<DamageHazard> hazards = new ArrayList<>();
    private final List<SolidObstacle> solids = new ArrayList<>();
    private final ObstacleFactory factory = new ObstacleFactory(); 

    public ObstacleManager() { }
    
    public void spawnObstacles(int hazardCount, int solidCount, EBackgroundType bgType) {
        hazards.clear();
        solids.clear();
        
        // Llama a la fábrica para crear los obstaculos que hacen daño (puas y charco)
        for (int i = 0; i < hazardCount; i++) {
            hazards.add(factory.createRandomHazard(bgType));
        }
        // Llama a la fábrica para crear los "sólidos" (bloque)
        for (int i = 0; i < solidCount; i++) {
            solids.add(factory.createRandomSolid(bgType));
        }
    }

    public void update(float delta) { }

    public void render(SpriteBatch batch) {
        // Dibuja primero los charcos/puas  //TODO ver bien esto
        for (DamageHazard h : hazards) {
            h.draw(batch);
        }
        // Dibuja los bloques sólidos encima
        for (SolidObstacle s : solids) {
            s.draw(batch);
        }
    }
    
    public List<DamageHazard> getHazards() { return hazards; }
    public List<SolidObstacle> getSolids() { return solids; }
}