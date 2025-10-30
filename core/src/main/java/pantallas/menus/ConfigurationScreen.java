package pantallas.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import enumeradores.ScreenType;
import interfaces.NavigableOption;
import logica.MainGame;
import logica.Volumen;
import pantallas.opciones.ConfigurationOption;

public class ConfigurationScreen extends NavigableScreen {
	private Volumen volumenTemporal = new Volumen();
    
    public ConfigurationScreen(MainGame game) {
        super(game, ConfigurationOption.values());
    }
    
    @Override
    protected void update(float delta) {
        // Navegación vertical
    	// TODO arreglar método ajustarValorSonido (evitar castings)
    	navegador.move(delta, Input.Keys.UP, Input.Keys.DOWN);
        NavigableOption opcionActual = navegador.getCurrentSelection();
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
    		volumenTemporal.adjustSoundValue((ConfigurationOption) opcionActual, -Volumen.STEP);
    	else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
    		volumenTemporal.adjustSoundValue((ConfigurationOption) opcionActual, Volumen.STEP);

        // Confirmaciones
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	// TODO evitar hacer castings
        	switch ((ConfigurationOption) opcionActual) {
        	case GUARDAR:
        		Volumen.applyChanges(getGame().getVolumen(), volumenTemporal);
        		break;
        	case VOLVER:
        		getGame().getPantallaManager().cambiarPantalla(ScreenType.MENU);
        		break;
        	default: break;
        	}
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        	getGame().getPantallaManager().cambiarPantalla(ScreenType.MENU);
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
        /*
        for (ConfigurationOption opcion : opciones) {
            boolean seleccionada = (opcionActual.equals(opcion));
            float alpha = seleccionada ? 1f : 0.7f;
            font.setColor(alpha, alpha, alpha, alpha);
            
            label = opcion.getNombre();
            value = "";
            
            switch (opcion) {
            case VOLUMEN_GENERAL:
            	value = labelValor(volumenTemporal.getMasterVolume()); 
            	break;
            case VOLUMEN_MUSICA:  
            	value = labelValor(volumenTemporal.getMusicVolume()); 
            	break;
            case VOLUMEN_EFECTOS: 
            	value = labelValor(volumenTemporal.getSfxVolume()); 
            	break;
            default: break;
            }
            
            textoCompleto = (seleccionada ? "> " : "  ") + label + (value.isEmpty() ? "" : ": " + value) + (seleccionada ? " <" : "");
            font.draw(batch, textoCompleto, x, y - opcion.ordinal() * 60);
        }*/
        
        navegador.drawOptions(batch, font);
        
        font.setColor(Color.WHITE);
        font.draw(batch, "LEFT/RIGHT para ajustar | ENTER para confirmar | ESC para volver", 220, 500);

        batch.end();
    }

    private String labelValor(float v) {
        return Math.round(v * 100f) + "%";
    }
}
