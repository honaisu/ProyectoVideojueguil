package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//para el fondo
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


import enumeradores.EScreenType;
import logica.GameWorld;
import logica.MainGame;
import pantallas.BaseScreen;
//puede que esto de error
import managers.AssetManager;

/**
 * Clase que representa una pantalla del juego, con su propio mundo.
 */
public class GameScreen extends BaseScreen {
	private final GameWorld world;
	private final HUDLayout hud = new HUDLayout();
	
	//cosas utiles para el tema del fondo 
	private Sprite backgroundSprite;      // El sprite que usaremos para el fondo
    private String currentBackgroundPath; // y el sprite del fondo actual (va a servir para más adelante)

	public GameScreen(MainGame game) {
		super(game);
		this.world = new GameWorld(game.getNextLevelToLoad());
		
		//nuevo para ver el tema del fondo
		this.currentBackgroundPath = world.getCurrentBackgroundPath();
		//esto debe cargarse en otro lado yo creo// pero estaes la textura del fondo creo
		Texture bgTexture = AssetManager.getInstancia().getFondo(this.currentBackgroundPath);
		
		
		//cremoas el sprite  lo ajustamos a la pantalla
		this.backgroundSprite = new Sprite(bgTexture);
        this.backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
		

	@Override
	protected void update(float delta) {
		world.update(delta);
		
		if (world.isGameWon()) {
            
            // ¡CAMBIADO! Ahora va a la pantalla de Victoria
            getGame().getPantallaManager().cambiarPantalla(EScreenType.VICTORIA); 
            return; // Detenemos el update
        }

	    // Chqeuamos la transicion
	    if (world.isLevelComplete()) {

	        // le decimos a mi game cual es el siguiente nivel
	        getGame().setNextLevelToLoad(world.getNextLevelIndex());

	        // 2. Cambiamos a la pantalla de transición
	        getGame().getPantallaManager().cambiarPantalla(EScreenType.TRANSICION);
	        return; // Detenemos este update
	    }

	    // Y el update si muere o pausa
		if (world.getPlayer().isDead()) {
			getGame().setNextLevelToLoad(0);//cambiamos al nivel 1
			getGame().getPantallaManager().cambiarPantalla(EScreenType.GAME_OVER);
			
			return; 
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			getGame().getPantallaManager().cambiarPantalla(EScreenType.PAUSA);
		}
	}
	
	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//logica de dibujar y mostrar el fondo
		// 1. Preguntamos al 'world' cuál debería ser el fondo
        String newBgPath = world.getCurrentBackgroundPath();
     // 2. Comparamos con el que estamos usando
        if (!newBgPath.equals(this.currentBackgroundPath)) {
            // ¡Cambiamos de nivel! Hay que cargar la nueva textura
        	Texture newTexture = AssetManager.getInstancia().getFondo(newBgPath);
            backgroundSprite.setTexture(newTexture);
            this.currentBackgroundPath = newBgPath; // Actualizamos nuestro path
        }
        
        //// Le preguntamos al GameWorld por AMBOS strings
        String levelName = world.getCurrentLevelName();
		String roundName = world.getCurrentRoundName();
        
		batch.begin();
		
		
		//dibujarmos el fondo ahora si
		backgroundSprite.draw(batch);
		
		//ahora antes de "dibujar" le preguntamos al GameWorld//
		//String currentRoundName = world.getCurrentRoundName();
		//batch.begin();
		world.getPlayer().draw(batch);
		world.getGameLogicHandler().render(batch);
		//hud.draw(batch, font, world.getPlayer(), getGame().getHighScore(), currentRoundName);
		hud.draw(batch, font, world.getPlayer(), getGame().getHighScore(), levelName, roundName);
		
		batch.end();
	}
}
