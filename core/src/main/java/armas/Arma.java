package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import pantallas.PantallaJuego;
import personajes.Jugador;

//Clase Abstracta Arma generica
public abstract class Arma {
	
	
	protected float cadencia;					// tiempo que dura un ataque despues de otro
    protected float tiempoDesdeUltimoDisparo;	// contador para que se cumpla la cadencia
    
    protected int municion;						// municion actual que tiene un arma
    protected int municionMax;					// municion máxima que puede poseer un arma
    
    protected Texture txBala;					// textura de la bala
    protected Sound soundBala;					// sonido de la bala
    
    public Arma(float cadencia, int municionMax, Texture txBala, Sound soundBala) {
    	this.cadencia = cadencia;
        this.municionMax = municionMax;
        this.municion = municionMax;
        
        this.txBala = txBala;
        this.soundBala = soundBala;
        
        this.tiempoDesdeUltimoDisparo = 0; // para que 
    }

    public abstract void disparar(Jugador nave, PantallaJuego juego, float delta);

    public void actualizar(float delta) {
    	if (tiempoDesdeUltimoDisparo < cadencia) {
            tiempoDesdeUltimoDisparo += delta;
        }
    }

    protected boolean puedeDisparar() {
        return tiempoDesdeUltimoDisparo >= cadencia;
    }

    protected void reiniciarCooldown() {
        tiempoDesdeUltimoDisparo = 0;
    }
    
    public int getMunicion() {
        return municion;
    }

    public int getMunicionMax() {
        return municionMax;
    }
	
}
