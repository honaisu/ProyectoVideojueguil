package armas;

import com.badlogic.gdx.math.Rectangle;

import armas.proyectiles.Projectile;
import armas.proyectiles.Swing;

//Clase para el arma cuerpo a cuerpo
public class Melee extends Weapon {
	//usa un proyectil arqueado
    private Swing swingActual;

    public Melee() {
        super(0.8f, 9999);
        super.setNombre("Melee");
    }

    //clase sobrescrita
    /*
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
        
        // distancia delante del jugador
        float length = player.getSpr().getHeight() / 2 + 20; 
        
        float swingX = centerX + (float) Math.cos(radians) * length;
        float swingY = centerY + (float) Math.sin(radians) * length;
        
        getSoundBala().play(0.1f);
        float radio = 20 + player.getSpr().getHeight() / 2;
        swingActual = new Swing(swingX, swingY, player.getRotation(), radio);
        // TODO Agregar Swing...
        // add Swing
        juego.getGameManager().getProyectilManager().add(swingActual); 
    }*/

    public Swing getSwingActual() {
        return swingActual;
    }

	@Override
	public Projectile crearProyectil(Rectangle r, float rotation) {
		float radians = (float) Math.toRadians(rotation);
		float centerX = r.getX() + r.getWidth() / 2;
        float centerY = r.getY() + r.getHeight() / 2;
        // distancia delante del jugador
        float length = r.getHeight() / 2 + 20; 
        float swingX = centerX + (float) Math.cos(radians) * length;
        float swingY = centerY + (float) Math.sin(radians) * length;
        swingActual = new Swing(swingX, swingY, rotation, length);
		return swingActual;
	}
}

