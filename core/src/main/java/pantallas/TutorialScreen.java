package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import enumeradores.EWeaponType;
import factories.WeaponFactory;
import enumeradores.recursos.texturas.EEnemyType;

import entidades.Enemy;
import entidades.Player;
import entidades.WeaponDrop;
import enumeradores.EScreenType;
import enumeradores.recursos.EGameMusic;
import enumeradores.EPlayerSkin;
import logica.GameLogicHandler;
import logica.MainGame;
import managers.assets.AssetManager;

public class TutorialScreen extends BaseScreen {
	
	
	//TODO ver si hago un enum para esto
	private enum Step {
		WELCOME, MOVE, SHOOT, SHOOT_DELAY, SPAWN_STATIC, KILL_STATIC, SPAWN_WEAPON, PICKUP_WEAPON, DROP_WARNING,
		SPAWN_HORDE, SURVIVE_HORDE, END
	}

	private Step pasoActual = Step.WELCOME;
	private float timer = 0f;

	private final float ROTATE_ANGLE = 5.0f;
	private final float ROTATE_ANGLE_SLOW = 1.0f;
	private final float ACCELERATION = 100f;
	private final float ACCELERATION_SLOW = 10f;
	private final float TUTORIAL_FRICTION = 0.9f;

	private boolean stepInitialized = false;

	private Player player;
	private GameLogicHandler gameLogicHandler;

	private final float worldW;
	private final float worldH;
	private final GlyphLayout layout = new GlyphLayout();
	private Music musicaTutorial;

	public TutorialScreen(MainGame game) {
		super(game);
		this.worldW = game.getViewport().getWorldWidth();
		this.worldH = game.getViewport().getWorldHeight();
		this.musicaTutorial = AssetManager.getInstancia().getMusic(EGameMusic.TUTORIAL);

		this.player = new Player(worldW / 2, worldH / 2, EPlayerSkin.ORIGINAL);
		this.gameLogicHandler = new GameLogicHandler();
	}

	private void resetTutorial() {
		pasoActual = Step.WELCOME;
		timer = 0f;
		stepInitialized = false;

		this.player = new Player(worldW / 2, worldH / 2, EPlayerSkin.ORIGINAL);
		this.gameLogicHandler = new GameLogicHandler();

		this.player.getPosition().set(worldW / 2, worldH / 2);
	}

	@Override
	public void show() {
		resetTutorial();
		musicaTutorial.setLooping(true);
		musicaTutorial.play();
	}

	@Override
	protected void update(float delta) {
		handleTutorialInput(delta);

		player.update(delta);
		gameLogicHandler.update(delta);
		gameLogicHandler.handleCollisions(player);

		checkTutorialProgression(delta);

		if (player.isDead()) {
			player.takeDamage(-100);
		}
	}

	private void handleTutorialInput(float delta) {
		if (pasoActual != Step.END) {
			if ((Gdx.input.isKeyPressed(Input.Keys.C))) {
				if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
					player.rotate(ROTATE_ANGLE_SLOW);
				if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
					player.rotate(-ROTATE_ANGLE_SLOW);
			} else {
				if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
					player.rotate(ROTATE_ANGLE);
				if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
					player.rotate(-ROTATE_ANGLE);
			}

			float currentAcceleration = ACCELERATION;
			if((Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))) {
				currentAcceleration = ACCELERATION_SLOW;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) //normal
				player.accelerate(currentAcceleration);
			else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
				player.accelerate(-currentAcceleration);
			else
				// aplica la friccion del jugador
				player.applyFriction(this.TUTORIAL_FRICTION);

			if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
				player.shoot(gameLogicHandler.getProyectilManager());
			}
		}

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
			if (timer > 2f || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
				changeStep(Step.MOVE);
			break;
		case MOVE:
			if (player.getVelocity().len() > 10f && timer > 2f)
				changeStep(Step.SHOOT);
			break;
		case SHOOT:
			if (Gdx.input.isKeyJustPressed(Input.Keys.Z))
				changeStep(Step.SHOOT_DELAY);
			break;
		case SHOOT_DELAY:
			if (timer >= 4.0f)
				changeStep(Step.SPAWN_STATIC);
			break;
		case SPAWN_STATIC:
			if (!stepInitialized) {
				spawnPracticeEnemy(worldW / 2 - 300, worldH / 2 + 100);
				spawnPracticeEnemy(worldW / 2 + 300, worldH / 2);
				spawnPracticeEnemy(worldW / 2 - 300, worldH / 2 - 100);
				stepInitialized = true;
			}
			changeStep(Step.KILL_STATIC);
			break;
		case KILL_STATIC:
			if (gameLogicHandler.getEnemyManager().isEmpty())
				changeStep(Step.SPAWN_WEAPON);
			break;

		case SPAWN_WEAPON:
			if (!stepInitialized) {
				player.setWeapon(WeaponFactory.create(EWeaponType.MELEE));

				WeaponDrop drop = new WeaponDrop(worldW / 2, worldH / 2 - 150,
						WeaponFactory.create(EWeaponType.SHOTGUN));
				gameLogicHandler.getDropManager().add(drop);
				stepInitialized = true;
			}
			changeStep(Step.PICKUP_WEAPON);
			break;

		case PICKUP_WEAPON:
			if (player.getWeapon() != null && player.getWeapon().getName().toUpperCase().contains("SHOTGUN")) {

				changeStep(Step.SPAWN_HORDE);
			} else if (gameLogicHandler.getDropManager().getDrops().isEmpty()) {
				changeStep(Step.DROP_WARNING);
			}
			break;


		case DROP_WARNING:
			if (timer >= 3.0f)
				changeStep(Step.SPAWN_WEAPON);
			break;
		case SPAWN_HORDE:
			if (!stepInitialized) {
				gameLogicHandler.getEnemyManager().spawnEnemies(4);
				stepInitialized = true;
			}
			changeStep(Step.SURVIVE_HORDE);
			break;
		case SURVIVE_HORDE:
			if (gameLogicHandler.getEnemyManager().isEmpty())
				changeStep(Step.END);
			break;
		case END:
			break;
		}
	}

	private void changeStep(Step nextStep) {
		pasoActual = nextStep;
		timer = 0f;
		stepInitialized = false;
	}

	private void spawnPracticeEnemy(float x, float y) {
		Enemy dummy = new Enemy(x, y, EEnemyType.GENERIC, 1.0f, 0f, 50, 0);
		dummy.getVelocity().set(0, 0);
		gameLogicHandler.getEnemyManager().add(dummy);
	}

	@Override
	protected void draw(SpriteBatch batch, BitmapFont font) {
		ScreenUtils.clear(0.1f, 0.12f, 0.16f, 1f);
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
			subtexto = "Usa las FLECHAS para moverte (manten LEFT_SHIFT para acelerar mas lento y c para mayor presicion) ";
			break;
		case SHOOT:
			texto = "PASO 2: DISPARO BÁSICO";
			subtexto = "Presiona Z para disparar. Si la munición se agota, el Cuchillo se activa.";
			break;
		case SHOOT_DELAY:
			texto = "PASO 2: DISPARO BÁSICO";
			subtexto = "Cuchillo activo si la munición se agota. PREPARANDO OBJETIVOS...";
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

		layout.setText(font, texto);
		font.draw(batch, texto, cx - layout.width / 2, cy);

		font.getData().setScale(1.0f);
		layout.setText(font, subtexto);
		font.draw(batch, subtexto, cx - layout.width / 2, cy - 40);

		if (!warning.isEmpty()) {
			font.getData().setScale(1.0f);
			layout.setText(font, warning);
			font.draw(batch, warning, cx - layout.width / 2, cy - 80);
		}
	}

	@Override
	public void dispose() {
		musicaTutorial.stop();
	}
}