package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.AssetsLoader;
import logica.NotHotlineMiami;
import personajes.SkinJugador;

public class PantallaPersonalizacion extends PantallaBase {
	private SkinJugador[] skinsDisponibles = SkinJugador.values();
    private SkinJugador skinActual = SkinJugador.JUGADOR_ORIGINAL;

    private float keyCooldown = 0f;
	private final float repeatDelay = 0.1f;
    
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

        keyCooldown -= delta;
        if (keyCooldown <= 0f) {        	
        	int indiceActual = skinActual.ordinal();
        	int largo = skinsDisponibles.length;
        	int nuevoIndice = indiceActual;
        	
        	if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        		nuevoIndice = navegar(Direccion.DERECHA, indiceActual, largo);
        	else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
        		nuevoIndice = navegar(Direccion.IZQUIERDA, indiceActual, largo);
        	
        	skinActual = skinsDisponibles[nuevoIndice];
        	keyCooldown = repeatDelay;
        }

        // Confirmar: guardar path y volver al menú
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	game.setSkinSelected(AssetsLoader.getInstancia().getSkinTexture(skinActual));
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
		
        game.getBatch().begin();
        game.getFont().getData().setScale(2.5f);
        game.getFont().draw(game.getBatch(), "CHOOSE YOUR CHARACTER!!", 360, 600);

        game.getFont().draw(game.getBatch(), "ENTER para confirmar   |   ESC para volver", 240, 100);

        game.getFont().getData().setScale(1.5f);
        int x = 360, y = 300;
        final float PREVIEW_HW = 128f;
        
        int i = 1;
        String mensajeSeleccionado;
        for (SkinJugador skin : SkinJugador.values()) {
        	boolean seleccionado = (skinActual.equals(skin));

        	// Mensaje al estar apretando en uno
        	mensajeSeleccionado = "[" + i + "]";
        	if (seleccionado) mensajeSeleccionado += " SELECCIONADO";
        	
        	// Nombre de la skin
        	game.getFont().draw(game.getBatch(), skin.getNombre(),
        			x - PREVIEW_HW/2f, y - PREVIEW_HW/2f + 200);
        	
        	// Sprite de la skin
        	game.getBatch().draw(skin.crearSprite(),
        			x - PREVIEW_HW/2f, y - PREVIEW_HW/2f, 
        			PREVIEW_HW, PREVIEW_HW);
        	
        	// Sólo imprime el mensaje original :D
            game.getFont().draw(game.getBatch(), mensajeSeleccionado, x - 100, y - 70);
            
        	x += 250;
        	i++;
        }

        game.getBatch().end();
	}
}
