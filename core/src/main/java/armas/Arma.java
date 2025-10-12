package armas;

import com.badlogic.gdx.audio.Sound;

import pantallas.PantallaJuego;
import personajes.Jugador;

//Clase Abstracta Arma generica
public abstract class Arma {
	protected float cadencia;					//tiempo que dura un ataque despues de otro
    protected float tiempoDesdeUltimoDisparo;	//contador para que se cumpla la cadencia
    protected int municion;						//municion actual que tiene un arma
    protected int municionMax;					//municion máxima que puede poseer un arma
    protected Sound soundBala;					//sonido de la bala
    
    public Arma(float cadencia, int municionMax, Sound soundBala) {
    	this.cadencia = cadencia;
        this.municionMax = municionMax;
        this.municion = municionMax;
        this.soundBala = soundBala;
        
        this.tiempoDesdeUltimoDisparo = cadencia; //para que dispare instantaneamente el primer disparo
    }
    
    //metodo abstracto para disparar un arma
    public abstract void disparar(Jugador nave, PantallaJuego juego, float delta);
    
    //actualiza el timer entre disparo y disparo
    public void actualizar(float delta) {
    	if (tiempoDesdeUltimoDisparo < cadencia) {
            tiempoDesdeUltimoDisparo += delta;
        }
    }
    
    //verifica si puede disparar cuando se haya cumplido el tiempo entre disparo y disparo
    protected boolean puedeDisparar() {
        return tiempoDesdeUltimoDisparo >= cadencia;
    }
    
    //reinicia el tiempo entre el disparo y el siguente
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
