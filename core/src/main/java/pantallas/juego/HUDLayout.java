package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entidades.Player;

public class HUDLayout {
	public void draw(SpriteBatch batch, BitmapFont font, Player player, int highScore, String levelName, String roundName) {
		font.getData().setScale(2f);
		font.draw(batch, levelName, 10, 60);
		font.draw(batch, "Vidas: " + player.getHp() + " " + roundName, 10, 30);
        font.draw(batch, "HighScore: " + highScore, Gdx.graphics.getWidth() / 2 - 100, 30);
        
        font.draw(batch, "Arma: " + player.getWeapon().getNombre() ,
                Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 20);
        if (player.hasWeapon()) {
            int mun = player.getWeapon().getState().getAmmo();
            int max = player.getWeapon().getState().maxAmmo;
            font.draw(batch, "Municion: " + mun + " / " + max,
                    Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 60);
        }
	}
}