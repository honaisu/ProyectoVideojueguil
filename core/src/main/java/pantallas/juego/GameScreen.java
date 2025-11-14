package pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//para el fondo
import com.badlogic.gdx.graphics.g2d.Sprite;


import enumeradores.EScreenType;
import enumeradores.recursos.EBackgroundType;
import factories.SpriteFactory;
import logica.GameWorld;
import logica.MainGame;
import pantallas.BaseScreen;

/**
 * Clase que representa una pantalla del juego, con su propio mundo.
 */
public class GameScreen extends BaseScreen {
	private final GameWorld world;
	private final HUDLayout hud = new HUDLayout();
	
	//cosas utiles para el tema del fondo 
	private Sprite backgroundSprite; // El sprite que usaremos para el fondo
	
	public GameScreen(MainGame game) {
		super(game);
		this.world = new GameWorld(game.getNextLevelToLoad(), game.getPlayerSkin());
		
		EBackgroundType background = world.getBackground();
		backgroundSprite = SpriteFactory.create(background);
		backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	protected void update(float delta) {
		world.update(delta);

	    // Chqeuamos la transicion
	    if (world.isLevelComplete()) {
	        // le decimoa  amin game cual es el siguiente nivel
	        getGame().setNextLevelToLoad(world.getNextLevelIndex());
	        getGame().getPantallaManager().cambiarPantalla(EScreenType.TRANSICION);
	        return;
	    }
	    
	    if (world.gameWon()) {
	    	// TODO Implementar pantalla de ganada :D
	    	getGame().getPantallaManager().cambiarPantalla(EScreenType.GAME_OVER);
	    }

	    // Y el update si muere o pausa
		if (world.getPlayer().isDead()) {
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
        
        // Le preguntamos al GameWorld por AMBOS strings
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
