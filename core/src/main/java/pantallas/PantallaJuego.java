package pantallas;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import armas.proyectiles.Bullet;
import hitboxes.Ball2;
import logica.SpaceNavigation;
import personajes.Nave4;

/**
 * Clase que se encarga de renderizar toda la lógica del juego.
 */
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
	 */
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
	 */
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
	 */
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
}
