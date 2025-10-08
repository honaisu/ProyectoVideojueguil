package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;


import personajes.SpaceNavigation;


public class PantallaMenu implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;

    // Nombres solicitados
    private final String[] opcion = { "Iniciar juego", "Customizar", "Opciones", "Tutorial", "Salir" };
    private int selec = 0;

    // Anti-rebote para teclas mantenidas
    private float keyCooldown = 0f;
    private final float repeatDelay = 0.15f;

    public PantallaMenu(SpaceNavigation game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.05f, 0.07f, 0.15f, 1f);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        // Input: navegación con flechas y confirmación con Enter
        keyCooldown -= delta;
        if (keyCooldown <= 0f) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP))   { selec = (selec - 1 + opcion.length) % opcion.length; keyCooldown = repeatDelay; }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { selec = (selec + 1) % opcion.length; keyCooldown = repeatDelay; }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            activarSeleccion();
        }
        // Tocar pantalla sigue iniciando juego rápido
        if (Gdx.input.isTouched()) {
            abrirJuego();
        }

        // Dibujo
        game.getBatch().begin();

        game.getFont().getData().setScale(2.2f);
        game.getFont().setColor(0.95f, 0.9f, 0.85f, 1f);
        game.getFont().draw(game.getBatch(), "SPACE NAVIGATION", 330, 650);

        game.getFont().getData().setScale(1.6f);
        float cx = 600f;
        float y = 480f;
        for (int i = 0; i < opcion.length; i++) {
            boolean sel = (i == selec);
            float a = sel ? 1f : 0.65f;
            game.getFont().setColor(sel ? 1f : 0.8f, sel ? 0.95f : 0.8f, sel ? 0.85f : 0.8f, a);
            String text = (sel ? "> " : "  ") + opcion[i] + (sel ? " <" : "");
            game.getFont().draw(game.getBatch(), text, cx - 140, y - i * 60);
        }

        game.getBatch().end();
    }

    private void activarSeleccion() {
        switch (selec) {
            case 0: // Iniciar juego
                abrirJuego();
                break;
            case 1: // Customizar
                // game.setScreen(new PantallaCustomizar(game));
                break;
            case 2: // Opciones
                // game.setScreen(new PantallaOpciones(game));
                break;
            case 3: // Tutorial
                // game.setScreen(new PantallaTutorial(game));
                break;
            case 4: // Salir
                Gdx.app.exit();
                break;
        }
    }

    private void abrirJuego() {
        Screen sg = new PantallaJuego(game, 1, 3, 0, 1, 1, 10); //sg -> Screan Game
        sg.resize(1200, 800);
        game.setScreen(sg);
        dispose();
    }
    
    /*private void customizarJuego() {
        Screen sC = new PantallaJuego(game, 1, 3, 0, 1, 1, 10); //sg -> Screan Custome
        sC.resize(1200, 800);
        game.setScreen(sC);
        dispose();
    }*/


    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
