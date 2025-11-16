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
	
	private List<Level> allLevels;       // Lista de Niveles
    private int currentLevelIndex;       // Índice para saber en qué nivel vamos
    private Level currentLevel;          // El objeto Nivel que estamos jugando AHORA
    private boolean waitingForNextRound; // Esto se mantiene, pero ahora lo usa el Nivel
    private boolean gameWon = false; //flag para ver si ya ganamos
    private boolean levelComplete = false; //flag para ver si se completo el nivel (para avizar a GameScreen)
	
	private final float ROTATE_ANGLE = 5.0f;
	private final float ROTATE_ANGLE_SLOW = 1.0f;
	private final float ACCELERATION = 100f;
	private boolean estaEnPausa = false;
	
	public GameWorld(int startingLevelIndex, EPlayerSkin skin) {
		this.player  = new Player(5, 5, skin);
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
		
	    gameLogicHandler.update(delta);
		gameLogicHandler.handleCollisions(player);
		
		//Chekeamos que este completo
		checkCompletion(); // es apra ver si esta vacia la lista de enemigos, si lo está, avanza de ronda
	}

	private void handleInput(float delta) {
		if ((Gdx.input.isKeyPressed(Input.Keys.C))){	//Mayor precision
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.rotate(ROTATE_ANGLE_SLOW);
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.rotate(-ROTATE_ANGLE_SLOW);
		}else { //Movimiento normal
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.rotate(ROTATE_ANGLE);
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.rotate(-ROTATE_ANGLE);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.accelerate(ACCELERATION);
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.accelerate(-ACCELERATION);
		else player.applyFriction(52f*delta); //TODO nacho revisa si te va bien :3
		
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			player.shoot(gameLogicHandler.getProyectilManager());
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
            
            // 2. ¡¡AQUÍ CAMBIARÍAMOS EL FONDO!!
            // (Hablaremos de esto en el Paso 2)
            
            // 3. Marcamos que estamos listos para la *primera ronda* de este nivel
            this.waitingForNextRound = true;
            return;
        }
        
        // Terminan los niveles
        this.gameWon = true; 
    }
	
	//para comporbar si todos los enemigos fueron derrotados
	/**
	 * Reemplaza a 'checkRoundCompletion' y 'startNextRound'.
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
                // ¡Así que cargamos el siguiente NIVEL!
            	this.levelComplete = true;
            } else {
                // El nivel aún tiene rondas.
                // Acabamos de empezar una nueva, así que dejamos de esperar.
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

	public boolean gameWon() {
		return gameWon;
	}
}
