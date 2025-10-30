package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import interfaces.NavigableOption;
import interfaces.Navigation;

/**
 * Clase encargada de poder navegar cierto catalogo de opciones dado.
 * <p>
 * Esto, en resumen, permite poder recorrer un arreglo de manera dinámica sin que el usuario sepa
 * la implementación que hay detrás. :)
 */
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
	
	// TODO arreglar lógica...? Aunque creo que ya se ve bonito
	/**
	 * Método encargado de moverse entre las opciones
	 * @param delta Parámetro delta.
	 * @param siguiente Se mueve a la opción siguiente
	 * @param anterior Se mueve a la opción anterior
	 */
	public void move(float delta, int siguiente, int anterior) {
		keyCooldown -= delta;
	    if (keyCooldown <= 0f) {
	    	int largo = opciones.length;
	    	
	        if (Gdx.input.isKeyPressed(siguiente))
	        	indiceActual = navegar(true, indiceActual, largo);
	        else if (Gdx.input.isKeyPressed(anterior)) 
	        	indiceActual = navegar(false, indiceActual, largo);
	        
	        keyCooldown = delay;
	    }
	}
	
	/**
	 * Método encargado de dibujar las opciones que existan en el arreglo.
	 * @param batch "Canvas" donde se quiere dibujar
	 * @param font Fuente. :D
	 */
	public void drawOptions(SpriteBatch batch, BitmapFont font) {
        int x = 360, y = 300;
	    for (NavigableOption opcion : opciones) {
	        boolean seleccionada = opciones[indiceActual].equals(opcion);
	        float alpha = seleccionada ? 1f : 0.7f;
            font.setColor(alpha, alpha, alpha, alpha);
            
            String label = opcion.getNombre();
            String value = "";
            
            String textoCompleto = (seleccionada ? "> " : "  ") + label + (value.isEmpty() ? "" : ": " + value) + (seleccionada ? " <" : "");
            font.draw(batch, textoCompleto, x - 140, y - opcion.ordinal() * 60);
	    }
    }
	
	public NavigableOption getCurrentSelection() {
		return opciones[indiceActual];
	}
}
