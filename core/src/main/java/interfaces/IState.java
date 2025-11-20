package interfaces;

/**
 * Interfaz que define un estado de ataque posible. Esta se encargará de poder
 * manejar su update, ver si puede y agregar ataques y verificar que sea válida,
 * junto con proveer recursos (ej. balas) para un mejor funcionamiento. :D
 */
public interface IState {
	void update(float delta);

	boolean canAttack();

	void recordAttack();

	boolean isValid();

	Integer getCurrentResource();
	
	void setCurrentResource(Integer amount);

	Integer getMaxResource();
}
