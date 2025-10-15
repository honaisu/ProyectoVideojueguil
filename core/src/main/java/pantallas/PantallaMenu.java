package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

	public PantallaMenu(NotHotlineMiami game) {
	    super(game);
	}
	
	@Override
	public void render(float delta) {
		this.update(delta);
		this.draw();
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
	        	nuevoIndice = navegar(Direccion.ARRIBA, indiceActual, largo);
	        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) 
	        	nuevoIndice = navegar(Direccion.ABAJO, indiceActual, largo);
	        
	        opcionActual = opciones[nuevoIndice];
	        keyCooldown = repeatDelay;
	    }
	    
	    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
	        this.activarSeleccion();
	    }
    }
    
    @Override
    protected void draw() {
		ScreenUtils.clear(Color.NAVY);

	    // Dibujo
	    game.getBatch().begin();

	    // Título
	    game.getFont().getData().setScale(2.5f);
	    game.getFont().setColor(0.95f, 0.9f, 0.85f, 1f);
	    game.getFont().draw(game.getBatch(), "NOT HOTLINE MIAMI", 
	    		400f, 
	    		700f);

	    // Menú
	    game.getFont().getData().setScale(2.5f);
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
	    
	    game.getBatch().end();
    }

	private void activarSeleccion() {
	    switch (opcionActual) {
	        case INICIAR:
	        	game.getPantallaManager().cambiarPantalla(TipoPantalla.JUEGO);
	        	break;
	        case PERSONALIZAR: 
	        	game.getPantallaManager().cambiarPantalla(TipoPantalla.PERSONALIZACION);
	        	break;
	        case OPCIONES:
	        	game.getPantallaManager().cambiarPantalla(TipoPantalla.CONFIGURACION);
	        	break;
	        case TUTORIAL: 
	        	/* abrirTutorial(); */ 
	        	break;
	        case SALIR: 
	        	Gdx.app.exit(); 
	        	break;
	    }
	}
}
