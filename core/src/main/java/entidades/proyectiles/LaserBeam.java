package entidades.proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import entidades.Player;
import entidades.RayHitbox;
import managers.assets.AssetManager;

public class LaserBeam extends Projectile {
    private Player nave;
    private boolean destroyed = false;
    

    private float largo;
    private float ancho;
    private float angle;

    // Control de encendido por tecla mantenida (Z)
    // si llega a 0, se apaga
    private float tiempoRestanteEncendido = 0f;
    private final float tiempoRefrescoEncendido = 0.12f; 
    
    /*
    public LaserBeam(Player nave, float ancho, Texture textura, int num) {
    	super(new RayHitbox(muzzle[0], muzzle[1], 1f, ancho, ang, textura, num));
        this.nave = nave;

        float ang = nave.getRotation() + 90f;
        float[] muzzle = calcularMuzzle(nave, ang);
    }*/
    
    public LaserBeam(float x, float y, float width, float angle, boolean isThin, Sprite spr, Player p) {
    	super(x, y, spr, p);
    	
    	// 2. Configura los campos (lógica de RayHitbox)
        this.ancho = p.getSpr().getBoundingRectangle().getWidth();
        this.largo = p.getSpr().getBoundingRectangle().getHeight(); // r.getHeight() es probablemente 1f, pero está bien
        this.angle = angle;
        
        if (isThin) getSpr().scale(3f);
        else getSpr().scale(10f);
        
        // 3. Llama al método de configuración (copiado de RayHitbox)
        this.setToScreenEnd();
    }
    
    private void setToScreenEnd() {
        float radians = (float) Math.toRadians(angle);
        float dx = (float) Math.cos(radians);
        float dy = (float) Math.sin(radians);

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();
        float tMin = Float.POSITIVE_INFINITY;
        
        if (dx > 0f) tMin = Math.min(tMin, (W - getX()) / dx);
        if (dx < 0f) tMin = Math.min(tMin, (0 - getX()) / dx);
        if (dy > 0f) tMin = Math.min(tMin, (H - getY()) / dy);
        if (dy < 0f) tMin = Math.min(tMin, (0 - getY()) / dy);

        largo = (tMin == Float.POSITIVE_INFINITY) ? Math.max(W, H) : tMin;
        
        getSpr().setSize(largo, ancho);
    }
    
    public void setTransform(float x, float y, float angulo) {
        setX(x);
        setY(y);
        this.angle = angulo;

        getSpr().setRotation(angulo);
        getSpr().setPosition(x, y - ancho / 2f); // (Asumo que quieres centrarlo)

        setToScreenEnd();
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
    
    @Override
    public void update(float delta, Player p) {
        if (isDestroyed()) return;

        // Lógica de tiempo de vida
        tiempoRestanteEncendido -= delta;
        if (tiempoRestanteEncendido <= 0f) {
            destroy();
        }
        
        // Igual que Swing, la clase Weapon que usa el LaserBeam
        // debe llamar a laser.setTransform(x, y, angulo) CADA FRAME
        // antes de llamar a laser.update(delta).
    }
    
    @Override
    public void update(float delta) {
    	// TODO Auto-generated method stub
    	
    }
    /*
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
    }*/

    /*@Override
    public void draw(SpriteBatch batch) {
        if (!destroyed) draw(batch);
    }*/

    public void destroy() {
        destroyed = true;
        //hitbox.setActivo(false);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    
    /*
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
	}*/
}
