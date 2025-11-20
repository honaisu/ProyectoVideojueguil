package pantallas.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import enumeradores.EPlayerSkin;
import enumeradores.EScreenType;
import factories.SpriteFactory;
import interfaces.INavigableOption;
import logica.MainGame;

public class CustomizationScreen extends NavigableScreen {
	private EPlayerSkin temporalSkin;
	
    public CustomizationScreen(MainGame game) {
        super(game, EPlayerSkin.values());
        temporalSkin = game.getPlayerSkin();
    }

	@Override
	protected void update(float delta) {
		// Entrada
		navegador.move(delta, Input.Keys.LEFT, Input.Keys.RIGHT);

        // Confirmar: guardar path y volver al menú
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	getGame().setPlayerSkin(temporalSkin);
        	getGame().getPantallaManager().cambiarPantalla(EScreenType.MENU);
            return;
        }

        // Volver sin cambios
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        	getGame().getPantallaManager().cambiarPantalla(EScreenType.MENU);
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
        float previewX = x - PREVIEW_HW/2f;
        float previewY = y - PREVIEW_HW/2f;
        
		INavigableOption opcionActual = navegador.getCurrentSelection();
        
        int i = 1;
        String mensajeSeleccionado;
        for (EPlayerSkin skin : EPlayerSkin.values()) {
        	boolean seleccionado = opcionActual.equals(skin);

        	// Mensaje al estar apretando en uno
        	mensajeSeleccionado = "[" + i + "]";
        	if (seleccionado) {
        		mensajeSeleccionado += " SELECCIONADO";
        		temporalSkin = skin;
        	}
        	
        	// Nombre de la skin
        	font.draw(batch, skin.getName(), previewX, previewY + 200);
        	
        	// Sprite de la skin
        	batch.draw(SpriteFactory.create(skin), previewX, previewY, PREVIEW_HW, PREVIEW_HW);
        	
        	// Sólo imprime el mensaje original :D
            font.draw(batch, mensajeSeleccionado, x - 100, y - 70);
            
        	x += 250;
        	i++;
        }

        batch.end();
	}
}
