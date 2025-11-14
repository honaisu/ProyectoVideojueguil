package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enumeradores.EScreenType;
import interfaces.INavigableOption;
import logica.MainGame;
import pantallas.menus.NavigableScreen;
import pantallas.opciones.EPauseOption;

public class PauseScreen extends NavigableScreen {
	private final Color TRANSPARENTE = new Color(0f, 0f, 0f, 0.45f);
    private final Texture TEXTURA_PAUSA;
	
	public PauseScreen(MainGame game) {
		super(game, EPauseOption.values());
		
		// Rellena un cuadrado entero con solo blanco para que sirva de "textura"
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        TEXTURA_PAUSA = new Texture(pixmap);
        pixmap.dispose();
	}

	@Override
	protected void update(float delta) {
		// Navegación
		navegador.move(delta, Input.Keys.UP, Input.Keys.DOWN);
		INavigableOption opcionActual = navegador.getCurrentSelection();
		
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	if (opcionActual.ordinal() == EPauseOption.CONTINUAR.ordinal()) {	
        		this.resume();
        	} else {
        		getGame().setNextLevelToLoad(0); // si sale al menu, se reinicia el nivel y las rondas
        		getGame().getPantallaManager().cambiarPantalla(EScreenType.MENU);
                this.dispose();
                return;
            }
        }
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		// Overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.begin();
        batch.setColor(TRANSPARENTE);
        batch.draw(TEXTURA_PAUSA, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(Color.WHITE);
        
        float panelW = 520f, panelH = 260f;
        float px = (Gdx.graphics.getWidth() - panelW) / 2f;
        float py = (Gdx.graphics.getHeight() - panelH) / 2f;

        // Texto
        font.getData().setScale(2.0f);
        font.draw(batch, "PAUSA", px + 190, py + 200);
        navegador.drawOptions(batch, font);


        batch.end();

	}

}
