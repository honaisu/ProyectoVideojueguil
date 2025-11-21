package logica;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import enumeradores.EPlayerSkin;
import enumeradores.EScreenType;
import managers.ScreenManager;
import managers.assets.AssetManager;

public class MainGame extends Game {	
	private SpriteBatch batch;
	private BitmapFont font;
	private Viewport viewport;
	private int highScore;
	
    // Volúmenes globales (0.0f a 1.0f)
	private Volumen volumen;
	// NO sabia mas donde dejarlo :c
	private EPlayerSkin playerSkin = EPlayerSkin.ORIGINAL;
    private ScreenManager pantallaManager;
    
    //para el cambio de nivel
    private int nextLevelToLoad = 0; // Empezamos en 0 (Nivel 1)
    
	/**
	 * Método encargado de crear el juego
	 * Crea una camara y UNA pantalla. 
	 */
	@Override
	public void create() {
		// Instancia de AssetManager
		AssetManager.getInstancia().load();
		highScore = 0;
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(2f);
		
		volumen = new Volumen();
		viewport = new FitViewport(1200, 800, new OrthographicCamera());
		// ESTE ES UN SINGLETON!! carga los assets :D
		
		// Acá ya se empieza a crear el juego
        pantallaManager = new ScreenManager(this);
        pantallaManager.cambiarPantalla(EScreenType.MENU);
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		pantallaManager.dispose();
		AssetManager.getInstancia().dispose();
	}
	
	public EPlayerSkin getPlayerSkin() {
		return playerSkin;
	}
	
	public void setPlayerSkin(EPlayerSkin skin) {
		playerSkin = skin;
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
	
	
	//Para el cambio de niveles 
	/**
     * Guarda el índice del *próximo* nivel que debe ser cargado.
     */
    public void setNextLevelToLoad(int levelIndex) {
        this.nextLevelToLoad = levelIndex;
    }

    /**
     * Devuelve el índice del nivel que toca cargar.
     */
    public int getNextLevelToLoad() {
        return nextLevelToLoad;
    }
	
}
