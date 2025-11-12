package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enumeradores.EScreenType;
import logica.GameWorld;
import logica.MainGame;
import pantallas.BaseScreen;

/**
 * Clase que representa una pantalla del juego, con su propio mundo.
 */
public class GameScreen extends BaseScreen {
	private final GameWorld world;
	private final HUDLayout hud = new HUDLayout();

	public GameScreen(MainGame game) {
		super(game);
		this.world = new GameWorld();
	}

	@Override
	protected void update(float delta) {
		world.update(delta);
		world.getGameLogicHandler().update(
				delta, 
				world.getPlayer()
				);
		
		// Sistema de Pausa
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			getGame().getPantallaManager().cambiarPantalla(EScreenType.PAUSA);
		}
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		world.getPlayer().draw(batch);				//TODO ⇧⇩ para que el player superponga las balas de las armas
		world.getGameLogicHandler().render(batch);
		hud.draw(batch, font, world.getPlayer(), getGame().getHighScore());
		batch.end();
	}
}
