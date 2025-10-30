package logica;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import enumeradores.ScreenType;
import managers.AssetManager;
import managers.ScreenManager;

public class MainGame extends Game {	
	private SpriteBatch batch;
	private BitmapFont font;
	private Viewport viewport;
	private int highScore;
	
    // Volúmenes globales (0.0f a 1.0f)
	private Volumen volumen;
    private ScreenManager pantallaManager;
    
	/**
	 * Método encargado de crear el juego
	 * Crea una camara y UNA pantalla. 
	 */
	@Override
	public void create() {
		highScore = 0;
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(2f);
		
		volumen = new Volumen();
		viewport = new FitViewport(1200, 800, new OrthographicCamera());
		// ESTE ES UN SINGLETON!! carga los assets :D
		AssetManager.getInstancia().load();
		
        pantallaManager = new ScreenManager(this);
        pantallaManager.cambiarPantalla(ScreenType.MENU);
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		pantallaManager.dispose();
		// Cuando lo probé daba error de lo que recuerdo xd
		//assets.dispose();
	}

    @Override 
    public void render() { 
    	super.render();
    }
    
    public SpriteBatch getBatch() { return batch; }
    public BitmapFont getFont() { return font; }
    public int getHighScore() { return highScore; }
    public void setHighScore(int highScore) { this.highScore = highScore; }

    // Volúmenes globales
    public Volumen getVolumen() {
    	return volumen;
    }

	public Viewport getViewport() {
		return viewport;
	}
	
	public ScreenManager getPantallaManager() {
		return pantallaManager;
	}
}
