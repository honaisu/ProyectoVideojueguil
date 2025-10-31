package hitboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import managers.AssetManager;

public class RayHitbox extends Hitbox {
    private float largo;
    private float ancho;
    private float angle;

    private boolean activo = true;

    	/*
    public RayHitbox(float x, float y, float largo, float ancho, float angulo, Texture tx, int num) {
    	super(x,y, new Sprite(AssetManager.getInstancia().getLaserContTexture()));
    	
        this.largo = largo;
        this.ancho = ancho;
        this.angulo = angulo;

        getSpr().setSize(largo, ancho);        
        getSpr().setOrigin(0, ancho /2f );
        getSpr().setRotation(angulo);
        getSpr().setPosition(x, y - ancho /2f );
        
        //idea de que si es 1, es el rayo delgado, si es 2, es el rayo grueso.
        if (num == 1) getSpr().scale(3f); //culpa benjoid
        if (num == 2) getSpr().scale(10); 
        setToScreenEnd();
    }*/
    
    public RayHitbox(Rectangle r, float angle, boolean isThin) {
    	super(r.getX(), r.getY(), new Sprite(AssetManager.getInstancia().getLaserContTexture()));
    	this.ancho = r.getWidth();
    	this.largo = r.getHeight();
    	this.angle = angle;
    	
    	if (isThin) getSpr().scale(3f);
    	else getSpr().scale(10f);
    	
    	this.setToScreenEnd();
    }

    // Recalcula el largo hasta el borde de la pantalla en la dirección actual
    private void setToScreenEnd() {
        float radians = (float) Math.toRadians(angle);
        float dx = (float) Math.cos(radians);
        float dy = (float) Math.sin(radians);

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();

        float tMin = Float.POSITIVE_INFINITY;
        
        // Lógica para encontrar la intersección con los bordes de la pantalla
        if (dx > 0f) tMin = Math.min(tMin, (W - getX()) / dx);
        if (dx < 0f) tMin = Math.min(tMin, (0 - getX()) / dx);
        if (dy > 0f) tMin = Math.min(tMin, (H - getY()) / dy);
        if (dy < 0f) tMin = Math.min(tMin, (0 - getY()) / dy);

        largo = (tMin == Float.POSITIVE_INFINITY) ? Math.max(W, H) : tMin;
        
        // Actualizar sprite
        getSpr().setSize(largo, ancho);
    }

    // Sin tiempo de vida: solo sincroniza posición/ángulo y recalcula hasta el borde
    public void setTransform(float x, float y, float angulo) {
        setX(x);
        setY(y);
        this.angle = angulo;

        // Posición básica
        getSpr().setRotation(angulo);
        getSpr().setPosition(x, y - ancho / 2f);

        // Recalcular largo y ajustar sprite
        setToScreenEnd();
    }
    
    // Control básico
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // Accesores
    public float getAnguloDeg() { return angle; }
    public float getLargo() { return largo; }
    public float getAncho() { return ancho; }
}
