package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import entidades.Player;
import enumeradores.recursos.EBackgroundType;
import enumeradores.recursos.EPlayerSkin;
import factories.LevelFactory;

//coso para los enemigos y el cambio de ronda
import java.util.ArrayList; //no c que tan necesario
import java.util.List; // no c si rompa lo e Strategy

import logica.levels.Level;

public class GameWorld {
	private final GameLogicHandler gameLogicHandler;
	private final Player player;

	private List<Level> allLevels; // Lista de Niveles
	private int currentLevelIndex; // Índice para saber en qué nivel vamos
	private Level currentLevel; // El objeto Nivel que estamos jugando AHORA
	private boolean waitingForNextRound; // Esto se mantiene, pero ahora lo usa el Nivel
	private boolean gameWon = false; // flag para ver si ya ganamos
	private boolean levelComplete = false; // flag para ver si se completo el nivel (para avizar a GameScreen)
	private float currentLevelFriction = 0.9f; // tipo flag para ver que fricciones tenemos actualemnte (normal es 0.9f)

	private final float ROTATE_ANGLE = 5.0f;
	private final float ROTATE_ANGLE_SLOW = 1.0f;
	private final float ACCELERATION = 100f;
	private boolean estaEnPausa = false;

	public GameWorld(int startingLevelIndex, EPlayerSkin skin) {
		this.player = new Player(5, 5, skin);
		this.gameLogicHandler = new GameLogicHandler();

		// para niveles
		this.allLevels = new ArrayList<>();

		// creamos y agregamos los niveles //TODO creo que se van a crear y agregar como
		// listas?
		this.allLevels.add(LevelFactory.createLevelOne());
		this.allLevels.add(LevelFactory.createLevelTwo());
		this.allLevels.add(LevelFactory.createLevelThree());
		this.allLevels.add(LevelFactory.createLevelFour());
		this.allLevels.add(LevelFactory.createLevelFive());

		// nuevo para el tema de inciar los niveles
		this.currentLevelIndex = startingLevelIndex - 1;
		this.waitingForNextRound = true;

		// y creamos el primer nivel
		startNextLevel();

		// TODO mejora esto// crea los obstaculos apra el nivel 1
		gameLogicHandler.getObstacleManager().spawnObstacles(currentLevel.getHazardCount(),
				currentLevel.getSolidCount(), currentLevel.getBackground());

	}

	public void update(float delta) {
		this.handleInput(delta);
		player.update(delta);

		gameLogicHandler.update(delta);

		gameLogicHandler.handleCollisions(player);

		// Chekeamos que este completo
		checkCompletion(); // es apra ver si esta vacia la lista de enemigos, si lo está, avanza de ronda
	}

	private void handleInput(float delta) {

		// Lógica de rotación de benjoid con coso de C
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

		// Lógica de aceleración
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			player.accelerate(ACCELERATION);
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			player.accelerate(-ACCELERATION);
		else
			// Lógica de fricción de 'HEAD'
			player.applyFriction(this.currentLevelFriction);

		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			player.shoot(gameLogicHandler.getProyectilManager());
		}

		// La idea se marcar si está en pausa o no
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

	// para manejar si está en pausa o no
	public boolean isEstaEnPausa() {
		return estaEnPausa;
	}

	public void setEstaEnPausa(boolean estaEnPausa) {
		this.estaEnPausa = estaEnPausa;
	}

	/**
	 * Carga el siguiente nivel de la lista.
	 */
	private void startNextLevel() {

		currentLevelIndex++; // Avanzamos al siguiente nivel

		if (currentLevelIndex < allLevels.size()) {
			// Cargamos el objeto Nivel
			currentLevel = allLevels.get(currentLevelIndex);

			// Actualizamos la friccion del nivel
			this.currentLevelFriction = currentLevel.getPlayerFriction();

			// Creamos los obstaculos apra los siguientes niveles //TODO ver si puedo
			// dejarlo ma wonito
			gameLogicHandler.getObstacleManager().spawnObstacles(currentLevel.getHazardCount(),
					currentLevel.getSolidCount(), currentLevel.getBackground());

			// Marcamos que estamos listos para la *primera ronda*
			this.waitingForNextRound = true;
		}
	}

	// para comporbar si todos los enemigos fueron derrotados
	/**
	 * Ahora DELEGA la lógica al Nivel actual.
	 */
	private void checkCompletion() {

		if (gameWon || levelComplete)
			return;
		if (currentLevel == null)
			return; // Aún no ha cargado nada

		// Si estamos esperando
		// Y la lista de enemigos está vacía
		if (waitingForNextRound && gameLogicHandler.getEnemyManager().getEnemies().isEmpty()) {

			// Le pedimos al NIVEL (currentLevel) que avance a su siguiente ronda
			boolean levelFinished = currentLevel.advanceToNextRound(gameLogicHandler);

			if (levelFinished) {
				// El nivel nos dijo que se quedó sin rondas
				int nextLevelIdx = currentLevelIndex + 1;

				if (nextLevelIdx >= allLevels.size()) {
					// ¡Ganó el juego! No hay más niveles.
					this.gameWon = true;
					System.out.println("¡HAS GANADO EL JUEGO!"); // (Lo movemos aquí)

				} else {
					// No ha ganado, solo pasa de nivel
					this.levelComplete = true;
				}
			} else {
				// El nivel aún tiene rondas.
				this.waitingForNextRound = false;
			}
		}

		// Si NO estamos esperando (estamos en media ronda)
		// y la lista se vacía, nos preparamos para la siguiente.
		else if (!waitingForNextRound && gameLogicHandler.getEnemyManager().getEnemies().isEmpty()) {
			this.waitingForNextRound = true; // Listo para la siguiente ronda
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
		return ""; // Vacío mientras carga
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
	 * Le dice a GameScreen cuál es el *siguiente* nivel.
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

}
