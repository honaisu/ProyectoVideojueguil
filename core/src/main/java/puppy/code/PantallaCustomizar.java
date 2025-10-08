package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaCustomizar implements Screen {
    private final SpaceNavigation game;
    private OrthographicCamera camera;
    private Texture tx1, tx2;

    public PantallaCustomizar(SpaceNavigation game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);
        tx1 = new Texture(Gdx.files.internal("MainShip3.png"));
        tx2 = new Texture(Gdx.files.internal("MainShipAlt.png"));
    }

    @Override public void render(float delta) {
        ScreenUtils.clear(0.08f, 0.08f, 0.1f, 1f);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        
        
        //se asume que benjoid hará el jugador
        // Entrada: 1/2 para elegir, ESC para volver
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.getJugador().getNave().setTexture(tx1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            game.getJugador().getNave().setTexture(tx2);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaMenu(game));
            dispose();
        }

        game.getBatch().begin();
        game.getFont().getData().setScale(1.6f);
        game.getFont().draw(game.getBatch(), "Elige nave: [1] MainShip3  |  [2] MainShipAlt   (ESC para volver)", 140, 420);
        game.getBatch().end();
    }

    @Override public void dispose() { tx1.dispose(); tx2.dispose(); }
    @Override public void show() {}
    @Override public void resize(int w,int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
