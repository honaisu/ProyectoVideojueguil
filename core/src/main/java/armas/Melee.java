package armas;

import armas.proyectiles.Swing;
import logica.assets.AssetManager;
import pantallas.juego.PantallaJuego;
import personajes.Jugador;

//Clase para el arma cuerpo a cuerpo
public class Melee extends Arma {
    private Swing swingActual;	//usa un proyectil arqueado

    public Melee() {
        super(0.8f,															// cadencia
        		9999,														// municion
        		AssetManager.getInstancia().getDisparoSound());	// sonido
    }

    //clase sobrescrita
    @Override
    public void disparar(Jugador jugador, PantallaJuego juego, float delta) {
        actualizar(delta);

        // No hay munición que contar en ataques melee
        if (!puedeDisparar()) return;

        // Crear golpe (Swing)
        crearSwing(jugador);
        reiniciarCooldown();
    }
    
    //crea el swing del arma con direccion respecto al jugador
    private void crearSwing(Jugador jugador) {
		float radians = (float) Math.toRadians(jugador.getRotacion() + 90);
	
	    float centerX = jugador.spr.getX() + jugador.spr.getWidth() / 2;
	    float centerY = jugador.spr.getY() + jugador.spr.getHeight() / 2;
	    float length = jugador.spr.getHeight() / 2;
	    
	    float radio = 100f;  // Alcance del golpe
	    
	    float swingX = centerX + (float) Math.cos(radians) * length;
	    float swingY = centerY + (float) Math.sin(radians) * length;
	    
	    soundBala.play(0.1f);
	    swingActual = new Swing(swingX, swingY, radio , jugador);
	    agregarSwing(swingActual);
    }

    public Swing getSwingActual() {
        return swingActual;
    }
}

