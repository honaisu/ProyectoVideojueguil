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
    
    // Valores de sonido locales
    private float master;
    private float music;
    private float sfx;

    // Cooldown al manejarse por el menú
    private float navCooldown = 0f;
    private final float navDelay = 0.15f;

    private float adjCooldown = 0f;
    private final float adjDelay = 0.08f;

    // Paso de ajuste por tecla
    private final float STEP = 0.05f;
    private final int ABAJO = 1;
    private final int ARRIBA = -1;

    public PantallaConfiguracion(NotHotlineMiami game) {
        super(game);

        // Cargar valores actuales desde el juego
        this.master = game.getMasterVolume();
        this.music  = game.getMusicVolume();
        this.sfx    = game.getSfxVolume();
    }
    
    @Override
    public void render(float delta) {
    	this.update(delta);
    	this.draw();
    }
    
    @Override
    protected void update(float delta) {
        // Navegación vertical
        navCooldown -= delta;
        if (navCooldown <= 0f) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) navegar(ARRIBA);
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) navegar(ABAJO);
        	
        	navCooldown = navDelay;
        }

        // Ajuste de valores
        adjCooldown -= delta;
        if (adjCooldown <= 0f) {
        	if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) ajustarValorSonido(-STEP);
        	else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ajustarValorSonido(STEP);
        	
            adjCooldown = adjDelay;
        }

        // Confirmaciones
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	switch (opcionActual) {
        	case GUARDAR:
        		aplicarCambios();
        		break;
        	case VOLVER:
        		// Volver sin perder cambios
        		//aplicarCambios();
        		game.setScreen(new PantallaMenu(game));
        		return;
        	default: break;
        	}
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Volver sin perder cambios (o comentarlo si quieres descartar)
            //aplicarCambios();
            game.setScreen(new PantallaMenu(game));
            return;
        }
    }
    
    @Override
    protected void draw() {
    	ScreenUtils.clear(Color.CLEAR_WHITE);

        // Dibujo
        game.getBatch().begin();

        game.getFont().getData().setScale(2.0f);
        game.getFont().draw(game.getBatch(), "OPCIONES", 480, 640);

        game.getFont().getData().setScale(1.6f);
        float x = 400f;
        float y = 520f;
        for (OpcionConfiguracion opcion : opciones) {
            boolean seleccionada = (opcionActual.equals(opcion));
            float alpha = seleccionada ? 1f : 0.7f;
            game.getFont().setColor(alpha, alpha, alpha, alpha);
            
            String label = opcion.getNombre();
            String value = "";
            
            switch (opcion) {
            case VOLUMEN_GENERAL:
            	value = labelValor(master); 
            	break;
            case VOLUMEN_MUSICA:  
            	value = labelValor(music); 
            	break;
            case VOLUMEN_EFECTOS: 
            	value = labelValor(sfx); 
            	break;
            default: break;
            }
            
            String textoCompleto = (seleccionada ? "> " : "  ") + label + (value.isEmpty() ? "" : ": " + value) + (seleccionada ? " <" : "");
            game.getFont().draw(game.getBatch(), textoCompleto, x, y - opcion.ordinal() * 60);
        }
        
        game.getFont().getData().setScale(1.2f);
        game.getFont().draw(game.getBatch(), "LEFT/RIGHT para ajustar | ENTER para confirmar | ESC para volver", 220, 160);

        game.getBatch().end();
    }
    
    private void navegar(int direccion) {
        int indiceActual = opcionActual.ordinal();
        int nuevoIndice = (indiceActual + direccion + opciones.length) % opciones.length;
        opcionActual = opciones[nuevoIndice];
    }

    private void aplicarCambios() {
        // Aplica a SpaceNavigation para que otras pantallas usen estos valores
        game.setMasterVolume(master);
        game.setMusicVolume(music);
        game.setSfxVolume(sfx);
    }
    
    private void ajustarValorSonido(float cambio) {
        switch (opcionActual) {
            case VOLUMEN_GENERAL:
                master = clamp01(master + cambio);
                break;
            case VOLUMEN_MUSICA:
                music = clamp01(music + cambio);
                break;
            case VOLUMEN_EFECTOS:
                sfx = clamp01(sfx + cambio);
                break;
            default: break;
        }
    }

    private String labelValor(float v) {
        return Math.round(v * 100f) + "%";
    }

    private float clamp01(float v) {
        return Math.max(0f, Math.min(1f, v));
    }
}
