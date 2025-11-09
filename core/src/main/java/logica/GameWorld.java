package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import personajes.Player;

public class GameWorld {
	private final Player player;
	private final GameLogicHandler gameLogicHandler;
	
	private final float ROTATE_ANGLE = 5.0f;
	private final float ACCELERATION = 0.2f;
	private boolean estaEnPausa = false;
	
	public GameWorld() {
		this.player  = new Player(5, 5);
		this.gameLogicHandler = new GameLogicHandler();
	}
	
	public void update(float delta) {
		this.handleInput(delta);
		
		player.update(delta);
		gameLogicHandler.handleCollisions(player);
	}

	private void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.rotate(ROTATE_ANGLE);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.rotate(-ROTATE_ANGLE);
		
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.accelerate(ACCELERATION);
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.accelerate(-ACCELERATION);
		else player.applyFriction(0.9f);
		
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			player.shoot(delta, gameLogicHandler.getProyectilManager());
			//player.getWeapon().disparar(player, this, delta);
		}
		
		// "manteniendo" la idea se marcar si está en pausa o no
	    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
	        estaEnPausa = true;
	    }
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public GameLogicHandler getGameLogicHandler() {
		return gameLogicHandler;
	}
	
	//para manejar si está en pausa o no
	public boolean isEstaEnPausa() {
	    return estaEnPausa;
	}
	
	public void setEstaEnPausa(boolean estaEnPausa) {
	    this.estaEnPausa = estaEnPausa;
	}
}
