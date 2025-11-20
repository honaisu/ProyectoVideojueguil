package pantallas.juego; 

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enumeradores.EScreenType;
import enumeradores.opciones.EWinOption;
import interfaces.INavigableOption;
import logica.MainGame;
import pantallas.menus.NavigableScreen;

// Hereda de NavigableScreen, igual que PauseScreen
public class WinScreen extends NavigableScreen {

    public WinScreen(MainGame game) {
        // Le pasamos las opciones que acabamos de crear
        super(game, EWinOption.values());
    }

    @Override
    protected void update(float delta) {
        // 1. Moverse por las opciones (Arriba/Abajo)
        navegador.move(delta, Input.Keys.UP, Input.Keys.DOWN);
        INavigableOption opcionActual = navegador.getCurrentSelection();

        // 2. Ejecutar la opción (Enter)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            
            // ¡¡LÓGICA CRÍTICA DE REINICIO!!
            // Ambas opciones deben reiniciar el juego
            getGame().setNextLevelToLoad(0);

            if (opcionActual.ordinal() == EWinOption.VOLVER_A_JUGAR.ordinal()) {
                // Opción: Volver a Jugar
                getGame().getPantallaManager().cambiarPantalla(EScreenType.GAME);
                this.dispose(); // (Igual que en tu PauseScreen)
                
            } else {
                // Opción: Menú Principal
                getGame().getPantallaManager().cambiarPantalla(EScreenType.MENU);
                this.dispose(); // (Igual que en tu PauseScreen)
            }
            return;
        }
    }

    @Override
    protected void draw(SpriteBatch batch, BitmapFont font) {
        // Fondo negro
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        float px = Gdx.graphics.getWidth() / 2f - 150f;
        float py = Gdx.graphics.getHeight() / 2f + 100f;

        // Texto
        font.getData().setScale(2.5f);
        font.draw(batch, "¡HAS GANADO!", px, py);
        
        // Dibuja las opciones ("Volver a Jugar", "Menu Principal")
        navegador.drawOptions(batch, font);

        batch.end();
    }
}