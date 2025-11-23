package interfaces;

import com.badlogic.gdx.audio.Sound;

import entidades.Entity;
import managers.ProjectileManager;

// TODO Ver que onda para que no nos reten por no tener un strategy xd
public interface IAttackable {
	/**
	 * Método que se encarga de generar un ataque
	 * 
	 * @param delta   Diferencia de tiempo
	 * @param e       Entidad donde spawneará el proyectil
	 * @param manager Manager de proyectiles // TODO Revisar
	 */
	public void attack(Entity e, ProjectileManager manager);
	
	/**
     * Actualiza cooldowns o timers internos (delegado al State).
     */
    void update(float delta);

    /**
     * Verifica si el arma sigue siendo válida (tiene munición o es infinita).
     */
    boolean isValid();
    
    String getName();
    IState getState();
    ITexture getDropTexture();
    Sound getPickupSound();
}
