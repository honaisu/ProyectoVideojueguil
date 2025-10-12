package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.NotHotlineMiami;
import pantallas.opciones.OpcionMenu;

public class PantallaMenu extends PantallaBase {
	// Listado de Opciones que muestra el menú
	private final OpcionMenu[] opciones = OpcionMenu.values();
	private OpcionMenu opcionActual = OpcionMenu.INICIAR;
	
	// Cooldown, retraso para el feedback del usuario :3
	private float keyCooldown = 0f;
	private final float repeatDelay = 0.1f;
	
    private final int ABAJO = 1;
    private final int ARRIBA = -1;

	public PantallaMenu(NotHotlineMiami game) {
	    super(game);
	}
	
	@Override
	public void render(float delta) {
		this.update(delta);
		this.draw();
	}

    private int navegar(int direccion, int indiceActual, int length) {
        int nuevoIndice = (indiceActual + direccion + length) % length;
        return nuevoIndice;
    }
    
    @Override
    protected void update(float delta) {
	    // Navegación con flechas + Enter
	    keyCooldown -= delta;
	    if (keyCooldown <= 0f) {
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) 
	        	opcionActual = opciones[navegar(ARRIBA, opcionActual.ordinal(), opciones.length)];
	        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) 
	        	opcionActual = opciones[navegar(ABAJO, opcionActual.ordinal(), opciones.length)];
	        
	        keyCooldown = repeatDelay;
	    }
	    
	    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
	        this.activarSeleccion();
	    }
    }
    
    @Override
    protected void draw() {
		ScreenUtils.clear(Color.FOREST);

		game.getViewport().apply();
	    game.getBatch().setProjectionMatrix(game.getViewport().getCamera().combined);
	    
	    // Dibujo
	    game.getBatch().begin();

	    // Título
	    game.getFont().getData().setScale(2.2f);
	    game.getFont().setColor(0.95f, 0.9f, 0.85f, 1f);
	    game.getFont().draw(game.getBatch(), "NOT HOTLINE MIAMI", 330, 650);

	    // Menú
	    game.getFont().getData().setScale(1.6f);
	    float cx = 600f, y = 480f;
	    
	    for (OpcionMenu opcion : opciones) {
	        boolean seleccionada = (opcionActual.equals(opcion));
	        float alpha = seleccionada ? 1f : 0.7f;
            game.getFont().setColor(alpha, alpha, alpha, alpha);
            
            String label = opcion.getNombre();
            String value = "";
            
            String textoCompleto = (seleccionada ? "> " : "  ") + label + (value.isEmpty() ? "" : ": " + value) + (seleccionada ? " <" : "");
            game.getFont().draw(game.getBatch(), textoCompleto, cx - 140, y - opcion.ordinal() * 60);
	    }

	    // Hint de controles
	    game.getFont().getData().setScale(1.0f);
	    game.getFont().setColor(0.9f, 0.9f, 0.9f, 0.9f);
	    //no creo que lo ocupe game.getFont().draw(game.getBatch(), "Usa UP/DOWN para moverte y ENTER para confirmar", 360, 220);

	    game.getBatch().end();
    }

	private void activarSeleccion() {
		Screen pantallaActual = null;
		
	    switch (opcionActual) {
	        case INICIAR:
	        	pantallaActual = new PantallaJuego(game, 1, 3, 0);
	        	break;
	        case PERSONALIZAR: 
	        	pantallaActual = new PantallaPersonalizacion(game);
	        	break;
	        case OPCIONES:
	        	pantallaActual = new PantallaConfiguracion(game);
	        	break;
	        case TUTORIAL: 
	        	/* abrirTutorial(); */ 
	        	break;
	        case SALIR: 
	        	Gdx.app.exit(); 
	        	return;
	    }
	    if (pantallaActual == null) return;
	    
	    game.setScreen(pantallaActual);
	    this.dispose();
	}
}
