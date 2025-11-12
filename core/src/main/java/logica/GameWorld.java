package logica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import personajes.Player;

//coso para los enemigos y el cambio de ronda
import java.util.ArrayList; //no c que tan necesario
import java.util.List; // no c si rompa lo e Strategy      
import personajes.Enemy;


public class GameWorld {
	private final Player player;
	private final GameLogicHandler gameLogicHandler;
	
	//para rondas y demás
	private List<Round> allRounds;          // Lista de todas las Rondas
    private int currentRoundIndex;          // Indetificador pa que ver en que ronda avamos
    private boolean waitingForNextRound;
	
	
	private final float ROTATE_ANGLE = 5.0f;
	private final float ACCELERATION = 0.2f;
	private boolean estaEnPausa = false;
	
	public GameWorld() {
		this.player  = new Player(5, 5);
		this.gameLogicHandler = new GameLogicHandler();
		
		//para rondas
		this.currentRoundIndex = -1; // Empezamos en -1 para que la primera ronda sea la 0
        this.waitingForNextRound = true; // Listo para empezar la primera ronda
        
        initializeRounds(); // Creamos las rondas
	}
	
	public void update(float delta) {
		this.handleInput(delta);
		
		player.update(delta);
		
		//logica que el propio GameWolrd deebria manejar, no GameScreen
		gameLogicHandler.getEnemyManager().update(delta);
	    gameLogicHandler.getProyectilManager().update(
	            delta, 
	            player
	            );
		
		
		
		gameLogicHandler.handleCollisions(player);
		
		//para ronda
		checkRoundCompletion(); // es apra ver si esta vacia la lista de enemigos, si lo está, avanza de ronda
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
	
	
	//metodos apra que las "rondas" funcionen bien
	private void initializeRounds() {
        this.allRounds = new ArrayList<>();
        
        // RONDAS SIMPLES (usan tu spawner aleatorio)
        allRounds.add(new Round("Ronda 1: 5 Aleatorios", (em) -> {
            // ¡Llama al método que acabamos de hacer público!
            em.spawnEnemies(1); //son 5
        }));
        
        allRounds.add(new Round("Ronda 2: 12 Aleatorios", (em) -> {
            em.spawnEnemies(1); //son 12
        }));

        // RONDAS COMPLEJAS (usan el método 'add' para control total)
        allRounds.add(new Round("Ronda 3: Formación en V", (em) -> {
        	
        	// Stats por defecto para enemigos manuales
            float defaultSize = 100f; // El tamaño de la factory
            float defaultDrop = 0.05f;  // 5% de drop
            int defaultHealth = 50;   // 50 de vida
            int defaultDamage = 10;   // 10 de daño
        	
            // Usamos 'new Enemy' para control total de la posición
            em.add(new Enemy(500, 700, defaultSize, 1f, defaultHealth, defaultDamage));//centro
            em.add(new Enemy(450, 650, defaultSize, 1f, defaultHealth, defaultDamage));//izquierda
            em.add(new Enemy(550, 650, defaultSize, 1f, defaultHealth, defaultDamage));//derecha
        }));
        
        // RONDAS HÍBRIDAS
        allRounds.add(new Round("Ronda 4: Jefe con Escolta", (em) -> {
            em.spawnEnemies(4); // 4 aleatorios
            
            // Stats para el "Jefe"
            float bossSize = 400f; // El tamaño 
            float bossDrop = 0.5f;   // 50% drop
            int bossHealth = 200;  // Más vida
            int bossDamage = 25;   // Más daño
            
            em.add(new Enemy(400, 1200, bossSize, bossDrop, bossHealth, bossDamage));
        }));
    }
	
	//para comporbar si todos los enemigos fueron derrotados
	private void checkRoundCompletion() {
        // Caso 1: Estamos en medio de una ronda
        if (!waitingForNextRound) {
            
            // Comprobamos si la lista de enemigos está vacía
            if (gameLogicHandler.getEnemyManager().getEnemies().isEmpty()) {
                
                this.waitingForNextRound = true;
                // (Opcional: poner un timer aquí antes de la siguiente ronda)
                startNextRound(); 
            }
        }
        
        // Caso 2: Es el inicio del juego (primera ronda)
        if (currentRoundIndex == -1 && waitingForNextRound) {
            startNextRound();
        }
    }
	
	
	//logica de avanzar a lam siguiente ronda y respawnear los enemigos
	private void startNextRound() {
        currentRoundIndex++; // Avanzamos el índice de ronda
        
        if (currentRoundIndex < allRounds.size()) {
            // Hay más rondas, ¡vamos!
            
            // 1. Obtenemos el objeto Round
            Round nextRound = allRounds.get(currentRoundIndex);
            
            // 2. Ejecutamos su estrategia de spawn
            // (le pasamos el manager, pero la lambda ya sabe qué hacer)
            nextRound.executeSpawn(gameLogicHandler.getEnemyManager());
            
            // 3. (OPCIONAL) Actualizar HUD
            // hud.setRoundText(nextRound.getName());
            
            // 4. Marcamos que el juego está en curso
            this.waitingForNextRound = false; 
            
        } else {
            // ¡Juego Ganado! No hay más rondas
            //System.out.println("¡HAS GANADO EL JUEGO!");
            
        }
    }
	
	//esto sirve para conectar ls rondas con el HUDLayout (osea mostarlo y actulizarlo en el draw)
	public String getCurrentRoundName() {
                if (currentRoundIndex < 0) {
            return "1"; // O "Preparando..."
        }
        
        // Si estamos en una ronda válida
        if (currentRoundIndex < allRounds.size()) {
            return allRounds.get(currentRoundIndex).getName();
        }
        
        // Si se acabaron las rondas
        return "¡Sobreviviste!";
    }
}
