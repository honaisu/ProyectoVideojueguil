package pantallas.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import interfaces.NavigableOption;
import logica.MainGame;
import pantallas.ScreenType;
import pantallas.opciones.PauseOption;

public class PauseScreen extends NavigableScreen {
	private final Color TRANSPARENTE = new Color(0f, 0f, 0f, 0.45f);
    private final Texture TEXTURA_PAUSA;
	
	public PauseScreen(MainGame game) {
		super(game, PauseOption.values());
		
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
		NavigableOption opcionActual = navegador.getCurrentSelection();
		
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	if (opcionActual.equals(PauseOption.CONTINUAR)) {        		
        		this.resume();
        	} else {
        		getGame().getPantallaManager().cambiarPantalla(ScreenType.MENU);
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
        font.getData().setScale(1.6f);

        
        navegador.drawOptions(batch, font);
        batch.end(); //lo movi al final para acá cerrar el batch
	}

}
