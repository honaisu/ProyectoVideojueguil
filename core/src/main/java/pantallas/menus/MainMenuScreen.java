package pantallas.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import interfaces.NavigableOption;
import logica.MainGame;
import pantallas.opciones.MainMenuOption;

public class MainMenuScreen extends NavigableScreen {
	public MainMenuScreen(MainGame game) {
	    super(game, MainMenuOption.values());
	}

	@Override
	protected void update(float delta) {
		navegador.move(delta, Input.Keys.UP, Input.Keys.DOWN);
		NavigableOption opcionActual = navegador.getCurrentSelection();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			opcionActual.ejecutar(getGame());
	    }
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		ScreenUtils.clear(Color.NAVY);

	    // Dibujo
	    batch.begin();

	    // Título
	    font.getData().setScale(2.5f);
	    font.setColor(0.95f, 0.9f, 0.85f, 1f);
	    font.draw(batch, "NOT HOTLINE MIAMI", 400f, 700f);

	    // Menú
	    font.getData().setScale(2.5f);
	    
	    navegador.drawOptions(batch, font);

	    // Hint de controles
	    font.getData().setScale(1.0f);
	    font.setColor(0.9f, 0.9f, 0.9f, 0.9f);
	    
	    batch.end();
	}
}
