package armas;

import armas.proyectiles.Swing;
import enumeradores.NameManager;
import logica.GameWorld;
import managers.AssetManager;
import personajes.Player;

//Clase para el arma cuerpo a cuerpo
public class Melee extends Weapon {
    private Swing swingActual;	//usa un proyectil arqueado

    public Melee() {
        super("Sword",
        		0.8f,															// cadencia
        		9999,														// municion
        		AssetManager.getInstancia().getDisparoSound());	// sonido
    }

    //clase sobrescrita
    @Override
    public void disparar(Player nave, GameWorld juego, float delta) {
        actualizar(delta);

        // No hay munición que contar en ataques melee
        if (!puedeDisparar()) return;

        // Crear golpe (Swing)
        crearProyectil(nave, juego);
        reiniciarCooldown();
    }
    
    //crea el swing del arma con direccion respecto al jugador
    @Override
    public void crearProyectil(Player player, GameWorld juego) {
    	float radians = (float) Math.toRadians(player.getRotation() + 90);

        float centerX = player.getSpr().getX() + player.getSpr().getWidth() / 2;
        float centerY = player.getSpr().getY() + player.getSpr().getHeight() / 2;
        //float length = jug.spr.getHeight() / 2;
        
        float length = player.getSpr().getHeight() / 2 + 20; // distancia delante del jugador
        
        float swingX = centerX + (float) Math.cos(radians) * length;
        float swingY = centerY + (float) Math.sin(radians) * length;
        
        getSoundBala().play(0.1f);
        swingActual = new Swing(swingX, swingY, player);
        // TODO Agregar Swing...
        juego.getGameManager().getManager(NameManager.MELEE); // add Swing
    }

    public Swing getSwingActual() {
        return swingActual;
    }
}

