package personajes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pantallas.PantallaMenu;

public class SpaceNavigation extends Game {
    private String nombreJuego = "Space Navigation";
    private SpriteBatch batch;
    private BitmapFont font;
    private int highScore;

    // Jugador y selección de skin
    private Jugador jugador;                 // se crea al iniciar partida
    private Texture selectedShipTexture;     // (opcional) no usado ya para flujo principal
    private String selectedShipPath;         // path elegido en Customizar

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

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }

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
}
