package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import armas.*;
import armas.proyectiles.Bullet;
import armas.proyectiles.Swing;
/*
<<<<<<< HEAD
import hitboxes.Ball2;
import logica.SpaceNavigation;
import personajes.Nave4;

/**
 * Clase que se encarga de renderizar toda la lógica del juego.
 /
public class PantallaJuego extends PantallaBase {
	private int score;
	private int ronda;
	
	private Nave4 nave;
	private ArrayList<Ball2> asteroidSprite = new ArrayList<>();
	private ArrayList<Ball2> asteroidCollision = new ArrayList<>();
	private ArrayList<Bullet> balas = new ArrayList<>();
	private int velXAsteroides = 1; 
	private int velYAsteroides = 1; 
	private int cantAsteroides = 0;
	
	
	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score) {
		super(game);
		this.ronda = ronda;
		this.score = score;
		
		
	    nave = new Nave4(
	    		Gdx.graphics.getWidth()/2,
	    		Gdx.graphics.getHeight()/2,
	    		game.assets.naveTexture,
	    		game.assets.hurtSound, 
	    		game.assets.balaTexture,
	    		game.assets.disparoSound
	    		);
	    
        nave.setVidas(vidas);
        iniciarNuevaRonda();
	}
	
	/**
	 * Método que se encarga de generar nuevas rondas (solo aumenta los asteroides) :D
	 /
	private void iniciarNuevaRonda() {
        asteroidSprite.clear();
        asteroidCollision.clear();
        
        cantAsteroides = 2 + (ronda - 1); 
	
        Random r = new Random();
        
	    for (int i = 0; i < cantAsteroides; i++) {
	        Ball2 bb = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4), 
	  	            game.assets.asteroideTexture);
	  	    asteroidSprite.add(bb);
	  	    asteroidCollision.add(bb);
	  	}
    }
    
	/**
	 * Método encargado de dibujar el UI.
	 /
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(game.batch, str, 10, 30);
		game.getFont().draw(game.batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(game.batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
	
	@Override
	public void render(float delta) {
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          
		  game.batch.begin();
	      if (!nave.estaHerido()) {
		      // colisiones entre balas y asteroides y su destruccion  
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
		            for (int j = 0; j < asteroidSprite.size(); j++) {    
		              if (b.checkCollision(asteroidSprite.get(j))) {          
		            	 game.assets.explosionSound.play(0.03f);
		            	 asteroidSprite.remove(j);
		            	 asteroidCollision.remove(j);
		            	 j--;
		            	 score +=10;
		              }   	  
		  	        }
		                
		            // b.draw(batch);
		            if (b.isDestroyed()) {
		                balas.remove(b);
		                i--; //para no saltarse 1 tras eliminar del arraylist
		            }
		      }
		      //actualizar movimiento de asteroides dentro del area
		      for (Ball2 ball : asteroidSprite) {
		          ball.update();
		      }

		      //colisiones entre asteroides y sus rebotes  
		      for (int i=0;i<asteroidSprite.size();i++) {
		    	Ball2 ball1 = asteroidSprite.get(i);   
		        for (int j=0;j<asteroidCollision.size();j++) {
		          Ball2 ball2 = asteroidCollision.get(j); 
		          if (i<j) {
		        	  ball1.checkCollision(ball2);
		     
		          }
		        }
		      } 
	      }
	      //dibujar balas
	      for (Bullet b : balas) {       
	    	  b.draw(game.batch);
	      }
	      nave.draw(game.batch, this, delta);
	      //dibujar asteroides y manejar colision con nave
	      for (int i = 0; i < asteroidSprite.size(); i++) {
	    	  Ball2 b=asteroidSprite.get(i);
	    	  b.draw(game.batch);
			  //perdió vida o game over
			  if (nave.checkCollision(b)) {
				  //asteroide se destruye con el choque             
				  asteroidSprite.remove(i);
				  asteroidCollision.remove(i);
				  i--;
			  }
	      }
	      
	      if (nave.estaDestruido()) {
	    	  if (score > game.getHighScore()) game.setHighScore(score);
	    	  
	    	  game.setScreen(new PantallaGameOver(game)); 
	          dispose();
	          return;
  		  }

	      // La UI se dibuja al final para evitar que quede "debajo"
		  dibujaEncabezado();
	      game.batch.end();
	      
	      //nivel completado
	      if (asteroidCollision.size()==0) {
	      	ronda++;
			iniciarNuevaRonda();
		  }
	}
    
	/**
	 * Método que... agrega balas?
	 * TODO Revisar
	 * @param bb
	 * @return
	 /
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
	
	@Override
	public void show() {
		// Verifica si es que la música se esta reproduciendo.
		// La reproduce si es que no está.
		if (!game.assets.gameMusic.isPlaying()) {
			game.assets.gameMusic.play();
		}
	}
=======*/
import logica.SpaceNavigation;
import managers.AsteroidManager;
import managers.BulletManager;
import managers.CollisionManager;
import managers.MeleeManager;
import personajes.Jugador;

public class PantallaJuego extends PantallaBase {
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Sound explosionSound;
    private Music gameMusic;

    private int score;
    private int ronda;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;

    private Jugador nave;

    // Managers
    private AsteroidManager asteroidManager;
    private BulletManager bulletManager;
    private CollisionManager collisionManager;
    private MeleeManager meleeManager;

    // Pausa
    private boolean paused = false;
    private float pauseToggleCooldown = 0f;
    private final float pauseToggleDelay = 0.2f;

    private final String[] pauseOptions = { "Continuar", "Menu principal" };
    private int pauseSelec = 0;
    private float keyCooldown = 0f;
    private final float repeatDelay = 0.15f;

    // Overlay de pausa
    private Texture texturaPausa;

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score) {
        super(game);
        this.ronda = ronda;
        this.score = score;

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);

        // Audio
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("musicaDoom.mp3"));
        gameMusic.setLooping(true);
        aplicarVolumenMusica();
        gameMusic.play();

        // Managers
        asteroidManager = new AsteroidManager(cantAsteroides, velXAsteroides, velYAsteroides);
        bulletManager = new BulletManager();
        meleeManager = new MeleeManager();
        collisionManager = new CollisionManager(explosionSound, 10); // 10 pts por asteroide

        // Jugador (lógica Nave4 + Arma)
        String path = game.getSelectedShipPath();
        if (path == null) path = "referencia.png";
        
        Texture naveTex = new Texture(Gdx.files.internal(path));
        
        nave = new Jugador(
            Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2,
            0f, // rotación inicial
            naveTex,
            new Melee(),
            game
        );
        game.setJugador(nave);
        nave.setVidas(vidas);
        
        //probar armas
        nave.setArma(new Metralleta());
        //nave.setArma(new Escopeta());

        // Crear textura overlay 1x1 para la pausa
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(1, 1, 1, 1);
        pm.fill();
        texturaPausa = new Texture(pm);
        pm.dispose();
    }

    private Sound cargarSoundSeguro(String primario, String alterno) {
        if (Gdx.files.internal(primario).exists()) return Gdx.audio.newSound(Gdx.files.internal(primario));
        if (Gdx.files.internal(alterno).exists()) return Gdx.audio.newSound(Gdx.files.internal(alterno));
        return null;
    }

    private void aplicarVolumenMusica() {
        float mv = game.getMasterVolume();
        float mus = game.getMusicVolume();
        gameMusic.setVolume(mv * mus);
    }

    private void dibujaEncabezado() {
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, "Vidas: " + nave.getVidas() + " Ronda: " + ronda, 10, 30);
        game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);

        // Munición arma (si aplica)
        if (nave.getArma() != null) {
            int mun = nave.getArma().getMunicion();
            int max = nave.getArma().getMunicionMax();
            game.getFont().draw(batch, "Municion: " + mun + " / " + max,
                    Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() - 20);
        }
    }

    @Override
    public void render(float delta) {
        // Toggle pausa con ESC
        pauseToggleCooldown -= delta;
        if (pauseToggleCooldown <= 0f && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pauseToggleCooldown = pauseToggleDelay;
            if (!paused) pause();
            else resume();
        }

        // Update si no está en pausa ni herido
        if (!paused && !nave.estaHerido()) {
            bulletManager.update();
            meleeManager.update(delta);
            asteroidManager.update();

            int gained = collisionManager.handleCollisions(nave, bulletManager, meleeManager, asteroidManager);
            if (gained > 0) score += gained;

            if (nave.estaDestruido()) {
                if (score > game.getHighScore()) game.setHighScore(score);
                Screen ss = new PantallaGameOver(game);
                ss.resize(1200, 800);
                game.setScreen(ss);
                dispose();
                return;
            }

            if (asteroidManager.isEmpty()) {
                
            }
        }

        // Render
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        dibujaEncabezado();

        bulletManager.render(batch);
        meleeManager.render(batch);
        nave.draw(batch, this, paused, delta);
        asteroidManager.render(batch);

        if (paused) dibujarMenuPausa(delta);

        batch.end();
    }

    private void dibujarMenuPausa(float delta) {
        // Overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(0f, 0f, 0f, 0.45f);
        batch.draw(texturaPausa, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(1f, 1f, 1f, 1f);

        // Navegación
        keyCooldown -= delta;
        if (keyCooldown <= 0f) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP))   { pauseSelec = (pauseSelec - 1 + pauseOptions.length) % pauseOptions.length; keyCooldown = repeatDelay; }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { pauseSelec = (pauseSelec + 1) % pauseOptions.length; keyCooldown = repeatDelay; }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (pauseSelec == 0) {
                resume();
            } else {
                Screen ss = new PantallaMenu(game);
                ss.resize(1200, 800);
                game.setScreen(ss);
                dispose();
                return;
            }
        }

        // Texto
        float panelW = 520f, panelH = 260f;
        float px = (Gdx.graphics.getWidth() - panelW) / 2f;
        float py = (Gdx.graphics.getHeight() - panelH) / 2f;

        game.getFont().getData().setScale(2.0f);
        game.getFont().draw(batch, "PAUSA", px + 190, py + 200);

        game.getFont().getData().setScale(1.6f);
        for (int i = 0; i < pauseOptions.length; i++) {
            boolean sel = (i == pauseSelec);
            String text = (sel ? "> " : "  ") + pauseOptions[i] + (sel ? " <" : "");
            game.getFont().draw(batch, text, px + 120, py + 130 - i * 60);
        }
    }

    // Hooks para que Armas agreguen entidades a sus managers
    public boolean agregarBala(Bullet b) { bulletManager.add(b); return true; }
    public void agregarSwing(Swing s) { meleeManager.add(s); }

    @Override public void show() { if (gameMusic != null) gameMusic.play(); }

    @Override
    public void pause() {
        paused = true;
        float mv = game.getMasterVolume();
        float mus = game.getMusicVolume();
        gameMusic.setVolume(mv * mus * 0.33f);
    }

    @Override
    public void resume() {
        paused = false;
        aplicarVolumenMusica();
        keyCooldown = repeatDelay;
    }

    @Override
    public void dispose() {
        if (explosionSound != null) explosionSound.dispose();
        if (gameMusic != null) gameMusic.dispose();
        if (texturaPausa != null) texturaPausa.dispose();
        if (asteroidManager != null) asteroidManager.clear();
        if (bulletManager != null) bulletManager.clear();
        if (meleeManager != null) meleeManager.clear();
    }

    // Accesores para managers (si los necesitan las Armas o sistemas externos)
    public AsteroidManager getAsteroidManager() { return asteroidManager; }
    public BulletManager getBulletManager() { return bulletManager; }
    public CollisionManager getCollisionManager() { return collisionManager; }
    public MeleeManager getMeleeManager() { return meleeManager; }
}
