package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import interfaces.INavigableOption;

/**
 * Clase encargada de poder navegar cierto catalogo de opciones dado.
 * <p>
 * Esto, en resumen, permite poder recorrer un arreglo de manera dinámica sin que el usuario sepa
 * la implementación que hay detrás. :)
 */
public class OptionNavigator {
	private final INavigableOption[] opciones;
	private int indiceActual;
	
	private float keyCooldown = 0f;
	private final float delay;
	
	public OptionNavigator(INavigableOption[] opciones) {
		this.opciones = opciones;
		this.delay = 0.001f;
	}
	
	public OptionNavigator(INavigableOption[] opciones, float delay) {
		this.opciones = opciones;
		this.delay = delay;
	}
	
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
	    	
	        if (Gdx.input.isKeyJustPressed(siguiente))
	        	indiceActual = navegar(true, indiceActual, largo);
	        else if (Gdx.input.isKeyJustPressed(anterior)) 
	        	indiceActual = navegar(false, indiceActual, largo);
	        
	        keyCooldown = delay;
	    }
	}
	
	/**
	 * Método encargado de dibujar las opciones que existan en el arreglo de manera génerica.
	 * @param batch "Canvas" donde se quiere dibujar
	 * @param font Fuente. :D
	 */
	public void drawOptions(SpriteBatch batch, BitmapFont font) {
        int x = 360, y = 300;
	    for (INavigableOption opcion : opciones) {
	        boolean seleccionada = opciones[indiceActual].equals(opcion);
	        float alpha = seleccionada ? 1f : 0.7f;
            font.setColor(alpha, alpha, alpha, alpha);
            
            String label = opcion.getName();
            String value = "";
            
            String textoCompleto = (seleccionada ? "> " : "  ") + label + (value.isEmpty() ? "" : ": " + value) + (seleccionada ? " <" : "");
            font.draw(batch, textoCompleto, x - 140, y - opcion.ordinal() * 60);
	    }
    }
	
	public INavigableOption getCurrentSelection() {
		return opciones[indiceActual];
	}
	
	/**
	 * Método encargado de navegar por un arreglo de manera circular.
	 * Devuelve un índice en específico.
	 */
	private static int navegar(boolean vaAdelante, int indiceActual, int length) {
    	int eleccion = 1;
    	
    	if (vaAdelante) eleccion = -1;
    	
        int nuevoIndice = (indiceActual + eleccion + length) % length;
        return nuevoIndice;
    }
}
