package logica;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import pantallas.PantallaMenu;

public class SpaceNavigation extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public OrthographicCamera camera;
	public Viewport viewport;
	public AssetsLoader assets;
	
	private int highScore;
	
	/**
	 * Método encargado de crear el juego
	 * Crea una camara y UNA pantalla.
	 * 
	 * TODO futuro colaborador si quieres reemplazas esto pq es mi implementacion :D
	 */
	@Override
	public void create() {
		highScore = 0;
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(2f);
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(1200, 800, camera);
		
		assets = new AssetsLoader();
		assets.load();
		
		this.setScreen(new PantallaMenu(this));
	}

	@Override
	public void render() {
		super.render(); // important!
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		assets.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
}