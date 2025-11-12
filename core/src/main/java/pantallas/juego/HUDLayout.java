package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.Melee;
import personajes.Player;

public class HUDLayout {
	public void draw(SpriteBatch batch, BitmapFont font, Player player, int highScore, String roundName) {
		font.getData().setScale(2f);
		//antes font.draw(batch, "Vidas: " + player.getLife() + " Ronda: " + player.getRound(), 10, 30);
		//ahorita con GameWorld
		font.draw(batch, "Vidas: " + player.getLife() + " " + roundName, 10, 30);
		
        font.draw(batch, "HighScore: " + highScore, Gdx.graphics.getWidth() / 2 - 100, 30);
        
        
        font.draw(batch, "Arma: " + player.getWeapon().getNombre() ,
                Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 20);
        if (player.hasWeapon() && !(player.getWeapon() instanceof Melee)) {
            int mun = player.getWeapon().getMunicion();
            int max = player.getWeapon().getMunicionMax();
            font.draw(batch, "Municion: " + mun + " / " + max,
                    Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 60);
        }
	}
}