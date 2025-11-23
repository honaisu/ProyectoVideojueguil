package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import enumeradores.EScreenType;
import logica.MainGame;
import pantallas.BaseScreen;

public class LevelTransitionScreen extends BaseScreen {
    private float timer;
    
    private String completedLevelName;
    private String nextLevelName;
 

    public LevelTransitionScreen(MainGame game) {
        super(game);
        int nextLevelIndex = game.getNextLevelToLoad();
        
        this.completedLevelName = "Nivel " + (nextLevelIndex);
        this.nextLevelName = "Nivel " + (nextLevelIndex + 1);
    }

    @Override
    public void show() {
        this.timer = 4.0f;
    }

    @Override
    protected void update(float delta) {
        timer -= delta;
        if (timer <= 0) {
            //  Se carga el juego
            getGame().getPantallaManager().cambiarPantalla(EScreenType.GAME);
        }
    }

    @Override
    protected void draw(SpriteBatch batch, BitmapFont font) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Fondo negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "¡" + completedLevelName + " Completado!", 
                  Gdx.graphics.getWidth() / 2, 
                  Gdx.graphics.getHeight() / 2 + 50, 
                  0, Align.center, false);
        
        font.draw(batch, "Cargando " + nextLevelName + "...", 
                  Gdx.graphics.getWidth() / 2, 
                  Gdx.graphics.getHeight() / 2 - 50, 
                  0, Align.center, false);
        
        font.setColor(Color.LIME);
        font.draw(batch, "Vida restaurada", 
                Gdx.graphics.getWidth() / 2, 
                100, 
                0, Align.center, false);
                  
        batch.end();
    }
}