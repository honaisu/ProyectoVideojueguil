package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import armas.Shotgun;
import armas.Melee;
import entidades.Enemy;
import entidades.Player;
import entidades.WeaponDrop;
import enumeradores.EScreenType;
import enumeradores.recursos.EGameMusic;
import enumeradores.recursos.EPlayerSkin; 
import logica.GameLogicHandler;
import logica.MainGame;
import managers.assets.AssetManager;

public class TutorialScreen extends BaseScreen {
	
	//TODO pasarloa  un enum pa que no este feo aqui
    private enum Step { 
        WELCOME, 
        MOVE,           // 1. Moverse por el escenario
        SHOOT,          // 2. Disparar 
        SHOOT_DELAY,    // 3. Pausa de 2 segundos antes del spawn de enemigos estaticos
        SPAWN_STATIC,   // 4. Spawnean los eenmigos estaticos
        KILL_STATIC,	// 5. Derrotar enemigos estáticos
        SPAWN_WEAPON,   // 6. Sistema de cambio de armas: spawn
        PICKUP_WEAPON,  // 7. Espera recolección O desaparición
        DROP_WARNING,   // 8. Muestra advertencia y prepara respawn
        SPAWN_HORDE,    // 9. Horda pequeña con enemigos moviéndose
        SURVIVE_HORDE,
        END 
    }

    private Step pasoActual = Step.WELCOME;
    private float timer = 0f;

    // Constantes de Movimiento que ocupambamos en GameWold
    private final float ROTATE_ANGLE = 5.0f;
    private final float ROTATE_ANGLE_SLOW = 1.0f;
    private final float ACCELERATION = 100f;
    private final float TUTORIAL_FRICTION = 0.9f; // Fricción del nivel "general"

    // Control de lógica del tutorial 
    private boolean stepInitialized = false; 

    // Entidades del Juego
    private Player player;
    private GameLogicHandler gameLogicHandler;
    
    // UI y Control
    private final float worldW;
    private final float worldH;
    private final GlyphLayout layout = new GlyphLayout();
    private Music musicaTutorial;
    
    
    public TutorialScreen(MainGame game) {
        super(game);
        this.worldW = game.getViewport().getWorldWidth();
        this.worldH = game.getViewport().getWorldHeight();
        this.musicaTutorial = AssetManager.getInstancia().getMusic(EGameMusic.TUTORIAL);

        // Inicializamos los objetos una vez
        this.player = new Player(worldW / 2, worldH / 2, EPlayerSkin.ORIGINAL); 
        this.gameLogicHandler = new GameLogicHandler();
    }
    
    /**
     * Resetea el estado del tutorial, el jugador y el mundo del juego.
     */
    private void resetTutorial() {
        // Reiniciar variables de control
        pasoActual = Step.WELCOME;
        timer = 0f;
        stepInitialized = false;

        // Reiniciar lógica del juego (Elimina enemigos, balas, drops)
        // La forma más segura es crear nuevas instancias para Player y GameLogicHandler
        this.player = new Player(worldW / 2, worldH / 2, EPlayerSkin.ORIGINAL);
        this.gameLogicHandler = new GameLogicHandler();
        
        // Colocar al jugador en el centro
        this.player.getPosition().set(worldW / 2, worldH / 2);
    }

    @Override
    public void show() {
        // Reiniciar el juego CADA VEZ que se muestra la pantalla
        resetTutorial(); 
        musicaTutorial.setLooping(true);
        musicaTutorial.play();
    }

    @Override
    protected void update(float delta) {
        // Manejar la entrada del jugador 
        handleTutorialInput(delta); 
        
        // Actualizar lógica del juego
        player.update(delta);
        gameLogicHandler.update(delta);
        gameLogicHandler.handleCollisions(player);

        // Máquina de Estados del Tutorial (ver en que "parte" de este vamos)
        checkTutorialProgression(delta);
        
        // Si el jugador muere, se le cura o reinicia (para no salir del tutorial)
        if (player.isDead()) {
             // Curar completamente
             player.takeDamage(-100); 
        }
    }
    
    /**
     * Maneja la entrada de teclado para controlar al jugador (Movimiento y Disparo).
     */
    private void handleTutorialInput(float delta) {
        
        // Solo procesamos input si NO hemos terminado
        if (pasoActual != Step.END) {
            // Lógica de rotación (incluye la precisión con 'C')
            if ((Gdx.input.isKeyPressed(Input.Keys.C))){	
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.rotate(ROTATE_ANGLE_SLOW);
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.rotate(-ROTATE_ANGLE_SLOW);
            }else { 
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.rotate(ROTATE_ANGLE);
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.rotate(-ROTATE_ANGLE);
            }
            
            // Lógica de aceleración
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.accelerate(ACCELERATION);
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.accelerate(-ACCELERATION);
            else 
                // Fricción
                player.applyFriction(this.TUTORIAL_FRICTION);
            
            // Lógica de disparo
            if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                player.shoot(gameLogicHandler.getProyectilManager());
            }
        }

        // Siempre permitimos salir con ENTER/ESCAPE
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
             if (pasoActual == Step.END) {
                musicaTutorial.stop();
                getGame().getPantallaManager().cambiarPantalla(EScreenType.MENU);
             }
        }
    }


    private void checkTutorialProgression(float delta) {
        timer += delta;

        switch (pasoActual) {
            case WELCOME:
                if (timer > 2f || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                    changeStep(Step.MOVE);
                }
                break;

            case MOVE:
                if (player.getVelocity().len() > 10f && timer > 2f) {
                    changeStep(Step.SHOOT);
                }
                break;

            case SHOOT:
                if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                    changeStep(Step.SHOOT_DELAY);
                }
                break;
                
            case SHOOT_DELAY:
                if (timer >= 2.0f) {
                    changeStep(Step.SPAWN_STATIC);
                }
                break;


            case SPAWN_STATIC:
                if (!stepInitialized) {
                    // Crea 3 enemigos de práctica (con velocidad 0)
                    spawnPracticeEnemy(worldW / 2 - 300, worldH / 2 + 100);
                    spawnPracticeEnemy(worldW / 2 + 300, worldH / 2);
                    spawnPracticeEnemy(worldW / 2 - 300, worldH / 2 - 100);
                    stepInitialized = true;
                }
                changeStep(Step.KILL_STATIC);
                break;

            case KILL_STATIC:
                if (gameLogicHandler.getEnemyManager().isEmpty()) {
                    changeStep(Step.SPAWN_WEAPON);
                }
                break;
                
            case SPAWN_WEAPON:
                if (!stepInitialized) {
                    player.setWeapon(new Melee()); 
                    
                    // Spawneamos el drop de Shotgun
                    WeaponDrop drop = new WeaponDrop(
                        worldW / 2, 
                        worldH / 2 - 150, 
                        new Shotgun() 
                    );
                    gameLogicHandler.getDropManager().add(drop);
                    stepInitialized = true;
                }
                changeStep(Step.PICKUP_WEAPON);
                break;

            case PICKUP_WEAPON:
                // 1. Éxito: Jugador recoge el arma
                if (player.getWeapon() instanceof Shotgun) {
                     changeStep(Step.SPAWN_HORDE);
                } 
                // 2. Fracaso: Drop desaparece antes de ser recogido
                else if (gameLogicHandler.getDropManager().getDrops().isEmpty()) {
                    changeStep(Step.DROP_WARNING);
                }
                break;
                
            case DROP_WARNING:
                // Espera 3 segundos para que el jugador lea la advertencia
                if (timer >= 3.0f) {
                    // Vuelve a spawnear el arma
                    changeStep(Step.SPAWN_WEAPON);
                }
                break;


            case SPAWN_HORDE:
                if (!stepInitialized) {
                    // Spawneamos 4 enemigos normales (con velocidad)
                    gameLogicHandler.getEnemyManager().spawnEnemies(4);
                    stepInitialized = true;
                }
                changeStep(Step.SURVIVE_HORDE);
                break;

            case SURVIVE_HORDE:
                if (gameLogicHandler.getEnemyManager().isEmpty()) {
                    changeStep(Step.END);
                }
                break;

            case END:
                // Solo esperamos aquí, la salida se maneja en handleTutorialInput
                break;
        }
    }

    private void changeStep(Step nextStep) {
        pasoActual = nextStep;
        timer = 0f;
        stepInitialized = false; 
    }

    /**
     * Helper para crear enemigos de práctica (velocidad 0).
     */
    private void spawnPracticeEnemy(float x, float y) {
        // Los valores de rareDrop, HP, y Damage son bajos para ser simples maniquíes
        Enemy dummy = new Enemy(x, y, 100f, 0f, 50, 0); 
        
        // TRUCO: Le ponemos velocidad 0 para que no se muevan (estáticos)
        dummy.setVelocity(0, 0); 
        
        gameLogicHandler.getEnemyManager().add(dummy);
    }

    @Override
    protected void draw(SpriteBatch batch, BitmapFont font) {
        // 1. Fondo
        ScreenUtils.clear(0.1f, 0.12f, 0.16f, 1f);

        // 2. Dibujar el MUNDO y la UI
        batch.begin();
        
        gameLogicHandler.draw(batch);
        player.draw(batch);
        
        drawInstructions(batch, font);
        
        batch.end();
    }

    private void drawInstructions(SpriteBatch batch, BitmapFont font) {
        float cx = worldW / 2;
        float cy = worldH * 0.9f; 
        
        font.getData().setScale(1.5f);
        String texto = "";
        String subtexto = "";
        String warning = ""; 

        switch (pasoActual) {
            case WELCOME:
                texto = "ENTRENAMIENTO INICIADO";
                subtexto = "El escenario permanecerá fijo. Presiona cualquier tecla para continuar.";
                break;
            case MOVE:
                texto = "PASO 1: MOVIMIENTO";
                subtexto = "Usa las FLECHAS (y C para precisión) para moverte.";
                break;
            case SHOOT:
                texto = "PASO 2: DISPARO BÁSICO";
                // --- TEXTO MODIFICADO ---
                subtexto = "Presiona Z para disparar. Si la munición se agota, el Cuchillo se activa.";
                warning = "[En el Tutorial, la munición es infinita, pero esta es la mecánica real]";
                // --- FIN TEXTO MODIFICADO ---
                break;
            case SHOOT_DELAY:
                texto = "PASO 2: DISPARO BÁSICO";
                // --- TEXTO MODIFICADO ---
                subtexto = "Cuchillo activo si la munición se agota. PREPARANDO OBJETIVOS...";
                warning = "[Munición infinita en el tutorial]";
                // --- FIN TEXTO MODIFICADO ---
                break;
            case SPAWN_STATIC:
            case KILL_STATIC:
                texto = "PASO 3: OBJETIVOS ESTÁTICOS";
                subtexto = "Destruye a los 3 maniquíes de prueba. ¡Dispara a discreción!";
                break;
            case SPAWN_WEAPON:
            case PICKUP_WEAPON:
                texto = "PASO 4: CAMBIO DE ARMAS";
                subtexto = "Acércate y camina sobre la caja (Escopeta) para equiparla.";
                break;
            case DROP_WARNING:
                texto = "¡ADVERTENCIA!";
                subtexto = "El arma desapareció. En el juego real tienes tiempo limitado para recogerlas.";
                warning = "Respawn en: " + String.format("%.1f", 5.0f - timer) + "s. ¡Inténtalo de nuevo!";
                break;
            case SPAWN_HORDE:
            case SURVIVE_HORDE:
                texto = "PASO 5: PRUEBA FINAL";
                subtexto = "¡Horda pequeña! Elimina a los 4 enemigos móviles para finalizar el entrenamiento.";
                break;
            case END:
                texto = "¡ENTRENAMIENTO COMPLETADO!";
                subtexto = "Estás listo para el menú principal. Presiona ENTER o ESC.";
                break;
        }

        // Título
        layout.setText(font, texto);
        font.draw(batch, texto, cx - layout.width / 2, cy);
        
        // Subtítulo
        font.getData().setScale(1.0f);
        layout.setText(font, subtexto);
        font.draw(batch, subtexto, cx - layout.width / 2, cy - 40);
        
        // Advertencia (Drop Time o Delay)
        if (!warning.isEmpty()) {
            font.getData().setScale(1.0f);
            layout.setText(font, warning);
            font.draw(batch, warning, cx - layout.width / 2, cy - 80);
        }
    }
    
    @Override
    public void dispose() {
        musicaTutorial.stop();
        // Nota: No es necesario llamar a dispose en player/gameLogicHandler
        // ya que el ScreenManager los reemplazará con una nueva instancia
        // al volver a entrar al tutorial.
    }
}