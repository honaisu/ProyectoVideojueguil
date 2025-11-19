package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import entidades.Player;
import factories.SpriteFactory;

public class HUDLayout {
	private final Sprite backgroundSprite;
	
	public HUDLayout() {
        Texture pixelTexture = new Texture(Gdx.files.internal("textures/pixel.png")); 
        
        this.backgroundSprite = new Sprite(pixelTexture);
        this.backgroundSprite.setColor(new Color(0.1f, 0.1f, 0.1f, 0.7f)); // Gris oscuro, 70% opacidad
    }
	
	
	public void draw(SpriteBatch batch, BitmapFont font, Player player, int highScore, String levelName, String roundName) {
		float x_derecha = Gdx.graphics.getWidth() - 20f;
		
		final float HUD_HEIGHT = 100f;
        
        this.backgroundSprite.setBounds(
            0, 
            0, 
            Gdx.graphics.getWidth(), 
            HUD_HEIGHT
        );
        this.backgroundSprite.draw(batch);

        // 2. DIBUJAR LOS ELEMENTOS DEL HUD ENCIMA (Ajustando sus coordenadas Y)
		
		//NIVEL
		font.setColor(Color.SKY);
		font.getData().setScale(2f);
		font.draw(batch, levelName, x_derecha, 60, 0, Align.right, false);
		font.draw(batch, roundName, x_derecha, 30, 0, Align.right, false);
		
		//VIDA
		player.getHealthBar().draw(batch);
		font.setColor(Color.FIREBRICK);
		font.draw(batch, player.getHp() + "/100", 320, 30);
		
        //SCORE
		font.setColor(Color.GOLD);
		font.draw(batch, "HighScore: " + highScore, 30, 70);
        
		//ARMAS
		font.setColor(Color.GOLDENROD);
        font.draw(batch, player.getWeapon().getName(),
                Gdx.graphics.getWidth()/2, 70);
        Sprite spr = SpriteFactory.create(player.getWeapon().getDropTexture());
        spr.scale(1f);
        spr.setPosition(Gdx.graphics.getWidth()/2-40, 48);
        spr.draw(batch);
        if (player.hasWeapon()) {
            int mun = player.getWeapon().getState().getAmmo();
            int max = player.getWeapon().getState().maxAmmo;
            font.draw(batch, "Municion: " + mun + " / " + max,
                    Gdx.graphics.getWidth()/2-60, 30);
        }
	}
}