package pantallas;

import com.badlogic.gdx.Gdx;

import logica.GameWorld;
import logica.MainGame;

public class HUDScreen {
	public void draw(MainGame game, GameWorld world) {
		game.getFont().getData().setScale(2f);
        game.getFont().draw(game.getBatch(), "Vidas: " + world.getPlayer().getLife() + " Ronda: " + world.getPlayer().getRound(), 10, 30);
        game.getFont().draw(game.getBatch(), "HighScore: " + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
        
        int municion = world.getPlayer().getWeapon().getMunicion();
        int maxima = world.getPlayer().getWeapon().getMunicionMax();
        game.getFont().draw(game.getBatch(), "Municion: " + municion + " / " + maxima,
                Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 20);
        /* TODO FULL Modificar esto. Sobre todo con el 'instance of'.
        if (player.getArma() != null && !(player.getArma() instanceof Melee)) {
            int mun = player.getArma().getMunicion();
            int max = player.getArma().getMunicionMax();
            game.getFont().draw(batch, "Municion: " + mun + " / " + max,
                    Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 20);
        }*/
	}
}
