package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import pantallas.PantallaJuego;
import personajes.Jugador;

public abstract class Arma {
	
    protected float tiempoDesdeUltimoDisparo;
    protected float cadencia;
    
    protected int municion;
    protected int municionMax;
    
    protected Texture txBala;
    protected Sound soundBala;
    
    public Arma(float cadencia, int municionMax, Sound soundBala) {
    	this.cadencia = cadencia;
        this.municionMax = municionMax;
        this.municion = municionMax;
        
        this.soundBala = soundBala;
        
        this.tiempoDesdeUltimoDisparo = cadencia;
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
