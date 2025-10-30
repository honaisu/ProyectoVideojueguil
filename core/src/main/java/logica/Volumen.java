package logica;

import pantallas.opciones.ConfigurationOption;

/**
 * Clase que sólo se encarga de manejar los valores del sonido.
 */
public class Volumen {    	
	// Paso de ajuste por tecla
	public static final float STEP = 0.05f;
	
    private float master;
    private float music;
    private float sfx;
    
    public Volumen() {
    	master 	= 1.0f;
        music 	= 1.0f;
        sfx		= 1.0f;
    }
    
    public Volumen(float master, float music, float sfx) {
    	this.master = master;
    	this.music 	= music;
    	this.sfx 	= sfx;
    }
    
    public void adjustSoundValue(ConfigurationOption opcion, float cambio) {
        switch (opcion) {
            case VOLUMEN_GENERAL:
                master = clamp01(master + cambio);
                break;
            case VOLUMEN_MUSICA:
                music = clamp01(music + cambio);
                break;
            case VOLUMEN_EFECTOS:
                sfx = clamp01(sfx + cambio);
                break;
            default: break;
        }
    }
    
    public static void applyChanges(Volumen destino, Volumen source) {
        destino.setMasterVolume(source.getMasterVolume());
        destino.setMusicVolume(source.getRawMusicVolume());
        destino.setSfxVolume(source.getRawSfxVolume());
    }
    
    // No sé bien que hace pero sin duda sirve para redondear xd
    private static float clamp01(float v) {
        return Math.max(0f, Math.min(1f, v));
    }
    
    public void setMasterVolume(float v) { master = clamp01(v); }
    public void setMusicVolume(float v) { music = clamp01(v); }
    public void setSfxVolume(float v) { sfx = clamp01(v); }

    public float getMasterVolume() { return master; }
    public float getMusicVolume() { return master * music; }
    public float getRawMusicVolume() { return music; }
    public float getSfxVolume() { return master * sfx; }
    public float getRawSfxVolume() { return sfx; }
}
