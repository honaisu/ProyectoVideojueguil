package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.AssetsLoader;
import logica.NotHotlineMiami;
import pantallas.BaseScreen;
import pantallas.ScreenType;

/**ra la pantalla de game over:)
 * Clase que muest
 */
public class GameOverScreen extends BaseScreen {

	// Efecto de muerte al entrar en Game Over
	private Sound sonidoMuerte = AssetsLoader.getInstancia().getMuerteSound();
	private boolean played = false;

	public GameOverScreen(NotHotlineMiami game) {
		super(game);
	}

	@Override
	public void show() {
	    // Reproducir una sola vez al entrar con master*sfx
	    if (!played && sonidoMuerte != null) {
	    	// TODO REEMPLAZAR GAME.GETSFXVOLUME() (MALA PRACTICA ROMPE PRINCIPIOS)
	        sonidoMuerte.play(game.getSfxVolume());
	        played = true;
	    }
	}

	@Override
	public void render(float delta) {
		this.update(delta);
		this.draw(game.getBatch(), game.getFont());
	}

	@Override
	protected void update(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			game.getPantallaManager().cambiarPantalla(ScreenType.JUEGO);
		}
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		ScreenUtils.clear(Color.NAVY);

	    batch.begin();
	    font.draw(batch, "Perdiste WUAAAJAJAJAJN !!!", 120, 400, 400, 1, true);
	    font.draw(batch, "Pincha en cualquier lado o presiona una tecla para reiniciar ...", 100, 300);
	    batch.end();
	}
	
	@Override
	public void dispose() {
	    if (sonidoMuerte != null) sonidoMuerte.dispose();
	}
}