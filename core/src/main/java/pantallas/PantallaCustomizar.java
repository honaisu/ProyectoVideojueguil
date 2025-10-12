package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.SpaceNavigation;

public class PantallaCustomizar extends PantallaBase {
    private Texture tx1, tx2, seleccion;

    // Rutas de los archivos en assets 
    private static final String PATH_1 = "referencia.png";
    private static final String PATH_2 = "MainShipAlt.png";

    public PantallaCustomizar(SpaceNavigation game) {
        super(game);
        
        // Previews locales solo para mostrar (no se comparten con el juego)
        tx1 = new Texture(Gdx.files.internal(PATH_1));
        tx2 = new Texture(Gdx.files.internal(PATH_2));
        seleccion = tx1;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.08f, 0.08f, 0.10f, 1f);
        
        // Entrada
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) seleccion = tx1;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) seleccion = tx2;

        // Confirmar: guardar path y volver al menú (NO iniciar partida aquí)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            String path = (seleccion == tx1) ? PATH_1 : PATH_2;
            game.setSelectedShipPath(path);
            game.setScreen(new PantallaMenu(game));
            dispose();
            return;
        }

        // Volver sin cambios
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaMenu(game));
            dispose();
            return;
        }

        // Dibujo
        game.getBatch().begin();
        game.getFont().getData().setScale(1.6f);
        game.getFont().draw(game.getBatch(), "Elige nave: [1] MainShip3  |  [2] MainShipAlt", 220, 520);
        game.getFont().draw(game.getBatch(), "ENTER para confirmar   |   ESC para volver", 240, 470);

        float y = 320f, x1 = 420f, x2 = 720f;
        float previewW = 96f, previewH = 96f;

        game.getBatch().draw(tx1, x1 - previewW/2f, y - previewH/2f, previewW, previewH);
        game.getBatch().draw(tx2, x2 - previewW/2f, y - previewH/2f, previewW, previewH);

        String sel1 = (seleccion == tx1) ? "> [1] SELECCIONADA <" : "[1]";
        String sel2 = (seleccion == tx2) ? "> [2] SELECCIONADA <" : "[2]";
        game.getFont().getData().setScale(1.3f);
        game.getFont().draw(game.getBatch(), sel1, x1 - 100, y - 70);
        game.getFont().draw(game.getBatch(), sel2, x2 - 100, y - 70);

        game.getBatch().end();
    }

    @Override 
    public void dispose() { 
    	if (tx1 != null) tx1.dispose(); 
    	if (tx2 != null) tx2.dispose(); 
    }
}
