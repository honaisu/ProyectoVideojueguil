package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import armas.proyectiles.Projectile;
import managers.*;
import personajes.Player;

public class GameWorld {
	private final Player player;
	private final GameLogicHandler gameLogicHandler;
	
	private final float ROTATE_ANGLE = 3.0f;
	private final float ACCELERATION = 0.2f;
	
	public GameWorld() {
		this.player  = new Player(5, 5);
		this.gameLogicHandler = new GameLogicHandler();
	}
	
	public void update(float delta) {
		this.handleInput(delta);
		
		player.update(delta);
		gameLogicHandler.handleCollisions();
	}

	private void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.rotate(ROTATE_ANGLE);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.rotate(-ROTATE_ANGLE);
		
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.accelerate(ACCELERATION);
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.accelerate(-ACCELERATION);
		else player.applyFriction(0.9f);
		
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			gameLogicHandler.addProjectile(player.shoot(delta));
			//player.getWeapon().disparar(player, this, delta);
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public GameLogicHandler getGameLogicHandler() {
		return gameLogicHandler;
	}
}
