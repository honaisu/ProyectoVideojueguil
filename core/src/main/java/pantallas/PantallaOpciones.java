package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import personajes.SpaceNavigation;

public class PantallaOpciones implements Screen {

    private final SpaceNavigation game;
    private OrthographicCamera camera;

    // Items de opciones (por ahora solo audio)
    private final String[] items = {
        "Volumen General",
        "Volumen Musica",
        "Volumen Efectos",
        "Guardar",
        "Volver"
    };
    private int selec = 0;

    // Valores locales editables (0..1)
    private float master;
    private float music;
    private float sfx;

    // Repetición/anti-rebote de teclas
    private float navCooldown = 0f;
    private final float navDelay = 0.15f;

    private float adjCooldown = 0f;
    private final float adjDelay = 0.08f;

    // Paso de ajuste por tecla
    private final float step = 0.05f;

    public PantallaOpciones(SpaceNavigation game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);

        // Cargar valores actuales desde el juego
        master = game.getMasterVolume();
        music  = game.getMusicVolume();
        sfx    = game.getSfxVolume();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.07f, 0.07f, 0.10f, 1f);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        // Navegación vertical
        navCooldown -= delta;
        if (navCooldown <= 0f) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP))   { selec = (selec - 1 + items.length) % items.length; navCooldown = navDelay; }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { selec = (selec + 1) % items.length; navCooldown = navDelay; }
        }

        // Ajuste de valores
        adjCooldown -= delta;
        if (adjCooldown <= 0f) {
            if (selec == 0) {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  { master = clamp01(master - step); adjCooldown = adjDelay; }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { master = clamp01(master + step); adjCooldown = adjDelay; }
            } else if (selec == 1) {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  { music = clamp01(music - step); adjCooldown = adjDelay; }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { music = clamp01(music + step); adjCooldown = adjDelay; }
            } else if (selec == 2) {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  { sfx = clamp01(sfx - step); adjCooldown = adjDelay; }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { sfx = clamp01(sfx + step); adjCooldown = adjDelay; }
            }
        }

        // Confirmaciones
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selec == 3) { // Guardar
                aplicarCambios();
            } else if (selec == 4) { // Volver
                aplicarCambios(); // auto-guardar al salir (opcional)
                game.setScreen(new PantallaMenu(game));
                return;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Volver sin perder cambios (o comentarlo si quieres descartar)
            aplicarCambios();
            game.setScreen(new PantallaMenu(game));
            return;
        }

        // Dibujo
        game.getBatch().begin();

        game.getFont().getData().setScale(2.0f);
        game.getFont().draw(game.getBatch(), "OPCIONES", 480, 640);

        game.getFont().getData().setScale(1.6f);
        float x = 400f;
        float y = 520f;
        for (int i = 0; i < items.length; i++) {
            boolean sel = (i == selec);
            float a = sel ? 1f : 0.7f;
            game.getFont().setColor(sel ? 1f : 0.85f, sel ? 0.95f : 0.85f, sel ? 0.9f : 0.85f, a);

            String label = items[i];
            String value = "";
            if (i == 0) value = labelValor(master);
            if (i == 1) value = labelValor(music);
            if (i == 2) value = labelValor(sfx);

            String text = (sel ? "> " : "  ") + label + (value.isEmpty() ? "" : ": " + value) + (sel ? " <" : "");
            game.getFont().draw(game.getBatch(), text, x, y - i * 60);
        }

        game.getFont().getData().setScale(1.2f);
        game.getFont().draw(game.getBatch(), "LEFT/RIGHT para ajustar | ENTER para confirmar | ESC para volver", 220, 160);

        game.getBatch().end();
    }

    private void aplicarCambios() {
        // Aplica a SpaceNavigation para que otras pantallas usen estos valores
        game.setMasterVolume(master);
        game.setMusicVolume(music);
        game.setSfxVolume(sfx);
    }

    private String labelValor(float v) {
        int pct = Math.round(v * 100f);
        return pct + "%";
    }

    private float clamp01(float v) {
        return Math.max(0f, Math.min(1f, v));
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
