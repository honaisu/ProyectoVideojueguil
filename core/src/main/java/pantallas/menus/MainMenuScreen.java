package pantallas.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.MainGame;
import pantallas.opciones.MenuOption;

public class MainMenuScreen extends NavigableScreen {
	public MainMenuScreen(MainGame game) {
	    super(game, MenuOption.values());
	}

	@Override
	protected void update(float delta) {
		// TODO Auto-generated method stub
		
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
	    float cx = 600f, y = 480f;
	    
	    navegador.draw(batch, font);

	    // Hint de controles
	    font.getData().setScale(1.0f);
	    font.setColor(0.9f, 0.9f, 0.9f, 0.9f);
	    
	    batch.end();
	}
}
