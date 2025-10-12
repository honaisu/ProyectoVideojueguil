package hitboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class RayHitbox {

    private float x;
    private float y;
    private float largo;
    private float ancho;
    private float angulo;

    private boolean visible = true;
    private boolean activo = true;

    private Sprite spr;

    public RayHitbox(float x, float y, float largo, float ancho, float angulo, Texture tx, int num) {
        this.x = x;
        this.y = y;
        this.largo = largo;
        this.ancho = ancho;
        this.angulo = angulo;

        spr = new Sprite(tx);
        spr.setSize(largo, ancho);
        spr.setOrigin(0, ancho /2f );
        spr.setRotation(angulo);
        spr.setPosition(x, y - ancho /2f );
        
        //idea de que si es 1, es el rayo delgado, si es 2, es el rayo grueso.
        if(num == 1) {spr.scale(3f);}//culpa benjoid
        if(num == 2) {spr.scale(10);}//
        setToScreenEnd();
    }

    public void draw(SpriteBatch batch) {
        if (visible) {
            spr.draw(batch);
        }
    }

    // Recalcula el largo hasta el borde de la pantalla en la dirección actual
    public void setToScreenEnd() {
        float angRad = (float) Math.toRadians(angulo);
        float dx = (float) Math.cos(angRad);
        float dy = (float) Math.sin(angRad);

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();

        // Evitar degeneraciones numéricas
        float eps = 1e-6f;
        if (Math.abs(dx) < eps) dx = Math.copySign(eps, dx);
        if (Math.abs(dy) < eps) dy = Math.copySign(eps, dy);

        float tMin = Float.POSITIVE_INFINITY;

        // Intersección con x = 0
        if (dx < 0f) {
            float t = (0f - x) / dx;
            if (t > 0f && t < tMin) tMin = t;
        }

        // Intersección con x = W
        if (dx > 0f) {
            float t = (W - x) / dx;
            if (t > 0f && t < tMin) tMin = t;
        }

        // Intersección con y = 0
        if (dy < 0f) {
            float t = (0f - y) / dy;
            if (t > 0f && t < tMin) tMin = t;
        }

        // Intersección con y = H
        if (dy > 0f) {
            float t = (H - y) / dy;
            if (t > 0f && t < tMin) tMin = t;
        }

        if (tMin == Float.POSITIVE_INFINITY) {
            // Fallback: dirección degenerada, extensión máxima
            largo = Math.max(W, H);
        } else {
            largo = tMin;
        }

        // Actualizar sprite al nuevo largo y pose
        spr.setSize(largo, ancho);
        spr.setOrigin(0, ancho / 2f);
        spr.setRotation(angulo);
        spr.setPosition(x, y - ancho / 2f);
    }

    // Sin tiempo de vida: solo sincroniza posición/ángulo y recalcula hasta el borde
    public void setTransform(float x, float y, float angulo) {
        this.x = x;
        this.y = y;
        this.angulo = angulo;

        // Posición básica
        spr.setRotation(angulo);
        spr.setPosition(x, y - ancho / 2f);

        // Recalcular largo y ajustar sprite
        setToScreenEnd();
    }

    public boolean colisionaCon(BallHitbox b2) {
        if (!activo) return false;

        // Centro y radio aproximados del asteroide a partir de su AABB
        Rectangle a = b2.getArea();
        float cx = a.x + a.width / 2f;
        float cy = a.y + a.height / 2f;
        float r = Math.min(a.width, a.height) / 2f;

        // Extremo final del rayo (usando largo ya recalculado)
        float angRad = (float) Math.toRadians(angulo);
        float x2 = x + (float) Math.cos(angRad) * largo;
        float y2 = y + (float) Math.sin(angRad) * largo;

        // Vector del segmento AB
        float vx = x2 - x;
        float vy = y2 - y;
        float segLen2 = vx * vx + vy * vy;
        if (segLen2 == 0f) return false; // rayo degenerado

        // Proyección de C sobre la recta AB → parámetro t
        float wx = cx - x;
        float wy = cy - y;
        float t = (wx * vx + wy * vy) / segLen2;

        // Clamp a segmento
        if (t < 0f) t = 0f;
        else if (t > 1f) t = 1f;

        // Punto más cercano P en el segmento
        float px = x + t * vx;
        float py = y + t * vy;

        // Distancia de C a P
        float dx = cx - px;
        float dy = cy - py;
        float dist2 = dx * dx + dy * dy;

        // Radio efectivo: radio del asteroide + semigrosor del rayo
        float radioEfectivo = r + (ancho * 0.5f);
        return dist2 <= radioEfectivo * radioEfectivo;
    }
    

    // Control básico
    public void setVisible(boolean visible) { this.visible = visible; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // Accesores
    public float getX() { return x; }
    public float getY() { return y; }
    public float getAnguloDeg() { return angulo; }
    public float getLargo() { return largo; }
    public float getAncho() { return ancho; }
    public Sprite getSprite() { return spr; }
}
