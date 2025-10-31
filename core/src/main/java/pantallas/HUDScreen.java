package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.Melee;
import logica.MainGame;
import personajes.Player;

public class HUDScreen extends BaseScreen {
	private final Player jugador;

	public HUDScreen(MainGame game, Player jugador) {
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
		getGame().getFont().getData().setScale(2f);
		getGame().getFont().draw(batch, "Vidas: " + jugador.getVidas() + " Ronda: " + jugador.getRound(), 10, 30);
		getGame().getFont().draw(batch, "Score:" + jugador.getScore(), Gdx.graphics.getWidth() - 150, 30);
		getGame().getFont().draw(batch, "HighScore:" + getGame().getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);

        // Munición arma (si aplica)
        //if (nave.getArma() == null || nave.getArma() instanceof Melee) return;
		getGame().getFont().draw(batch, "Arma: " + jugador.getWeapon().getNombre() ,
                Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 20);
        if (jugador.getWeapon() != null && !(jugador.getWeapon() instanceof Melee)) {
            int mun = jugador.getWeapon().getMunicion();
            int max = jugador.getWeapon().getMunicionMax();
            getGame().getFont().draw(batch, "Municion: " + mun + " / " + max,
                    Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 60);
        }
	}
}
