package pantallas;

import com.badlogic.gdx.Screen;

import logica.SpaceNavigation;

/**
 * Clase abstracta encargada de poder tener una base de la lógica interna de las pantallas
 * de nuestro juego.
 * <p>
 * Hace Override a los métodos de Screen con implementaciones vacías para que no sea
 * requerimiento implementar todas a las sub-clases.
 */
public abstract class PantallaBase implements Screen {
    protected SpaceNavigation game;
	
    /**
     * Constructor que toma el juego.
     * @param game
     */
    public PantallaBase(SpaceNavigation game) {
        this.game = game;
    }
    
    public SpaceNavigation getGame() {
    	return game;
    }
	
	@Override public void show() {}
    @Override public void render(float delta) {}
    @Override public void resize(int width, int height) {
        game.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
