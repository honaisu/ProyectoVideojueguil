package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.AssetsLoader;
import logica.NotHotlineMiami;
import personajes.SkinJugador;

public class PantallaPersonalizacion extends PantallaBase {
    private SkinJugador seleccion = SkinJugador.JUGADOR_ORIGINAL;

    public PantallaPersonalizacion(NotHotlineMiami game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        this.update(delta);
        this.draw();
    }

	@Override
	protected void update(float delta) {
		// Entrada
		
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
        	seleccion = SkinJugador.JUGADOR_ORIGINAL;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
        	seleccion = SkinJugador.JUGADOR_ALT_1;
        }

        // Confirmar: guardar path y volver al menú
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	game.setSkinSelected(AssetsLoader.getInstancia().getSkinTexture(seleccion));
            game.setScreen(new PantallaMenu(game));
            this.dispose();
            return;
        }

        // Volver sin cambios
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaMenu(game));
            this.dispose();
            return;
        }
	}

	@Override
	protected void draw() {
		ScreenUtils.clear(Color.NAVY);
		
        // Dibujo
        game.getBatch().begin();
        game.getFont().getData().setScale(1.6f);
        game.getFont().draw(game.getBatch(), "Elige nave: [1] MainShip3  |  [2] MainShipAlt", 220, 520);
        game.getFont().draw(game.getBatch(), "ENTER para confirmar   |   ESC para volver", 240, 470);

        int y = 320, x = 420;
        float previewW = 96f, previewH = 96f;
        
        int i = 1;
        String mensajeSeleccionado;
        for (SkinJugador skin : SkinJugador.values()) {
        	boolean seleccionado = (seleccion.equals(skin));

        	mensajeSeleccionado = "[" + i + "]";
        	
        	game.getBatch().draw(skin.crearSprite(),
        			x - previewW/2f, y - previewH/2f, 
        			previewW, previewH);
        	
        	if (seleccionado) {
        		mensajeSeleccionado += " SELECCIONADO";
        	}
        	
        	game.getFont().getData().setScale(1.3f);
            game.getFont().draw(game.getBatch(), mensajeSeleccionado, x - 100, y - 70);
            
        	x += 200;
        	i++;
        }

        game.getBatch().end();
	}
}
