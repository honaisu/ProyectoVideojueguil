package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.SpaceNavigation;

/**ra la pantalla de game over:)
 * Clase que muest
 */
public class PantallaGameOver extends PantallaBase {
	private OrthographicCamera camera;

	// Efecto de muerte al entrar en Game Over
	private Sound sonidoMuerte;
	private boolean played = false;

	public PantallaGameOver(SpaceNavigation game) {
		super(game);
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
	    sonidoMuerte = Gdx.audio.newSound(Gdx.files.internal("muerteSound.mp3"));
	}

	@Override
	public void show() {
	    // Reproducir una sola vez al entrar con master*sfx
	    if (!played && sonidoMuerte != null) {
	        float mv = game.getMasterVolume();
	        float sfx = game.getSfxVolume();
	        sonidoMuerte.play(mv * sfx);
	        played = true;
	    }
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.FOREST);

		camera.update();
	    game.getBatch().setProjectionMatrix(camera.combined);

	    game.getBatch().begin();
	    game.getFont().draw(game.getBatch(), "Perdiste WUAAAJAJAJAJN !!! ", 120, 400, 400, 1, true);
	    game.getFont().draw(game.getBatch(), "Pincha en cualquier lado o presiona una tecla para reiniciar ...", 100, 300);
	    game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
	}
	
	@Override
	public void dispose() {
	    if (sonidoMuerte != null) sonidoMuerte.dispose();
	}
}