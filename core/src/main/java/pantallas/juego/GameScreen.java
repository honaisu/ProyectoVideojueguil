package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logica.GameWorld;
import logica.MainGame;
import pantallas.BaseScreen;

public class GameScreen extends BaseScreen {
	private GameWorld world;

	public GameScreen(MainGame game) {
		super(game);
		
		this.world = new GameWorld();
	}

	@Override
	protected void update(float delta) {
		world.update(delta);
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		world.getPlayer().draw(batch);
		world.getGameManager().render(batch);
		batch.end();
	}

}
