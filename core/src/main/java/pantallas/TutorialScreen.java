package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;               
import com.badlogic.gdx.utils.ScreenUtils;

import enumeradores.EScreenType;
import enumeradores.recursos.EGameMusic;
import logica.MainGame;
import managers.assets.AssetManager;

public class TutorialScreen extends BaseScreen {
    private enum Step { MOVER, MOVER_CONFIRMADO, DISPARAR, DISPARAR_CONFIRMADO, FIN } //esto quisas ponerlo en otro lado

    private Step pasoActual = Step.MOVER;

    // Control general de inputs para evitar rebotes
    private float keyCooldown = 0f;
    private final float repeatDelay = 0.08f;

    // Delays entre subpasos
    private final float delayMoverConfirmado = 3f;     // tiempo mostrando el mensaje tras moverse
    private final float delayDisparoConfirmado = 3f;   // tiempo mostrando el mensaje tras disparar

    // Temporizadores
    private float timer = 0f;
    
    //Musica 
    private Music musicaTutorial;
    
    final float worldW;
    final float worldH;

    // Utilidad para centrar el título
    private final GlyphLayout layout = new GlyphLayout();

    public TutorialScreen(MainGame game) {
        super(game);
        worldW = game.getViewport().getWorldWidth();
        worldH = game.getViewport().getWorldHeight();
        musicaTutorial = AssetManager.getInstancia().getMusic(EGameMusic.TUTORIAL);
    }
    
    @Override
    protected void update(float delta) {
        musicaTutorial.setLooping(true);
        musicaTutorial.play();
        keyCooldown -= delta;

        switch (pasoActual) {
            case MOVER:
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
                        || Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                        || Gdx.input.isKeyPressed(Input.Keys.UP)
                        || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    pasoActual = Step.MOVER_CONFIRMADO;
                    timer = 0f;
                    keyCooldown = repeatDelay;
                }
                break;

            case MOVER_CONFIRMADO:
                // El jugador puede seguir moviéndose; solo corre el temporizador
                timer += delta;
                if (timer >= delayMoverConfirmado) {
                    pasoActual = Step.DISPARAR;
                    keyCooldown = repeatDelay;
                }
                break;

            case DISPARAR:
                if (keyCooldown <= 0f && Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                    pasoActual = Step.DISPARAR_CONFIRMADO;
                    timer = 0f;
                    keyCooldown = repeatDelay;
                }
                break;

            case DISPARAR_CONFIRMADO:
                // Puede seguir presionando Z; mostramos el mensaje por un rato y luego finalizamos
                timer += delta;
                if (timer >= delayDisparoConfirmado) {
                    pasoActual = Step.FIN;
                    keyCooldown = repeatDelay;
                }
                break;

            case FIN:
                if (keyCooldown <= 0f &&
                        (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))) {
                    getGame().getPantallaManager().cambiarPantalla(EScreenType.MENU);
                    musicaTutorial.stop();
                }
                break;
        }
    }
    
	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		ScreenUtils.clear(0.1f, 0.12f, 0.16f, 1f);

        // Caja centrada para el cuerpo (multilínea)
        final float cajaAncho = Math.min(720f, worldW * 0.8f);
        final float cajaX = (worldW - cajaAncho) * 0.5f; // caja centrada horizontalmente
        final float tituloY = worldH * 0.82f;
        final float cuerpoY = worldH * 0.6f;
        final float lineaDY = 64f;

        batch.begin();

        // — TÍTULO CENTRADO —
        // 1) Medimos el texto con GlyphLayout (obtiene ancho exacto).
        // 2) Dibujamos en X = centro_mundo − (ancho_texto / 2).
        font.getData().setScale(2.2f);
        String titulo = "TUTORIAL";
        layout.setText(font, titulo);
        float tituloX = (worldW - layout.width) * 0.5f;
        font.draw(batch, layout, tituloX, tituloY);

        // — CUERPO CENTRADO —
        // Usamos draw con Align.center dentro de una “caja” (cajaX, cajaAncho), ideal para varias líneas.
        font.getData().setScale(1.6f);

        switch (pasoActual) {
            case MOVER:
                font.draw(batch, "Muévete con las flechitas", cajaX, cuerpoY, cajaAncho, Align.center, false);
                font.draw(batch, "Presiona cualquier flecha para continuar", cajaX, cuerpoY - lineaDY, cajaAncho, Align.center, false);
                break;

            case MOVER_CONFIRMADO:
                font.draw(batch, "¡ESO, Sigue moviéndote y acostumbrandote.", cajaX, cuerpoY, cajaAncho, Align.center, false);
                font.draw(batch, "Aprenderemos a disparar en un momento", cajaX, cuerpoY - lineaDY, cajaAncho, Align.center, false);
                break;

            case DISPARAR:
                font.draw(batch, "Dispara con Z", cajaX, cuerpoY, cajaAncho, Align.center, false);
                font.draw(batch, "Presiona Z para continuar", cajaX, cuerpoY - lineaDY, cajaAncho, Align.center, false);
                break;

            case DISPARAR_CONFIRMADO:
                font.draw(batch, "¡ESOOOO! Sigue disparando y masacrando.", cajaX, cuerpoY, cajaAncho, Align.center, false);
                font.draw(batch, "Por el momento la munición es infinita.", cajaX, cuerpoY - lineaDY, cajaAncho, Align.center, false);
                break;

            case FIN:
                font.draw(batch, "¡Listo!, hasta aquí llego lo facil", cajaX, cuerpoY, cajaAncho, Align.center, false);
                font.draw(batch, "ENTER o ESC para volver al Menú", cajaX, cuerpoY - lineaDY, cajaAncho, Align.center, false);
                break;
        }

        batch.end();
	}
}
