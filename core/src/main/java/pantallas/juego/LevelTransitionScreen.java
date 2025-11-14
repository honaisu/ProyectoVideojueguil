package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import enumeradores.EScreenType;
import logica.MainGame;
import pantallas.BaseScreen;

public class LevelTransitionScreen extends BaseScreen {

    private float timer;
    
    
    private String completedLevelName; // El nombre del nivel que terminaste (ej: "Nivel 1")
    private String nextLevelName;      // El nombre del nivel que vas a cargar (ej: "Nivel 2")
 

    public LevelTransitionScreen(MainGame game) {
        super(game);
        int nextLevelIndex = game.getNextLevelToLoad();
        
        this.completedLevelName = "Nivel " + (nextLevelIndex);
        this.nextLevelName = "Nivel " + (nextLevelIndex + 1);
    }

    @Override
    public void show() {
        this.timer = 3.0f; // Temporizador de 3 segundos
    }

    @Override
    protected void update(float delta) {
        timer -= delta;
        if (timer <= 0) {
            // Cuando el tiempo acaba, cargamos el JUEGO
            // (GameScreen usará 'getNextLevelToLoad()' para cargar el Nivel 2)
            getGame().getPantallaManager().cambiarPantalla(EScreenType.JUEGO);
        }
    }

    @Override
    protected void draw(SpriteBatch batch, BitmapFont font) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Fondo negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        
        // ¡CORREGIDO!
        font.draw(batch, "¡" + completedLevelName + " Completado!", 
                  Gdx.graphics.getWidth() / 2, 
                  Gdx.graphics.getHeight() / 2 + 50, 
                  0, Align.center, false);
        
        // ¡CORREGIDO!
        font.draw(batch, "Cargando " + nextLevelName + "...", 
                  Gdx.graphics.getWidth() / 2, 
                  Gdx.graphics.getHeight() / 2 - 50, 
                  0, Align.center, false);
                  
        batch.end();
    }
}