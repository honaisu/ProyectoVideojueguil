package pantallas.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.MainGame;
import pantallas.ScreenType;
import pantallas.opciones.NavigableOption;
import personajes.SkinJugador;

public class CustomizationScreen extends NavigableScreen {
    public CustomizationScreen(MainGame game) {
        super(game, SkinJugador.values());
    }

    @Override
    public void render(float delta) {
        this.update(delta);
        this.draw(game.getBatch(), game.getFont());
    }

	@Override
	protected void update(float delta) {
		// Entrada
		navegador.move(delta, Input.Keys.LEFT, Input.Keys.RIGHT);

        // Confirmar: guardar path y volver al menú
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	// TODO Volver a poner el poder elegir skin
            game.getPantallaManager().cambiarPantalla(ScreenType.MENU);
            return;
        }

        // Volver sin cambios
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getPantallaManager().cambiarPantalla(ScreenType.MENU);
            return;
        }
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		ScreenUtils.clear(Color.NAVY);
		
        batch.begin();
        font.getData().setScale(2.5f);
        font.draw(batch, "CHOOSE YOUR CHARACTER!!", 360, 600);

        font.draw(batch, "ENTER para confirmar   |   ESC para volver", 240, 100);

        font.getData().setScale(1.5f);
        int x = 360, y = 300;
        final float PREVIEW_HW = 128f;
        
		NavigableOption opcionActual = navegador.getCurrentSelection();
        
        int i = 1;
        String mensajeSeleccionado;
        for (SkinJugador skin : SkinJugador.values()) {
        	boolean seleccionado = opcionActual.equals(skin);

        	// Mensaje al estar apretando en uno
        	mensajeSeleccionado = "[" + i + "]";
        	if (seleccionado) mensajeSeleccionado += " SELECCIONADO";
        	
        	// Nombre de la skin
        	font.draw(batch, skin.getNombre(),
        			x - PREVIEW_HW/2f, y - PREVIEW_HW/2f + 200);
        	
        	// Sprite de la skin
        	batch.draw(skin.crearSprite(),
        			x - PREVIEW_HW/2f, y - PREVIEW_HW/2f, 
        			PREVIEW_HW, PREVIEW_HW);
        	
        	// Sólo imprime el mensaje original :D
            font.draw(batch, mensajeSeleccionado, x - 100, y - 70);
            
        	x += 250;
        	i++;
        }

        batch.end();
	}
}
