package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import personajes.Player;

public class HUDLayout {
	public void draw(SpriteBatch batch, BitmapFont font, Player player, int highScore) {
		font.getData().setScale(2f);
		font.draw(batch, "Vidas: " + player.getLife() + " Ronda: " + player.getRound(), 10, 30);
        font.draw(batch, "HighScore: " + highScore, Gdx.graphics.getWidth() / 2 - 100, 30);
        
        if (player.hasWeapon()) {
        	int municion = player.getWeapon().getMunicion();
        	int maxima = player.getWeapon().getMunicionMax();
        	font.draw(batch, "Municion: " + municion + " / " + maxima,
        			Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 20);
        }
	}
}
