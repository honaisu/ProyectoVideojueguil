package armas;


import armas.proyectiles.Swing;
import logica.AssetsLoader;
import pantallas.PantallaJuego;
import personajes.Jugador;

//Clase para el arma cuerpo a cuerpo
public class Melee extends Arma {
	
    private Swing swingActual;	//usa un proyectil arqueado

    public Melee() {
        super(0.8f,															// cadencia
        		9999,														// municion
        		AssetsLoader.getInstancia().getDisparoSound());	// sonido
    }

    //clase sobrescrita
    @Override
    public void disparar(Jugador nave, PantallaJuego juego, float delta) {
        actualizar(delta);

        // No hay munición que contar en ataques melee
        if (!puedeDisparar()) return;

        // Crear golpe (Swing)
        crearSwing(nave, juego);
        reiniciarCooldown();
    }
    
    //crea el swing del arma con direccion respecto al jugador
    private void crearSwing(Jugador jug, PantallaJuego juego) {
    	float radians = (float) Math.toRadians(jug.getRotacion() + 90);

        float centerX = jug.spr.getX() + jug.spr.getWidth() / 2;
        float centerY = jug.spr.getY() + jug.spr.getHeight() / 2;
        float length = jug.spr.getHeight() / 2;
        
        float radio = 100f;  // Alcance del golpe
        
        float swingX = centerX + (float) Math.cos(radians) * length;
        float swingY = centerY + (float) Math.sin(radians) * length;
        
        soundBala.play(0.1f);
        swingActual = new Swing(swingX, swingY, radio , jug);
        juego.agregarSwing(swingActual);
       
    }

    public Swing getSwingActual() {
        return swingActual;
    }
}

