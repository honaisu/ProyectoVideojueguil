package pantallas;

import com.badlogic.gdx.Screen;

import logica.NotHotlineMiami;

/**
 * Clase abstracta encargada de poder tener una base de la lógica interna de las pantallas
 * de nuestro juego.
 * <p>
 * Hace Override a los métodos de Screen con implementaciones vacías para que no sea
 * requerimiento implementar todas a las sub-clases.
 */
public abstract class PantallaBase implements Screen {
    protected NotHotlineMiami game;
    /**
     * Enum simple que indica que direcciones puede tener cierta selección
     * (en caso de menús con movimiento entre pantallas)
     */
    protected enum Direccion { ARRIBA, ABAJO, IZQUIERDA, DERECHA; }
	
    /**
     * Constructor que toma el juego.
     * @param game
     */
    public PantallaBase(NotHotlineMiami game) {
        this.game = game;
    }
    
    public NotHotlineMiami getGame() {
    	return game;
    }
    
    /**
     * Método que permite navegar menús de forma intuitiva y fácil :D
     * 
     * @param direccion Donde "apuntará" la nueva selección
     * @param indiceActual El índice actual de la selección
     * @param length El tamaño completo de las opciones de selección
     * @return El índice de la opción a elegir
     */
    protected int navegar(Direccion direccion, int indiceActual, int length) {
    	int eleccion = 1;
    	
    	if (direccion.equals(Direccion.ARRIBA)
    			|| direccion.equals(Direccion.IZQUIERDA)) eleccion = -1;
    	
        int nuevoIndice = (indiceActual + eleccion + length) % length;
        return nuevoIndice;
    }
    
    // Todos los hijos van a implementarla
    public abstract void render(float delta);
    // No son necesarias pero ayudan a ordenar mejor el codigo de cada una
    
    /**
     * Método encargado de poder manejar únicamente la lógica de una pantalla específica.
     * @param delta Diferencia de tiempo proveniente de render.
     */
    protected abstract void update(float delta);
    
    /**
     * Método encargado de dibujar todo lo que ocurra en pantalla.
     */
    protected abstract void draw();
    
	@Override public void show() {}
    @Override public void resize(int width, int height) {
        game.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
