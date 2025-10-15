package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.Melee;
import logica.MainGame;
import personajes.Jugador;

public class HUDScreen extends BaseScreen {
	private final Jugador jugador;

	public HUDScreen(MainGame game, Jugador jugador) {
		super(game);
		this.jugador = jugador;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		font.getData().setScale(2f);
        font.draw(batch, "Vidas: " + jugador.getVidas() + " Ronda: " + jugador.getRonda(), 10, 30);
        font.draw(batch, "Score:" + jugador.getScore(), Gdx.graphics.getWidth() - 150, 30);
        font.draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
        
        if (jugador.getArma() != null && !(jugador.getArma() instanceof Melee)) {
            int mun = jugador.getArma().getMunicion();
            int max = jugador.getArma().getMunicionMax();
            game.getFont().draw(batch, "Municion: " + mun + " / " + max,
                    Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 20);
        }
	}
}
