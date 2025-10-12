package armas.proyectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hitboxes.RayHitbox;
import hitboxes.BallHitbox;
import personajes.Jugador;
import armas.Laser;

public class RayoLaser extends Swing {

    private final Jugador nave;
    private final RayHitbox rayo;
    private boolean destroyed = false;

    // Control de encendido por tecla mantenida (Z)
    private float tiempoRestanteEncendido = 0f;     // si llega a 0, se apaga
    private final float tiempoRefrescoEncendido = 0.12f; // margen entre frames para mantener encendido

    // Consumo de munición por tiempo
    private float acumuladorMunicion = 0f; // acumula fracciones hasta consumir unidades enteras

    public RayoLaser(Jugador nave, float ancho, Texture textura, int num) {
        super(0f, 0f, 0f, nave); // no usamos ArcHitbox del Swing base
        this.nave = nave;

        float ang = nave.getRotacion() + 90f;
        float[] muzzle = calcularMuzzle(nave, ang);

        this.rayo = new RayHitbox(muzzle[0], muzzle[1], 1f, ancho, ang, textura, num);
    }

    private static float[] calcularMuzzle(Jugador nave, float ang) {
        float cx = nave.spr.getX() + nave.spr.getWidth() / 2f;
        float cy = nave.spr.getY() + nave.spr.getHeight() / 2f;
        float length = nave.spr.getHeight() / 2f;
        float rad = (float) Math.toRadians(ang);
        float mx = cx + (float) Math.cos(rad) * length;
        float my = cy + (float) Math.sin(rad) * length;
        return new float[]{mx, my};
    }

    // Llamado por Laser.disparar cada frame mientras la tecla (Z) siga presionada
    public void refrescarEncendido() {
        tiempoRestanteEncendido = tiempoRefrescoEncendido;
    }

    // Drenar munición con base en consumoPorSegundo y delta
    public void drenarMunicionPorTiempo(Laser arma, float consumoPorSegundo, float delta) {
        if (destroyed) return;
        acumuladorMunicion += consumoPorSegundo * delta;
        while (acumuladorMunicion >= 1f && arma.getMunicion() > 0) {
            arma.consumirUnidadMunicion(1);
            acumuladorMunicion -= 1f;
        }
        if (arma.getMunicion() <= 0) {
            destroy();
        }
    }
    
    
    //Esto sirve para el Cañon laser
    public void configurarPulso(float duracionSegundos) {
        // Usamos el temporizador interno como TTL del pulso
        this.tiempoRestanteEncendido = Math.max(0f, duracionSegundos);
        // No se llama a refrescarEncendido desde CanonLaser, por lo que se apagará solo al vencer
    }
    
    @Override
    public void update(float delta) {
        if (destroyed) return;

        // Seguir a la nave y estirarse hasta borde (RayHitbox ya lo hace en setTransform)
        float ang = nave.getRotacion() + 90f;
        float[] muzzle = calcularMuzzle(nave, ang);
        rayo.setTransform(muzzle[0], muzzle[1], ang);

        // Apagar si no se refrescó (la tecla se soltó)
        tiempoRestanteEncendido -= delta;
        if (tiempoRestanteEncendido <= 0f) {
            destroy();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!destroyed) rayo.draw(batch);
    }

    @Override
    public boolean checkCollision(BallHitbox b2) {
        return !destroyed && rayo.colisionaCon(b2);
    }

    public void destroy() {
        destroyed = true;
        rayo.setActivo(false);
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
