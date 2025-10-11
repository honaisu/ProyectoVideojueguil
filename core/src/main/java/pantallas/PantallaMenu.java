package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import logica.SpaceNavigation;


public class PantallaMenu extends PantallaBase {
	private SpaceNavigation game;
	private OrthographicCamera camera;
	
	public PantallaMenu(SpaceNavigation game) {
		super(game);
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.FOREST);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);
		// Empieza el código para dibujar
		game.getBatch().begin();	
		
		game.getFont().draw(game.getBatch(), "Bienvenido a Not Hotline Miami !", 140, 400);
		game.getFont().draw(game.getBatch(), "Pincha en cualquier lado o presiona cualquier tecla para comenzar ...", 100, 300);
		
		// Termina de dibujar lo de "en medio"
		game.getBatch().end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
	}
}