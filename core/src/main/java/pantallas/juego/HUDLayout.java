package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import entidades.Player;
import factories.SpriteFactory;

public class HUDLayout {
	public void draw(SpriteBatch batch, BitmapFont font, Player player, int highScore, String levelName, String roundName) {
		float x_derecha = Gdx.graphics.getWidth() - 20f;
		
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
		font.draw(batch, "HighScore: " + highScore, Gdx.graphics.getWidth() / 2 - 100, 30);
        
		//ARMAS
		font.setColor(Color.GOLDENROD);
        font.draw(batch, player.getWeapon().getName(),
                Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 20);
        Sprite spr = SpriteFactory.create(player.getWeapon().getDropTexture());
        spr.scale(1f);
        spr.setPosition(Gdx.graphics.getWidth() - 390, Gdx.graphics.getHeight()- 40);
        spr.draw(batch);
        if (player.hasWeapon()) {
            int mun = player.getWeapon().getState().getAmmo();
            int max = player.getWeapon().getState().maxAmmo;
            font.draw(batch, "Municion: " + mun + " / " + max,
                    Gdx.graphics.getWidth() - 390, Gdx.graphics.getHeight() - 60);
        }
	}
}