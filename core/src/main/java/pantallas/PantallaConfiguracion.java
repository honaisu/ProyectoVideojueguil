package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import logica.NotHotlineMiami;
import pantallas.opciones.OpcionConfiguracion;

public class PantallaConfiguracion extends PantallaBase {
	// Opciones a mostrar (Por ahora sólo es audio)
	private final OpcionConfiguracion[] opciones = OpcionConfiguracion.values();
    private OpcionConfiguracion opcionActual = OpcionConfiguracion.VOLUMEN_GENERAL;
    
    // Cooldown al manejarse por el menú
    private float keyCooldown = 0f;
    private final float repeatDelay = 0.08f;

    public PantallaConfiguracion(NotHotlineMiami game) {
        super(game);

        // Cargar valores actuales desde el juego
        Sonido.master = game.getMasterVolume();
        Sonido.music  = game.getMusicVolume();
        Sonido.sfx    = game.getSfxVolume();
    }
    
    @Override
    public void render(float delta) {
    	this.update(delta);
    	this.draw();
    }
    
    @Override
    protected void update(float delta) {
        // Navegación vertical
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
	        
        	// Ajuste de valores
        	if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
        		Sonido.ajustarValorSonido(opcionActual, -Sonido.STEP);
        	else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
        		Sonido.ajustarValorSonido(opcionActual, Sonido.STEP);
        	
            keyCooldown = repeatDelay;
        }

        // Confirmaciones
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	switch (opcionActual) {
        	case GUARDAR:
        		aplicarCambios();
        		break;
        	case VOLVER:
        		game.setScreen(new PantallaMenu(game));
        		return;
        	default: break;
        	}
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaMenu(game));
            return;
        }
    }
    
    @Override
    protected void draw() {
    	ScreenUtils.clear(Color.NAVY);

        // Dibujo
        game.getBatch().begin();

        game.getFont().getData().setScale(2.5f);
        game.getFont().setColor(Color.WHITE);
        game.getFont().draw(game.getBatch(), "OPCIONES", 480, 640);

        game.getFont().getData().setScale(1.5f);
        float x = 400f;
        float y = 520f;
        
        String label;
        String value;
        String textoCompleto;
        for (OpcionConfiguracion opcion : opciones) {
            boolean seleccionada = (opcionActual.equals(opcion));
            float alpha = seleccionada ? 1f : 0.7f;
            game.getFont().setColor(alpha, alpha, alpha, alpha);
            
            label = opcion.getNombre();
            value = "";
            
            switch (opcion) {
            case VOLUMEN_GENERAL:
            	value = labelValor(Sonido.master); 
            	break;
            case VOLUMEN_MUSICA:  
            	value = labelValor(Sonido.music); 
            	break;
            case VOLUMEN_EFECTOS: 
            	value = labelValor(Sonido.sfx); 
            	break;
            default: break;
            }
            
            textoCompleto = (seleccionada ? "> " : "  ") + label + (value.isEmpty() ? "" : ": " + value) + (seleccionada ? " <" : "");
            game.getFont().draw(game.getBatch(), textoCompleto, x, y - opcion.ordinal() * 60);
        }
        
        game.getFont().setColor(Color.WHITE);
        game.getFont().draw(game.getBatch(), "LEFT/RIGHT para ajustar | ENTER para confirmar | ESC para volver", 220, 160);

        game.getBatch().end();
    }
    
    private void aplicarCambios() {
        game.setMasterVolume(Sonido.master);
        game.setMusicVolume(Sonido.music);
        game.setSfxVolume(Sonido.sfx);
    }

    private String labelValor(float v) {
        return Math.round(v * 100f) + "%";
    }
    

    /**
     * Sub-clase que sólo se encarga de manejar los valores del sonido.
     */
    private static class Sonido {    	
    	static float master;
    	static float music;
    	static float sfx;
    	
    	// Paso de ajuste por tecla
        private static final float STEP = 0.05f;
        
        private static void ajustarValorSonido(OpcionConfiguracion opcion, float cambio) {
            switch (opcion) {
                case VOLUMEN_GENERAL:
                    Sonido.master = clamp01(Sonido.master + cambio);
                    break;
                case VOLUMEN_MUSICA:
                    Sonido.music = clamp01(Sonido.music + cambio);
                    break;
                case VOLUMEN_EFECTOS:
                    Sonido.sfx = clamp01(Sonido.sfx + cambio);
                    break;
                default: break;
            }
        }
        
        private static float clamp01(float v) {
            return Math.max(0f, Math.min(1f, v));
        }
    }
}
