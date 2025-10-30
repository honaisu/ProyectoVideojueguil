package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import enumeradores.NameManager;
import managers.*;
import personajes.Player;

public class GameWorld {
	private final Player player;
	private final GameManager gameManager;
	
	private final float ROTATE_ANGLE = 3.0f;
	private final float ACCELERATION = 0.2f;
	
	public GameWorld() {
		this.player  = new Player(5, 5);
		//this.gameManager = null;
		this.gameManager = new GameManager();
	}
	
	public void update(float delta) {
		this.handleInput(delta);
		
		player.update(delta);
		gameManager.update(delta);
		gameManager.getCollisionManager().handleCollisions(
				player,
				// TODO Arreglar para solo pedir un manager o algo. Tarea de benjoid xd
				(BulletManager)gameManager.getManager(NameManager.BULLET), 
				(MeleeManager)gameManager.getManager(NameManager.MELEE), 
				(LaserManager)gameManager.getManager(NameManager.LASER), 
				(AsteroidManager)gameManager.getManager(NameManager.ASTEROID));
	}

	private void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.rotate(ROTATE_ANGLE);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.rotate(-ROTATE_ANGLE);
		
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.accelerate(ACCELERATION);
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.accelerate(-ACCELERATION);
		else player.applyFriction(0.9f);
		
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			player.shoot(delta);
			player.getWeapon().disparar(player, this, delta);
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}
}
