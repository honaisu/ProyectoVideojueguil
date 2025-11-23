package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import enumeradores.EScreenType;
import enumeradores.recursos.EGameSound;
import logica.MainGame;
import managers.assets.AssetManager;
import pantallas.BaseScreen;

/**ra la pantalla de game over:)
 * Clase que muest
 */
public class GameOverScreen extends BaseScreen {
	// Efecto de muerte al entrar en Game Over
	private Sound sonidoMuerte = AssetManager.getInstancia().getSound(EGameSound.DEATH);
	private boolean played = false;

	public GameOverScreen(MainGame game) {
		super(game);
		//sonidoMuerte.setVolume(0, game.getVolumen().getSfxVolume());
	}

	@Override
	public void show() {
	    // Reproducir una sola vez al entrar con master*sfx
	    if (!played && sonidoMuerte != null) {
	        played = true;
	    }
	}
	
	@Override
	protected void update(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			getGame().getPantallaManager().cambiarPantalla(EScreenType.GAME);
		}
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		ScreenUtils.clear(Color.NAVY);

	    batch.begin();
	    font.draw(batch, "GAME OVER", 0, 500, Gdx.graphics.getWidth(), Align.center, true);
	    font.draw(batch, "Presione cualquier tecla", 0, 70, Gdx.graphics.getWidth() - 20, Align.right, true);
	    font.draw(batch, "para continuar", 0, 40, Gdx.graphics.getWidth() - 20, Align.right, true);
	    batch.end();
	}
	
	@Override
	public void dispose() {
	    if (sonidoMuerte != null) sonidoMuerte.dispose();
	}
}