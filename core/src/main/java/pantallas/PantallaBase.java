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
     * Constructor que toma el juego.
     * @param game
     */
    public PantallaBase(NotHotlineMiami game) {
        this.game = game;
    }
    
    public NotHotlineMiami getGame() {
    	return game;
    }
    
    // No son necesarias pero ayudan a ordenar mejor el codigo de cada una
    public abstract void render(float delta);
    protected abstract void update(float delta);
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
