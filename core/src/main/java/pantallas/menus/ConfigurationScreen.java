package pantallas.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import enumeradores.EScreenType;
import enumeradores.opciones.EConfigurationOption;
import interfaces.INavigableOption;
import logica.MainGame;
import logica.Volumen;

public class ConfigurationScreen extends NavigableScreen {
	private Volumen volumenTemporal = new Volumen();
    
    public ConfigurationScreen(MainGame game) {
        super(game, EConfigurationOption.values());
    }
    
    @Override
    protected void update(float delta) {
        // Navegación vertical
    	// TODO arreglar método ajustarValorSonido (evitar castings)
    	navegador.move(delta, Input.Keys.UP, Input.Keys.DOWN);
        INavigableOption opcionActual = navegador.getCurrentSelection();
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
    		volumenTemporal.adjustSoundValue((EConfigurationOption) opcionActual, -Volumen.STEP);
    	else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
    		volumenTemporal.adjustSoundValue((EConfigurationOption) opcionActual, Volumen.STEP);

        // Confirmaciones
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	// TODO evitar hacer castings
        	switch ((EConfigurationOption) opcionActual) {
        	case GUARDAR:
        		Volumen.applyChanges(getGame().getVolumen(), volumenTemporal);
        		break;
        	case VOLVER:
        		getGame().getPantallaManager().cambiarPantalla(EScreenType.MENU);
        		break;
        	default: break;
        	}
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        	getGame().getPantallaManager().cambiarPantalla(EScreenType.MENU);
            return;
        }
    }
    
    @Override
    protected void draw(SpriteBatch batch, BitmapFont font) {
    	ScreenUtils.clear(Color.NAVY);

        // Dibujo
        batch.begin();

        font.getData().setScale(2.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "OPCIONES", 480, 640);

        font.getData().setScale(1.5f);
        // TODO No me gustan los números mágicos pero no se me ocurrió otra alternativa u.u
        font.draw(batch,labelValor(volumenTemporal.getMasterVolume()), 460, 300);
        font.draw(batch,labelValor(volumenTemporal.getRawMusicVolume()), 460, 240);
        font.draw(batch,labelValor(volumenTemporal.getRawSfxVolume()), 460, 180);
        
        navegador.drawOptions(batch, font);
        
        font.setColor(Color.WHITE);
        font.draw(batch, "LEFT/RIGHT para ajustar | ENTER para confirmar | ESC para volver", 220, 500);

        batch.end();
    }

    private String labelValor(float v) {
        return Math.round(v * 100f) + "%";
    }
}
