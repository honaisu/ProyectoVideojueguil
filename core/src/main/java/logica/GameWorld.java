package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import managers.GameManager;
import personajes.Player;

public class GameWorld {
	private final Player player;
	private final GameManager gameManager;
	
	private final float ROTATE_ANGLE = 3.0f;
	private final float ACCELERATION = 0.2f;
	
	public GameWorld() {
		this.player  = new Player();
		this.gameManager = null;
		//this.gameManager = new GameManager();
	}
	
	public void update(float delta) {
		this.handleInput(delta);
		
		player.update(delta);
		//gameManager.update(delta);
	}

	private void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.rotate(ROTATE_ANGLE);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.rotate(-ROTATE_ANGLE);
		
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.accelerate(ACCELERATION);
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.accelerate(-ACCELERATION);
		else player.applyFriction(0.9f);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}
}
