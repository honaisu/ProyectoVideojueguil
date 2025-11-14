package armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import entidades.Player;
import enumeradores.recursos.EGameSound;
import managers.ProjectileManager;
import managers.assets.AssetManager;

//Clase Abstracta Arma generica
public abstract class Weapon {
	private String nombre;
	
	private int damage;
	//tiempo que dura un ataque despues de otro
	private float cadencia;
	//contador para que se cumpla la cadencia
    private float tiempoUltDisp;	
    //municion actual que tiene un arma
    private int municion;
    //municion máxima que puede poseer un arma
    private int municionMax;
    private Sound soundBala;
    
    public Weapon(String nombre, int damage, float cadencia, int municionMax, Sound soundBala) {
    	this.nombre = nombre;
    	this.damage = damage;
    	this.cadencia = cadencia;
        this.municionMax = municionMax;
        this.municion = municionMax;
        this.soundBala = soundBala;
        this.tiempoUltDisp = cadencia; //para que dispare instantaneamente el primer disparo
    }
    
    public Weapon(String nombre, int damage, float cadencia, int municionMax) {
    	// Para que tenga un sonido generico
    	this(nombre, damage, cadencia, municionMax, AssetManager.getInstancia().getSound(EGameSound.SHOOT));
    }
    
    // metodo abstracto para crear el proyectil para cada arma
    public abstract void crearProyectil(Player p, ProjectileManager manager);
    // metodo abstracto para crear la textura del drop
    public abstract Texture getDropTexture();
    // metodo abstracto para crear es sonido del drop
    public abstract Sound getPickupSound();
    
    // metodo abstracto para disparar un arma
    //public abstract void disparar(Player nave, GameWorld juego, float delta);
    public void atacar(float delta, Player p, ProjectileManager manager) {
    	actualizar(delta);
    	if (!puedeDisparar()) return;
        if (getMunicion() <= 0) return;
        
        restarMunicion(delta);
        if (puedeDisparar()) reiniciarCooldown();
        crearProyectil(p, manager);
    }
    
    
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
    public int getDamage() {return damage; }
    
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
