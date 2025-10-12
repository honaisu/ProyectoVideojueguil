package logica;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import pantallas.PantallaMenu;
import personajes.Jugador;

public class SpaceNavigation extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public OrthographicCamera camera;
	public Viewport viewport;
	public AssetsLoader assets;
	
	private int highScore;
	
	/**
	 * Método encargado de crear el juego
	 * Crea una camara y UNA pantalla.
	 * 
	 * TODO futuro colaborador si quieres reemplazas esto pq es mi implementacion :D
	 */
	


    //private String nombreJuego = "Space Navigation"; // ver si lo ocupo
    
    // Jugador y selección de skin
    private Jugador jugador;                 // se crea al iniciar partida
    private Texture selectedShipTexture;     // (opcional) no usado ya para flujo principal
    private String selectedShipPath;         // path elegido en Customizar

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		//assets.dispose();
	}


    // Volúmenes globales (0.0f a 1.0f)
    private float masterVolume = 1.0f;
    private float musicVolume  = 0.0f;
    private float sfxVolume    = 0.60f;

    @Override
    public void create() {
        highScore = 0;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2f);
        jugador = null;
        selectedShipTexture = null;
        selectedShipPath = null;            // si es null, se usará la skin por defecto
        Screen ss = new PantallaMenu(this);
        this.setScreen(ss);
    }

    @Override
    public void render() { super.render(); }



    public SpriteBatch getBatch() { return batch; }
    public BitmapFont getFont() { return font; }
    public int getHighScore() { return highScore; }
    public void setHighScore(int highScore) { this.highScore = highScore; }

    // Jugador
    public Jugador getJugador() { return jugador; }
    public void setJugador(Jugador jugador) { this.jugador = jugador; }

    // Selección de skin (por path)
    public String getSelectedShipPath() { return selectedShipPath; }
    public void setSelectedShipPath(String p) { this.selectedShipPath = p; }

    // Mantener por compatibilidad si lo usabas en otros sitios
    public Texture getSelectedShipTexture() { return selectedShipTexture; }
    public void setSelectedShipTexture(Texture t) { this.selectedShipTexture = t; }

    // Volúmenes globales
    public float getMasterVolume() { return masterVolume; }
    public void setMasterVolume(float v) { masterVolume = clamp01(v); }

    public float getMusicVolume() { return musicVolume; }
    public void setMusicVolume(float v) { musicVolume = clamp01(v); }

    public float getSfxVolume() { return sfxVolume; }
    public void setSfxVolume(float v) { sfxVolume = clamp01(v); }

    // Utilidad local para limitar 0..1
    private float clamp01(float v) { return Math.max(0f, Math.min(1f, v)); }

}

