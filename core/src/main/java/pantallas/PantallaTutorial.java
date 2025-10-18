package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;   // <-- para medir ancho del título
import com.badlogic.gdx.utils.Align;               // <-- para centrar texto en una “caja”
import com.badlogic.gdx.utils.ScreenUtils;

import logica.AssetsLoader;
import logica.NotHotlineMiami;

public class PantallaTutorial extends PantallaBase {

    private enum Paso { MOVER, MOVER_CONFIRMADO, DISPARAR, DISPARAR_CONFIRMADO, FIN } //esto quisas ponerlo en otro lado

    private Paso pasoActual = Paso.MOVER;

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

    // Utilidad para centrar el título
    private final GlyphLayout layout = new GlyphLayout();

    public PantallaTutorial(NotHotlineMiami game) {
        super(game);
        musicaTutorial = AssetsLoader.getInstancia().getTutorialSound();
    }
    
    @Override
    public void render(float delta) {
        this.update(delta);
        this.draw();
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
                    pasoActual = Paso.MOVER_CONFIRMADO;
                    timer = 0f;
                    keyCooldown = repeatDelay;
                }
                break;

            case MOVER_CONFIRMADO:
                // El jugador puede seguir moviéndose; solo corre el temporizador
                timer += delta;
                if (timer >= delayMoverConfirmado) {
                    pasoActual = Paso.DISPARAR;
                    keyCooldown = repeatDelay;
                }
                break;

            case DISPARAR:
                if (keyCooldown <= 0f && Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                    pasoActual = Paso.DISPARAR_CONFIRMADO;
                    timer = 0f;
                    keyCooldown = repeatDelay;
                }
                break;

            case DISPARAR_CONFIRMADO:
                // Puede seguir presionando Z; mostramos el mensaje por un rato y luego finalizamos
                timer += delta;
                if (timer >= delayDisparoConfirmado) {
                    pasoActual = Paso.FIN;
                    keyCooldown = repeatDelay;
                }
                break;

            case FIN:
                if (keyCooldown <= 0f &&
                        (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))) {
                    game.getPantallaManager().cambiarPantalla(TipoPantalla.MENU);
                    musicaTutorial.stop();
                }
                break;
        }
    }

    @Override
    protected void draw() {
        ScreenUtils.clear(0.1f, 0.12f, 0.16f, 1f);

        // Usamos dimensiones del viewport para centrar en tu “mundo” lógico
        final float worldW = game.getViewport().getWorldWidth();
        final float worldH = game.getViewport().getWorldHeight();

        // Caja centrada para el cuerpo (multilínea)
        final float cajaAncho = Math.min(720f, worldW * 0.8f);
        final float cajaX = (worldW - cajaAncho) * 0.5f; // centramos la caja horizontalmente
        final float tituloY = worldH * 0.82f;
        final float cuerpoY = worldH * 0.6f;
        final float lineaDY = 64f;

        game.getBatch().begin();

        // — TÍTULO CENTRADO —
        // 1) Medimos el texto con GlyphLayout (obtiene ancho exacto).
        // 2) Dibujamos en X = centro_mundo − (ancho_texto / 2).
        game.getFont().getData().setScale(2.2f);
        String titulo = "TUTORIAL";
        layout.setText(game.getFont(), titulo);
        float tituloX = (worldW - layout.width) * 0.5f;
        game.getFont().draw(game.getBatch(), layout, tituloX, tituloY);

        // — CUERPO CENTRADO —
        // Usamos draw con Align.center dentro de una “caja” (cajaX, cajaAncho), ideal para varias líneas.
        game.getFont().getData().setScale(1.6f);

        switch (pasoActual) {
            case MOVER:
                game.getFont().draw(game.getBatch(), "Muévete con las flechitas", cajaX, cuerpoY, cajaAncho, Align.center, false);
                game.getFont().draw(game.getBatch(), "Presiona cualquier flecha para continuar", cajaX, cuerpoY - lineaDY, cajaAncho, Align.center, false);
                break;

            case MOVER_CONFIRMADO:
                game.getFont().draw(game.getBatch(), "¡ESO, Sigue moviéndote y acostumbrandote.", cajaX, cuerpoY, cajaAncho, Align.center, false);
                game.getFont().draw(game.getBatch(), "Aprenderemos a disparar en un momento", cajaX, cuerpoY - lineaDY, cajaAncho, Align.center, false);
                break;

            case DISPARAR:
                game.getFont().draw(game.getBatch(), "Dispara con Z", cajaX, cuerpoY, cajaAncho, Align.center, false);
                game.getFont().draw(game.getBatch(), "Presiona Z para continuar", cajaX, cuerpoY - lineaDY, cajaAncho, Align.center, false);
                break;

            case DISPARAR_CONFIRMADO:
                game.getFont().draw(game.getBatch(), "¡ESOOOO! Sigue disparando y masacrando.", cajaX, cuerpoY, cajaAncho, Align.center, false);
                game.getFont().draw(game.getBatch(), "Por el momento la munición es infinita.", cajaX, cuerpoY - lineaDY, cajaAncho, Align.center, false);
                break;

            case FIN:
                game.getFont().draw(game.getBatch(), "¡Listo!, hasta aquí llego lo facil", cajaX, cuerpoY, cajaAncho, Align.center, false);
                game.getFont().draw(game.getBatch(), "ENTER o ESC para volver al Menú", cajaX, cuerpoY - lineaDY, cajaAncho, Align.center, false);
                break;
        }

        game.getBatch().end();
    }
    
}
