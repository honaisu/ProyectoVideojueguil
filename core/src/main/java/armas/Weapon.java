package armas;

import com.badlogic.gdx.audio.Sound;

import pantallas.PantallaJuego;
import personajes.Jugador;

//Clase Abstracta Arma generica
public abstract class Weapon {
	private String nombre;
	
	private float cadencia;					//tiempo que dura un ataque despues de otro
    private float tiempoUltDisp;	//contador para que se cumpla la cadencia
    private int municion;						//municion actual que tiene un arma
    private int municionMax;					//municion máxima que puede poseer un arma
    private Sound soundBala;					//sonido de la bala
    
    public Weapon(String nombre, float cadencia, int municionMax, Sound soundBala) {
    	this.nombre = nombre;
    	this.cadencia = cadencia;
        this.municionMax = municionMax;
        this.municion = municionMax;
        this.soundBala = soundBala;
        
        this.tiempoUltDisp = cadencia; //para que dispare instantaneamente el primer disparo
    }
    
    // metodo abstracto para disparar un arma
    public abstract void disparar(Jugador nave, PantallaJuego juego, float delta);
    
    // metodo abstracto para crear el proyectil para cada arma
    public abstract void crearProyectil(Jugador nave, PantallaJuego juego);
    
    
    public void restarMunicion(Jugador nave, PantallaJuego juego, float delta) {
    	tiempoUltDisp += delta;
        if (tiempoUltDisp >= cadencia) {
            crearProyectil(nave, juego);
            municion -= 1;
            tiempoUltDisp = 0;
        }
    }
    
    
    //actualiza el timer entre disparo y disparo
    public void actualizar(float delta) {
    	if (tiempoUltDisp < cadencia) {
            tiempoUltDisp += delta;
        }
    }
    
    //verifica si puede disparar cuando se haya cumplido el tiempo entre disparo y disparo
    protected boolean puedeDisparar() {
        return tiempoUltDisp >= cadencia;
    }
    
    //reinicia el tiempo entre el disparo y el siguente
    protected void reiniciarCooldown() {
        tiempoUltDisp = 0;
    }
    
    //Getters y Setters
    public int getMunicion() { return municion; }

    public int getMunicionMax() { return municionMax; }

	public float getCadencia() { return cadencia; }

	public float getTiempoUltDisp() { return tiempoUltDisp; }

	public Sound getSoundBala() { return soundBala;	}

	public String getNombre() {	return nombre; }

	public void setCadencia(float cadencia) { this.cadencia = cadencia;	}

	public void setTiempoUltDisp(float tiempoUltDisp) {
		this.tiempoUltDisp = tiempoUltDisp;
	}

	public void setMunicion(int municion) {
		this.municion = municion;
	}

	public void setMunicionMax(int municionMax) {
		this.municionMax = municionMax;
	}

	public void setSoundBala(Sound soundBala) {
		this.soundBala = soundBala;
	}

	
}
