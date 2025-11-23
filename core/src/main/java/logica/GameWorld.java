package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import entidades.Player;
import enumeradores.EPlayerSkin;
import enumeradores.recursos.texturas.EBackgroundType;
import factories.LevelFactory;

//coso para los enemigos y el cambio de ronda

import logica.levels.Level;

public class GameWorld {
	private final GameLogicHandler gameLogicHandler;
	private final Player player;

	private int currentLevelIndex; // Índice nivel actual
	private Level currentLevel; // Level actual
	private boolean waitingForNextRound;
	private boolean gameWon = false; // flag para ver si ya ganamos
	private boolean levelComplete = false; // flag para ver si se completo el nivel
	private float currentLevelFriction = 0.9f; // para la friccion del nivelactual (normal es 0.9f)

	
	// Parametro movimiento del jugador
	private final float ROTATE_ANGLE = 5.0f;
	private final float ROTATE_ANGLE_SLOW = 1.0f;
	private final float ACCELERATION = 100f;
	private final float ACCELERATION_SLOW = 10f;
	
	// Para transicion de rondas
	private boolean roundComplete = false; // flag para ver si se completo la ronda
	private boolean isTransitioningRound = false; // flag para controlar el delay interna
	private float roundDelayTimer = 0f;
	private final float ROUND_DELAY_DURATION = 2.0f;

	public GameWorld(int startingLevelIndex, EPlayerSkin skin) {
		this.player = new Player(0, 0, skin);
		this.gameLogicHandler = new GameLogicHandler();

		this.currentLevelIndex = startingLevelIndex - 1;
		this.waitingForNextRound = true;

		// crear Primer nivel
		startNextLevel();
	}

	private void startNextLevel() {

		currentLevelIndex++; // avanzar indice nivel
		final int TOTAL_LEVELS = 5; // Constante para saber cuándo parar

		// TODO ver rondas en especifico
		//currentLevelIndex = 4;

		if (currentLevelIndex < TOTAL_LEVELS) {

			// Cargar nivel
			switch (currentLevelIndex) {
			case 0: // Nivel 1
				currentLevel = LevelFactory.createLevelOne();
				break;
			case 1: // Nivel 2
				currentLevel = LevelFactory.createLevelTwo();
				break;
			case 2: // Nivel 3
				currentLevel = LevelFactory.createLevelThree();
				break;
			case 3: // Nivel 4
				currentLevel = LevelFactory.createLevelFour();
				break;
			case 4: // Nivel 5
				currentLevel = LevelFactory.createLevelFive();
				break;
			default:
				System.err.println("Error: Índice de nivel inválido: " + currentLevelIndex);
				return;
			}

			// Friccion del jugador del nivel
			this.currentLevelFriction = currentLevel.getPlayerFriction();

			// Crear obstaculos
			// TODO mejora esto// crea los obstaculos apra el nivel 1
			gameLogicHandler.getObstacleManager().spawnObstacles(currentLevel.getHazardCount(),
					currentLevel.getSolidCount(), currentLevel.getBackground());

			this.waitingForNextRound = true;

		}
	}

	public void update(float delta) {
		this.handleInput(delta);
		player.update(delta);
		gameLogicHandler.update(delta);
		gameLogicHandler.handleCollisions(player);
		// Chekeamos que este completo
		checkCompletion(); // es para ver si esta vacia la lista de enemigos, si lo está, avanza de ronda
		updateRoundTransition(delta);
	}

	private void handleInput(float delta) {
		
		// Rotacion del jugador
		if ((Gdx.input.isKeyPressed(Input.Keys.C))) { // Mayor precision
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
				player.rotate(ROTATE_ANGLE_SLOW);
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
				player.rotate(-ROTATE_ANGLE_SLOW);
		} else { // Movimiento normal
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
				player.rotate(ROTATE_ANGLE);
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
				player.rotate(-ROTATE_ANGLE);
		}

		// Aceleración del jugador
		
		float currentAcceleration = ACCELERATION;
		
		// mas lento
		if((Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))) {
			currentAcceleration = ACCELERATION_SLOW;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) //normal
			player.accelerate(currentAcceleration);
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			player.accelerate(-currentAcceleration);
		else
			// aplica la friccion del jugador
			player.applyFriction(this.currentLevelFriction);

		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			player.shoot(gameLogicHandler.getProyectilManager());
		}

	}

	public Player getPlayer() {
		return player;
	}

	public GameLogicHandler getGameLogicHandler() {
		return gameLogicHandler;
	}


	// para comporbar si todos los enemigos fueron derrotados
	/**
	 * Ahora DELEGA la lógica al Nivel actual.
	 */
	private void checkCompletion() {
		
		if (gameWon || levelComplete) return;
	    if (currentLevel == null) return;

	    // Si estamos listos para la siguiente ronda, no estamos ya en transición 
	    // y no quedan enemigos...
	    if (waitingForNextRound && !isTransitioningRound && 
	        gameLogicHandler.getEnemyManager().getEnemies().isEmpty()) {
	        
	        // 1. Activamos la transición
	        isTransitioningRound = true;
	        
	        // 2. Activamos la flag para que tu otra clase dibuje el texto en pantalla
	        roundComplete = true; 
	        
	        // 3. Reseteamos el timer
	        roundDelayTimer = 0f;
	    }else if (!waitingForNextRound && gameLogicHandler.getEnemyManager().getEnemies().isEmpty()) {
	    	this.waitingForNextRound = true; 
	    }
	}
	
	private void updateRoundTransition(float delta) {
	    if (isTransitioningRound) {
	        roundDelayTimer += delta;

	        
	        if (roundDelayTimer >= ROUND_DELAY_DURATION) {
	            advanceRoundLogic();
	            
	            // Reseteamos las flags
	            isTransitioningRound = false;
	            roundComplete = false;
	            roundDelayTimer = 0f;
	        }
	    }
	}
	
	private void advanceRoundLogic() {
	    boolean levelFinished = currentLevel.advanceToNextRound(gameLogicHandler);

	    if (levelFinished) {
	        int nextLevelIdx = currentLevelIndex + 1;
	        final int TOTAL_LEVELS = 5;

	        if (nextLevelIdx >= TOTAL_LEVELS) {
	            this.gameWon = true;
	        } else {
	            this.levelComplete = true;
	        }
	    } else {
	        this.waitingForNextRound = false; 
	    }
	}

	/**
	 * Devuelve el nombre del NIVEL actual
	 */
	public String getCurrentLevelName() {
		if (currentLevel != null) {
			return currentLevel.getLevelName();
		}
		return "Cargando...";
	}
	

	/**
	 * Devuelve el nombre de la RONDA actual
	 */
	public String getCurrentRoundName() {
		if (currentLevel != null) {
			return currentLevel.getCurrentRoundName();
		}
		return "";
	}

	public String getNextRoundName() {
		return currentLevel.getNextRoundName();
	}

	public EBackgroundType getBackground() {
		return currentLevel.getBackground();
	}

	/**
	 * Le avisa a GameScreen que el nivel terminó.
	 */
	public boolean isLevelComplete() {
		return levelComplete;
	}

	/**
	 * siguiente nivel.
	 */
	public int getNextLevelIndex() {
		// currentLevelIndex es el que *acabamos* de terminar
		return currentLevelIndex + 1;
	}

	// para pantalla de victoria
	/**
	 * Le avisa a GameScreen que el juego ha sido completado.
	 */
	public boolean isGameWon() {
		return gameWon;
	}

	public boolean isRoundComplete() {
		return roundComplete;
	}

}
