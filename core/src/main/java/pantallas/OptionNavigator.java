package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import pantallas.menus.Navigation;
import pantallas.opciones.NavigableOption;

public class OptionNavigator implements Navigation {
	private final NavigableOption[] opciones;
	private int indiceActual;
	
	private float keyCooldown = 0f;
	private final float delay;
	
	public OptionNavigator(NavigableOption[] opciones) {
		this.opciones = opciones;
		this.delay = 0.1f;
	}
	
	public OptionNavigator(NavigableOption[] opciones, float delay) {
		this.opciones = opciones;
		this.delay = delay;
	}
	
	// TODO arreglar lógica
	public void move(float delta, int up, int down) {
		keyCooldown -= delta;
	    if (keyCooldown <= 0f) {
	    	int largo = opciones.length;
	    	
	        if (Gdx.input.isKeyPressed(up))
	        	indiceActual = navegar(true, indiceActual, largo);
	        else if (Gdx.input.isKeyPressed(down)) 
	        	indiceActual = navegar(false, indiceActual, largo);
	        
	        keyCooldown = delay;
	    }
	}
	
	public void drawOptions(SpriteBatch batch, BitmapFont font) {
	    for (NavigableOption opcion : opciones) {
	        boolean seleccionada = opciones[indiceActual].equals(opcion);
	        float alpha = seleccionada ? 1f : 0.7f;
            font.setColor(alpha, alpha, alpha, alpha);
            
            String label = opcion.getNombre();
            String value = "";
            
            String textoCompleto = (seleccionada ? "> " : "  ") + label + (value.isEmpty() ? "" : ": " + value) + (seleccionada ? " <" : "");
            font.draw(batch, textoCompleto, cx - 140, y - opcion.ordinal() * 60);
	    }
    }
	
	public NavigableOption getCurrentSelection() {
		return opciones[indiceActual];
	}
}
