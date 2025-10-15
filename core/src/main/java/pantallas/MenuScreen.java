package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.NotHotlineMiami;
import pantallas.opciones.MenuOption;

public class MenuScreen extends BaseScreen {
	// Listado de Opciones que muestra el menú
	private final MenuOption[] opciones = MenuOption.values();
	private MenuOption opcionActual = MenuOption.INICIAR;
	
	// Cooldown, retraso para el feedback del usuario :3
	private float keyCooldown = 0f;
	private final float repeatDelay = 0.1f;

	public MenuScreen(NotHotlineMiami game) {
	    super(game);
	}
	
	@Override
	public void render(float delta) {
		this.update(delta);
		this.draw(game.getBatch(), game.getFont());
	}
    
    @Override
    protected void update(float delta) {
	    // Navegación con flechas + Enter
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
	        opcionActual.getAction().ejecutar(game);
	    }
    }
    
    @Override
    protected void draw(SpriteBatch batch, BitmapFont font) {
		ScreenUtils.clear(Color.NAVY);

	    // Dibujo
	    batch.begin();

	    // Título
	    font.getData().setScale(2.5f);
	    font.setColor(0.95f, 0.9f, 0.85f, 1f);
	    font.draw(batch, "NOT HOTLINE MIAMI", 400f, 700f);

	    // Menú
	    font.getData().setScale(2.5f);
	    float cx = 600f, y = 480f;
	    
	    for (MenuOption opcion : opciones) {
	        boolean seleccionada = (opcionActual.equals(opcion));
	        float alpha = seleccionada ? 1f : 0.7f;
            font.setColor(alpha, alpha, alpha, alpha);
            
            String label = opcion.getNombre();
            String value = "";
            
            String textoCompleto = (seleccionada ? "> " : "  ") + label + (value.isEmpty() ? "" : ": " + value) + (seleccionada ? " <" : "");
            font.draw(batch, textoCompleto, cx - 140, y - opcion.ordinal() * 60);
	    }

	    // Hint de controles
	    font.getData().setScale(1.0f);
	    font.setColor(0.9f, 0.9f, 0.9f, 0.9f);
	    
	    batch.end();
    }
}
