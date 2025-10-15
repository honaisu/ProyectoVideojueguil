package pantallas.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.MainGame;
import pantallas.ScreenType;
import pantallas.Volumen;
import pantallas.opciones.ConfigurationOption;
import pantallas.opciones.NavigableOption;

public class ConfigurationScreen extends NavigableScreen {
	private Volumen volumenTemporal = new Volumen();
    
    public ConfigurationScreen(MainGame game) {
        super(game, ConfigurationOption.values());
    }
    
    @Override
    public void render(float delta) {
    	this.update(delta);
    	this.draw(game.getBatch(), game.getFont());
    }
    
    @Override
    protected void update(float delta) {
        // Navegación vertical
    	// TODO arreglar método ajustarValorSonido (evitar castings)
        NavigableOption opcionActual = navegador.getCurrentSelection();
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
    		volumenTemporal.ajustarValorSonido((ConfigurationOption) opcionActual, -Volumen.STEP);
    	else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
    		volumenTemporal.ajustarValorSonido((ConfigurationOption) opcionActual, Volumen.STEP);

        // Confirmaciones
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	// TODO evitar hacer castings
        	switch ((ConfigurationOption) opcionActual) {
        	case GUARDAR:
        		Volumen.aplicarCambios(game.getVolumen(), volumenTemporal);
        		break;
        	case VOLVER:
        		game.getPantallaManager().cambiarPantalla(ScreenType.MENU);
        		break;
        	default: break;
        	}
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        	game.getPantallaManager().cambiarPantalla(ScreenType.MENU);
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
        font.draw(game.getBatch(), "OPCIONES", 480, 640);

        font.getData().setScale(1.5f);
        float x = 400f;
        float y = 520f;
        
        String label;
        String value;
        String textoCompleto;
        
        
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
        
        navegador.draw(batch, font);
        
        font.setColor(Color.WHITE);
        font.draw(game.getBatch(), "LEFT/RIGHT para ajustar | ENTER para confirmar | ESC para volver", 220, 160);

        batch.end();
    }

    private String labelValor(float v) {
        return Math.round(v * 100f) + "%";
    }
}
