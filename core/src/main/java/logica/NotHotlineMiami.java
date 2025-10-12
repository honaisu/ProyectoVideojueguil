package logica;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import managers.PantallaManager;
import pantallas.PantallaMenu;
import pantallas.TipoPantalla;
import personajes.Jugador;
import personajes.SkinJugador;

public class NotHotlineMiami extends Game {	
	private SpriteBatch batch;
	private BitmapFont font;
	private Viewport viewport;
	private int highScore;
	
    // Jugador y selección de skin
    private Jugador jugador;		// se crea al iniciar partida
    private Texture skinSelected;	// la skin a usar
	
    // Volúmenes globales (0.0f a 1.0f)
    private float masterVolume = 1.0f;
    private float musicVolume  = 0.30f;
    private float sfxVolume    = 0.60f;
    
    private PantallaManager pantallaManager;
    
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
		
		viewport = new FitViewport(1200, 800, new OrthographicCamera());
		// ESTE ES UN SINGLETON!! carga los assets :D
		AssetsLoader.getInstancia().load();
		
        jugador = null;
        skinSelected = AssetsLoader.getInstancia().getSkinTexture(SkinJugador.JUGADOR_ORIGINAL);
		
        pantallaManager = new PantallaManager(this);
        pantallaManager.changeScreen(TipoPantalla.MENU);
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		pantallaManager.dispose();
		//assets.dispose();
	}

    @Override public void render() { super.render(); }
    
    public SpriteBatch getBatch() { return batch; }
    public BitmapFont getFont() { return font; }
    public int getHighScore() { return highScore; }
    public void setHighScore(int highScore) { this.highScore = highScore; }

    // Jugador
    public Jugador getJugador() { return jugador; }
    public void setJugador(Jugador jugador) { this.jugador = jugador; }

    // Volúmenes globales
    public float getMasterVolume() { return masterVolume; }
    public void setMasterVolume(float v) { masterVolume = clamp01(v); }

    public float getMusicVolume() { return musicVolume; }
    public void setMusicVolume(float v) { musicVolume = clamp01(v); }

    public float getSfxVolume() { return sfxVolume; }
    public void setSfxVolume(float v) { sfxVolume = clamp01(v); }

    // Utilidad local para limitar 0..1
    private float clamp01(float v) { return Math.max(0f, Math.min(1f, v)); }

	public Viewport getViewport() {
		return viewport;
	}

	// SKIN
	public void setSkinSelected(Texture texture) {
		this.skinSelected = texture;
	}
	
	public Texture getSkinSelectedTx() {
		return skinSelected;
	}

	public PantallaManager getPantallaManager() {
		return pantallaManager;
	}
}
