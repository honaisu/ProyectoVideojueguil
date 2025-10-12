package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.SpaceNavigation;


public class PantallaMenu implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;

	private final String[] opcion = { "Iniciar juego", "Customizar", "Opciones", "Tutorial", "Salir" };
	private int selec = 0;

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

	    // Navegación con flechas + Enter
	    keyCooldown -= delta;
	    if (keyCooldown <= 0f) {
	        if (Gdx.input.isKeyPressed(Input.Keys.UP))   { selec = (selec - 1 + opcion.length) % opcion.length; keyCooldown = repeatDelay; }
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { selec = (selec + 1) % opcion.length; keyCooldown = repeatDelay; }
	    }
	    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
	        activarSeleccion();
	    }

	    // Opcional: tap para iniciar rápido (comentar si no se desea)
	    // if (Gdx.input.isTouched()) abrirJuego();

	    // Dibujo
	    game.getBatch().begin();

	    // Título
	    game.getFont().getData().setScale(2.2f);
	    game.getFont().setColor(0.95f, 0.9f, 0.85f, 1f);
	    game.getFont().draw(game.getBatch(), "SPACE NAVIGATION", 330, 650);

	    // Menú
	    game.getFont().getData().setScale(1.6f);
	    float cx = 600f, y = 480f;
	    for (int i = 0; i < opcion.length; i++) {
	        boolean sel = (i == selec);
	        float a = sel ? 1f : 0.65f;
	        game.getFont().setColor(sel ? 1f : 0.8f, sel ? 0.95f : 0.8f, sel ? 0.85f : 0.8f, a);
	        String text = (sel ? "> " : "  ") + opcion[i] + (sel ? " <" : "");
	        game.getFont().draw(game.getBatch(), text, cx - 140, y - i * 60);
	    }

	    // Hint de controles
	    game.getFont().getData().setScale(1.0f);
	    game.getFont().setColor(0.9f, 0.9f, 0.9f, 0.9f);
	    //no creo que lo ocupe game.getFont().draw(game.getBatch(), "Usa UP/DOWN para moverte y ENTER para confirmar", 360, 220);

	    game.getBatch().end();
	}

	private void activarSeleccion() {
	    switch (selec) {
	        case 0: abrirJuego(); break;
	        case 1: abrirCustomizar(); break;
	        case 2: abrirOpciones(); break;
	        case 3: /* abrirTutorial(); */ break;
	        case 4: Gdx.app.exit(); break;
	    }
	}

	private void abrirJuego() {
	    Screen sg = new PantallaJuego(game, 1, 3, 0, 1, 1, 10);
	    sg.resize(1200, 800);
	    game.setScreen(sg);
	    dispose();
	}

	private void abrirCustomizar() {
	    Screen sc = new PantallaCustomizar(game);
	    sc.resize(1200, 800);
	    game.setScreen(sc);
	    dispose();
	}

	private void abrirOpciones() {
	    Screen so = new PantallaOpciones(game);
	    so.resize(1200, 800);
	    game.setScreen(so);
	    dispose();
	}

	@Override public void show() {}
	@Override public void resize(int width, int height) {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void hide() {}
	@Override public void dispose() {}

}
