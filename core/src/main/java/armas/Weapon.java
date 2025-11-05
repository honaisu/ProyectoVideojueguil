package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import managers.AssetManager;
import managers.ProjectileManager;

//Clase Abstracta Arma generica
public abstract class Weapon {
	private String nombre;
	//tiempo que dura un ataque despues de otro
	private float cadencia;
	//contador para que se cumpla la cadencia
    private float tiempoUltDisp;	
    //municion actual que tiene un arma
    private int municion;
    //municion máxima que puede poseer un arma
    private int municionMax;
    private Sound soundBala;
    
    public Weapon(String nombre, float cadencia, int municionMax, Sound soundBala) {
    	this.nombre = nombre;
    	this.cadencia = cadencia;
        this.municionMax = municionMax;
        this.municion = municionMax;
        this.soundBala = soundBala;
        this.tiempoUltDisp = cadencia; //para que dispare instantaneamente el primer disparo
    }
    
    public Weapon(float cadencia, int municionMax) {
    	this.cadencia = cadencia;
    	this.tiempoUltDisp = cadencia;
    	this.municion = municionMax;
    	this.municionMax = municionMax;
    	
    	this.nombre = "Weapon";
    	this.soundBala = AssetManager.getInstancia().getDisparoSound();
    }
    
    // metodo abstracto para disparar un arma
    //public abstract void disparar(Player nave, GameWorld juego, float delta);
    public void atacar(float delta, Rectangle r, float rotation, ProjectileManager manager) {
    	actualizar(delta);
    	if (!puedeDisparar()) return;
        if (getMunicion() <= 0) return;
        
        restarMunicion(delta);
        if (puedeDisparar()) reiniciarCooldown();
        crearProyectil(r, rotation, manager);
    }
    
    // metodo abstracto para crear el proyectil para cada arma
    //public abstract void crearProyectil(Player nave, GameWorld juego);
    public abstract void crearProyectil(Rectangle r, float rotation, ProjectileManager manager);
    
    public void restarMunicion(float delta) {
    	tiempoUltDisp += delta;
        if (tiempoUltDisp >= cadencia) {
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
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

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
