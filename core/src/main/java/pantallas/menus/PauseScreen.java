package pantallas.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import logica.MainGame;
import pantallas.ScreenType;
import pantallas.opciones.PauseOption;

public class PauseScreen extends NavigableScreen {
	private final Color TRANSPARENTE = new Color(0f, 0f, 0f, 0.45f);
    private final Texture TEXTURA_PAUSA;
    
    private PauseOption[] opciones = PauseOption.values();
    private PauseOption opcionActual = PauseOption.CONTINUAR;
    
    // Cooldown al manejarse por el menú
    private float keyCooldown = 0f;
    private final float repeatDelay = 0.08f;
	
	public PauseScreen(MainGame game) {
		super(game);
		
		// Rellena un cuadrado entero con solo blanco para que sirva de "textura"
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        TEXTURA_PAUSA = new Texture(pixmap);
        pixmap.dispose();
	}

	@Override
	public void render(float delta) {
		this.update(delta);
		this.draw(game.getBatch(), game.getFont());
	}

	@Override
	protected void update(float delta) {
		// Navegación
		keyCooldown -= delta;
        if (keyCooldown <= 0f) {
        	int indiceActual = opcionActual.ordinal();
	    	int largo = opciones.length;
	    	int nuevoIndice = indiceActual;
	    	
	        if (Gdx.input.isKeyPressed(Input.Keys.UP))
	        	nuevoIndice = navegar(Direction.ARRIBA, indiceActual, largo);
	        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) 
	        	nuevoIndice = navegar(Direction.ABAJO, indiceActual, largo);
	        
	        opcionActual = opciones[nuevoIndice];
            keyCooldown = repeatDelay;
        }
		
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	if (opcionActual.equals(PauseOption.CONTINUAR)) {        		
        		this.resume();
        	} else {
        		game.getPantallaManager().cambiarPantalla(ScreenType.MENU);
                this.dispose();
                return;
            }
        }
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		// Overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(TRANSPARENTE);
        batch.draw(TEXTURA_PAUSA, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(Color.WHITE);
        
        // Texto
        float panelW = 520f, panelH = 260f;
        float px = (Gdx.graphics.getWidth() - panelW) / 2f;
        float py = (Gdx.graphics.getHeight() - panelH) / 2f;

        font.getData().setScale(2.0f);
        font.draw(batch, "PAUSA", px + 190, py + 200);

        font.getData().setScale(1.6f);
        for (PauseOption opcion : opciones) {
        	boolean seleccionada = (opcionActual.equals(opcion));
	        float alpha = seleccionada ? 1f : 0.7f;
            font.setColor(alpha, alpha, alpha, alpha);
            
            String label = opcion.getNombre();
            String value = "";
            
            String textoCompleto = (seleccionada ? "> " : "  ") + label + (value.isEmpty() ? "" : ": " + value) + (seleccionada ? " <" : "");
            font.draw(batch, textoCompleto, px + 120, py + 130 - opcion.ordinal() * 60);
        }
	}

}
