package pantallas.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import enumeradores.EScreenType;
import enumeradores.recursos.EGameMusic;
import logica.MainGame;
import managers.assets.AssetManager;
import pantallas.BaseScreen;

public class TutorialScreen extends BaseScreen {

    private enum Paso { MOVER, MOVER_CONFIRMADO, DISPARAR, DISPARAR_CONFIRMADO, FIN } //esto despues pasarlo a un opciones menu quisas

    private Paso pasoActual = Paso.MOVER;
    private float keyCooldown = 0f;
    private final float repeatDelay = 0.08f;
    private final float delayMoverConfirmado = 3f;
    private final float delayDisparoConfirmado = 3f;
    private float timer = 0f;

    private Music musicaTutorial;

    public TutorialScreen(MainGame game) {
        super(game);
        // Ajusta el getter concreto según tus nombres
        musicaTutorial = AssetManager.getInstancia().getMusic(EGameMusic.TUTORIAL);
    }

    @Override public void show() {
        if (musicaTutorial != null) {
            musicaTutorial.setLooping(true);
            musicaTutorial.play();
        }
    }

    @Override public void hide() {
        if (musicaTutorial != null) musicaTutorial.stop();
    }

    @Override
    protected void update(float delta) {
        keyCooldown -= delta;

        switch (pasoActual) {
            case MOVER:
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
                 || Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                 || Gdx.input.isKeyPressed(Input.Keys.UP)
                 || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    pasoActual = Paso.MOVER_CONFIRMADO;
                    timer = 0f;
                    keyCooldown = repeatDelay;
                }
                break;

            case MOVER_CONFIRMADO:
                timer += delta;
                if (timer >= delayMoverConfirmado) {
                    pasoActual = Paso.DISPARAR;
                    keyCooldown = repeatDelay;
                }
                break;

            case DISPARAR:
                if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                    pasoActual = Paso.DISPARAR_CONFIRMADO;
                    timer = 0f;
                    keyCooldown = repeatDelay;
                }
                break;

            case DISPARAR_CONFIRMADO:
                timer += delta;
                if (timer >= delayDisparoConfirmado) {
                    pasoActual = Paso.FIN;
                    keyCooldown = repeatDelay;
                }
                break;

            case FIN:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                 || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    getGame().getPantallaManager().cambiarPantalla(EScreenType.MENU);
                }
                break;
        }
    }

    @Override
    protected void draw(SpriteBatch batch, BitmapFont font) {
        ScreenUtils.clear(0.1f, 0.12f, 0.16f, 1f);

        float w = getGame().getViewport().getWorldWidth();
        float h = getGame().getViewport().getWorldHeight();
        float caja = Math.min(720f, w * 0.8f);
        float x = (w - caja) * 0.5f;
        float yTitulo = h * 0.82f;
        float y = h * 0.6f;
        float dy = 64f;

        batch.begin();
        font.getData().setScale(2.2f);
        
        font.draw(batch, "TUTORIAL", x, yTitulo, caja, Align.center, false);

        font.getData().setScale(1.6f);
        switch (pasoActual) {
            case MOVER:
                font.draw(batch, "Muévete con flechas", x, y, caja, Align.center, false);
                font.draw(batch, "Pulsa una flecha para continuar", x, y - dy, caja, Align.center, false);
                break;
            case MOVER_CONFIRMADO:
                font.draw(batch, "¡Bien! Sigue moviéndote.", x, y, caja, Align.center, false);
                font.draw(batch, "Enseguida aprendemos a disparar", x, y - dy, caja, Align.center, false);
                break;
            case DISPARAR:
                font.draw(batch, "Dispara con Z", x, y, caja, Align.center, false);
                font.draw(batch, "Pulsa Z para continuar", x, y - dy, caja, Align.center, false);
                break;
            case DISPARAR_CONFIRMADO:
                font.draw(batch, "¡Perfecto!", x, y, caja, Align.center, false);
                font.draw(batch, "Munición infinita por ahora", x, y - dy, caja, Align.center, false);
                break;
            case FIN:
                font.draw(batch, "Listo, fin del tutorial", x, y, caja, Align.center, false);
                font.draw(batch, "ENTER o ESC para volver al menú", x, y - dy, caja, Align.center, false);
                break;
        }
        batch.end();
    }
}