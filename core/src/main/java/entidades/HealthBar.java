package entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class HealthBar {
	
	private Creature owner;			// La criatura a la que pertenece esta barra
    private TextureRegion[] frames;	// Arreglo con los 11 frames
    private Sprite displaySprite;	// El sprite que se va a dibujar
    private boolean isPlayer;		// Si es jugador o no
    
    
    public HealthBar(Creature owner, TextureRegion[] frames, boolean isPlayer) {
        if (frames == null || frames.length != 11) { //TODO
            throw new IllegalArgumentException("HealthBar requiere exactamente 11 frames.");
        }
        
        this.owner = owner;
        this.frames = frames;
        this.isPlayer = isPlayer;
        
        this.displaySprite = new Sprite(frames[0]);

    }
    
    public void update() {
        //Porcentaje de vida
        float healthPercent = (float) owner.getHp() / (float) owner.totalHp;
        if (owner.isDead()) {
            healthPercent = 0f;
        }

        //Calcular el índice del frame
        int frameIndex = 10 - (int) Math.ceil(healthPercent * 10);

        frameIndex = MathUtils.clamp(frameIndex, 0, 10);

        //Asignar el sprite
        displaySprite.setRegion(frames[frameIndex]);

        // Posicion de la healthbar
        if(isPlayer) { //Jugador en el HUD
        	Vector2 hudPos = new Vector2(10,0);
        	displaySprite.setBounds(hudPos.x, hudPos.y, 300, 30);
        }else { //Debajo de los enemigos
        	Vector2 ownerPos = owner.getPosition();
        	float ownerWidth = owner.getSprite().getWidth();
            float barWidth = 64f;
            float centeredX = ownerPos.x + (ownerWidth / 2) - (barWidth / 2);
            
            // 3. Aplicamos la nueva X
            displaySprite.setBounds(centeredX, ownerPos.y - 15, barWidth, 8);
        }
    }
    
    public void draw(SpriteBatch batch) {
        displaySprite.draw(batch);
    }

}
