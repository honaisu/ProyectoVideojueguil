package pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logica.MainGame;

/**
 * Clase abstracta encargada de poder tener una base de la lógica interna de las pantallas
 * de nuestro juego.
 * <p>
 * Hace Override a los métodos de Screen con implementaciones vacías para que no sea
 * requerimiento implementar todas a las sub-clases.
 */
public abstract class BaseScreen implements Screen {
    // TODO Cambiar parámetro a private para usar el getGame
	private MainGame game;
	
    /**
     * Constructor que toma el juego.
     * @param game
     */
    public BaseScreen(MainGame game) {
        this.game = game;
    }
    
    public MainGame getGame() {
    	return game;
    }
    
    @Override
    public void render(float delta) {
    	this.update(delta);
    	this.draw(game.getBatch(), game.getFont());
    }
    
    /**
     * Método encargado de poder manejar únicamente la lógica de una pantalla específica.
     * @param delta Diferencia de tiempo proveniente de render.
     */
    protected abstract void update(float delta);
    
    /**
     * Método encargado de dibujar todo lo que ocurra en pantalla.
     * <p>
     * No posee lógicas internas, si no que se encarga de todo lo "dibujable"
     */
    protected abstract void draw(SpriteBatch batch, BitmapFont font);
    
	@Override public void show() {}
    @Override public void resize(int width, int height) {
        game.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
