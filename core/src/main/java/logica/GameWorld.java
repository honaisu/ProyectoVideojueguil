package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import personajes.Player;

//coso para los enemigos y el cambio de ronda
import java.util.ArrayList; //no c que tan necesario
import java.util.List; // no c si rompa lo e Strategy      
//import personajes.Enemy; antes con logica de enemigos y rondas
//ahora con la logica de los niveles
import logica.levels.Level; 
import logica.levels.LevelFactory; 


public class GameWorld {
	private final Player player;
	private final GameLogicHandler gameLogicHandler;
	
	//para rondas y demás
	/*private List<Round> allRounds;          // Lista de todas las Rondas
    private int currentRoundIndex;          // Indetificador pa que ver en que ronda avamos
    private boolean waitingForNextRound;*/ //cambiamos todo para que level gestione esto
	
	private List<Level> allLevels;       // Lista de Niveles
    private int currentLevelIndex;       // Índice para saber en qué nivel vamos
    private Level currentLevel;          // El objeto Nivel que estamos jugando AHORA
    private boolean waitingForNextRound; // Esto se mantiene, pero ahora lo usa el Nivel
    private boolean gameWon = false; //flag para ver si ya ganamos
    private boolean levelComplete = false; //flag para ver si se completo el nivel (para avizar a GameScreen)
	
	private final float ROTATE_ANGLE = 5.0f;
	private final float ACCELERATION = 0.2f;
	private boolean estaEnPausa = false;
	
	public GameWorld(int startingLevelIndex) {
		this.player  = new Player(5, 5);
		this.gameLogicHandler = new GameLogicHandler();
		
		//para niveles
		this.allLevels = new ArrayList<>();
		
		//creamos y agregamos los 2 primeros niveles por el momento
		this.allLevels.add(LevelFactory.createLevelOne());
        this.allLevels.add(LevelFactory.createLevelTwo());
        
        //nuevo para el tema de inciar los niveles
        this.currentLevelIndex = startingLevelIndex -1;
        this.waitingForNextRound = true;
        
        //y creamos el primer nivel
        startNextLevel();
	}
	
	public void update(float delta) {
		this.handleInput(delta);
		
		player.update(delta);
		
		//logica que el propio GameWolrd deebria manejar, no GameScreen
		gameLogicHandler.getEnemyManager().update(delta);
	    gameLogicHandler.getProyectilManager().update(
	            delta, 
	            player.getSpr().getBoundingRectangle(), 
	            player.getRotation()
	            );
		
		
		
		gameLogicHandler.handleCollisions(player);
		
		//Chekeamos que este completo
		checkCompletion(); // es apra ver si esta vacia la lista de enemigos, si lo está, avanza de ronda
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
	
	
	/**
	 * Carga el siguiente nivel de la lista.
	 * (Este es el método que reemplaza la idea de 'initializeRounds').
	 */
	private void startNextLevel() {
	    currentLevelIndex++; // Avanzamos al siguiente nivel

	    if (currentLevelIndex < allLevels.size()) {
	        // 1. Cargamos el objeto Nivel
	        currentLevel = allLevels.get(currentLevelIndex);

	        // 2. Marcamos que estamos listos para la *primera ronda*
	        this.waitingForNextRound = true; 
	    }
	    // ¡El 'else' que imprimía "GANASTE" se elimina!
	    // 'checkCompletion' ahora se encarga de la victoria.
	}
	
	//para comporbar si todos los enemigos fueron derrotados
	/**
	 * Ahora DELEGA la lógica al Nivel actual.
	 */
	private void checkCompletion() {
		
		if (gameWon || levelComplete) return;
        if (currentLevel == null) return; // Aún no ha cargado nada

        // Si estamos esperando (para la 1ra ronda o la siguiente)
        // Y la lista de enemigos está vacía...
        if (waitingForNextRound && gameLogicHandler.getEnemyManager().getEnemies().isEmpty()) {
            
            // Le pedimos al NIVEL (currentLevel) que avance a su siguiente ronda
            boolean levelFinished = currentLevel.advanceToNextRound(gameLogicHandler);

            if (levelFinished) {
                // El nivel nos dijo que se quedó sin rondas
            	// ¡¡AQUÍ CHEQUEAMOS SI EL JUEGO TERMINÓ!!
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
     * Devuelve el nombre del NIVEL actual (ej: "Nivel 1: El Espacio")
     */
	public String getCurrentLevelName() {
        if (currentLevel != null) {
            return currentLevel.getLevelName();
        }
        return "Cargando...";
    }
	
	
	/**
     * Devuelve el nombre de la RONDA actual (ej: "Ronda 1")
     * (Ahora le pregunta al Nivel cuál es su ronda actual)
     */
    public String getCurrentRoundName() {
        if (currentLevel != null) {
            return currentLevel.getCurrentRoundName();
        }
        return ""; // Vacío mientras carga
    }
	
    
    
    /**
     * Devuelve el path de la textura de fondo del nivel actual
     * para que GameScreen sepa qué dibujar.
     */
    //creo que sirve apra el tema de los fondos//pude que de error
    public String getCurrentBackgroundPath() {
        if (currentLevel != null) {
            return currentLevel.getBackgroundAssetPath();
        }
        // Devuelve un fondo "default" si todo falla
        // (Asegúrate que este archivo exista)
        return "fondos/fondoNivelUno.png"; 
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
    
    //para pantalla de victoria
    /**
     * Le avisa a GameScreen que el juego ha sido completado.
     */
    public boolean isGameWon() {
        return gameWon;
    }
    
}
