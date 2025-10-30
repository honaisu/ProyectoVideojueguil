package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enumeradores.ScreenType;
import logica.GameWorld;
import logica.MainGame;
import pantallas.BaseScreen;
import pantallas.HUDScreen;

/**
 * Clase que representa una pantalla del juego, con su propio mundo.
 */
public class GameScreen extends BaseScreen {
	private final GameWorld world;
	private final HUDScreen hud = new HUDScreen();

	public GameScreen(MainGame game) {
		super(game);
		this.world = new GameWorld();
	}

	@Override
	protected void update(float delta) {
		world.update(delta);
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			getGame().getPantallaManager().cambiarPantalla(ScreenType.PAUSA);
		}
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		world.getPlayer().draw(batch);
		world.getGameManager().render(batch);
		hud.draw(batch, font, world);
		batch.end();
	}
}
