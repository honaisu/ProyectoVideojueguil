package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.AssetsLoader;
import logica.NotHotlineMiami;

/**ra la pantalla de game over:)
 * Clase que muest
 */
public class PantallaGameOver extends PantallaBase {

	// Efecto de muerte al entrar en Game Over
	private Sound sonidoMuerte = AssetsLoader.getInstancia().getMuerteSound();
	private boolean played = false;

	public PantallaGameOver(NotHotlineMiami game) {
		super(game);
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
		this.update(delta);
		this.draw();
	}
	
	@Override
	public void dispose() {
	    if (sonidoMuerte != null) sonidoMuerte.dispose();
	}

	@Override
	protected void update(float delta) {
		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0);
			ss.resize(1200, 800);
			game.setScreen(ss);
			this.dispose();
		}
	}

	@Override
	protected void draw() {
		ScreenUtils.clear(Color.FOREST);

	    game.getBatch().begin();
	    game.getFont().draw(game.getBatch(), "Perdiste WUAAAJAJAJAJN !!! ", 120, 400, 400, 1, true);
	    game.getFont().draw(game.getBatch(), "Pincha en cualquier lado o presiona una tecla para reiniciar ...", 100, 300);
	    game.getBatch().end();
	}
}