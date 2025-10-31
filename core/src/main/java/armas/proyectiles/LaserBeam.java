package armas.proyectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import hitboxes.RayHitbox;
import personajes.Player;

public class LaserBeam extends Projectile {
    private Player nave;
    private boolean destroyed = false;

    // Control de encendido por tecla mantenida (Z)
    // si llega a 0, se apaga
    private float tiempoRestanteEncendido = 0f;     
    // margen entre frames para mantener encendido
    private final float tiempoRefrescoEncendido = 0.12f; 
    
    /*
    public LaserBeam(Player nave, float ancho, Texture textura, int num) {
    	super(new RayHitbox(muzzle[0], muzzle[1], 1f, ancho, ang, textura, num));
        this.nave = nave;

        float ang = nave.getRotation() + 90f;
        float[] muzzle = calcularMuzzle(nave, ang);
    }*/
    
    public LaserBeam(float x, float y, float width, float angle) {
    	super(new RayHitbox(new Rectangle(x, y, width, 1f), angle, true));
    }
    
    public LaserBeam(Rectangle r, float angle) {
    	super(new RayHitbox(r, angle, true));
    }
    
    //TODO revisar para reutilizar este metodo para otras armas
    private static float[] calcularMuzzle(Player nave, float ang) {
        float cx = nave.getSpr().getX() + nave.getSpr().getWidth() / 2f;
        float cy = nave.getSpr().getY() + nave.getSpr().getHeight() / 2f;
        float length = nave.getSpr().getHeight() / 2f;
        float rad = (float) Math.toRadians(ang);
        float mx = cx + (float) Math.cos(rad) * length;
        float my = cy + (float) Math.sin(rad) * length;
        return new float[]{mx, my};
    }

    // Llamado por Laser.disparar cada frame mientras la tecla (Z) siga presionada
    public void refrescarEncendido() {
        tiempoRestanteEncendido = tiempoRefrescoEncendido;
    }
        
    //Esto sirve para el Cañon laser
    public void configurarPulso(float duracionSegundos) {
        // Usamos el temporizador interno como TTL del pulso
        this.tiempoRestanteEncendido = Math.max(0f, duracionSegundos);
        // No se llama a refrescarEncendido desde CanonLaser, por lo que se apagará solo al vencer
    }
    
    
    public void update(float delta) {
        if (destroyed) return;

        // Seguir a la nave y estirarse hasta borde (RayHitbox ya lo hace en setTransform)
        float ang = nave.getRotation() + 90f;
        float[] muzzle = calcularMuzzle(nave, ang);
        // TODO 
        //getHitbox().setTransform(muzzle[0], muzzle[1], ang);

        // Apagar si no se refrescó (la tecla se soltó)
        tiempoRestanteEncendido -= delta;
        if (tiempoRestanteEncendido <= 0f) {
            destroy();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!destroyed) getHitbox().draw(batch);
    }

    public void destroy() {
        destroyed = true;
        //hitbox.setActivo(false);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

	@Override
	public void update(float delta, Rectangle r, float rotation) {
		if (destroyed) return;

        // Seguir a la nave y estirarse hasta borde (RayHitbox ya lo hace en setTransform)
        float ang = rotation;
        //float[] muzzle = calcularMuzzle(nave, ang);
        //getHitbox().setTransform(muzzle[0], muzzle[1], ang);

        // Apagar si no se refrescó (la tecla se soltó)
        tiempoRestanteEncendido -= delta;
        if (tiempoRestanteEncendido <= 0f) {
            destroy();
        }
	}
}
