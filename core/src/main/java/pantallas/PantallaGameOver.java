package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.SpaceNavigation;

/**ra la pantalla de game over:)
 * Clase que muest
 */
public class PantallaGameOver extends PantallaBase {
	private SpaceNavigation game;
	private OrthographicCamera camera;

	public PantallaGameOver(SpaceNavigation game) {
		super(game);
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.FOREST);

		camera.update();

		game.getBatch().begin();
		game.getFont().draw(game.getBatch(), "Game Over !!! ", 120, 400,400,1,true);
		game.getFont().draw(game.getBatch(), "Pincha en cualquier lado para reiniciar ...", 100, 300);
	
		game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
	}
}