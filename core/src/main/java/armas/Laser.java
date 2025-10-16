package armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import armas.proyectiles.RayoLaser;
import logica.AssetsLoader;
import pantallas.PantallaJuego;
import personajes.Jugador;

public class Laser extends Arma {

    // Recursos
    private final Texture texturaLaser;
    private final Sound soundLaser;

    // Parámetros del “pulso” (un disparo = un rayo breve)
    private final float anchoLaser = 10f;     // grosor del rayo
    private final float ttlPulso   = 0.12f;   // duración de cada pulso en segundos

    // misma idea que el metralleta (literal es igual en todos creo xd)
    public Laser() {
        super(0.08f, 30, AssetsLoader.getInstancia().getDisparoLaserSound());
        this.texturaLaser = new Texture(Gdx.files.internal("cosaFea.png"));
        this.soundLaser   = this.soundBala;
    }

    // Disparo “pulso” como dijo benjoid
    @Override
    public void disparar(Jugador nave, PantallaJuego pantalla, float delta) {
        // avanzar cooldown
        this.actualizar(delta);

        // mismas verificaciones que otras armas
        if (!puedeDisparar()) return;
        if (this.getMunicion() <= 0) return;

        // consumir 1 unidad
        this.municion -= 1;

        // crear el rayo y registrarlo como un “disparo” breve
        RayoLaser pulso = crearLaser(nave);
        pulso.configurarPulso(ttlPulso); // se apaga solo al vencer el TTL
        pantalla.agregarSwing(pulso);

        // sonido y reinicio de cadencia
        if (soundLaser != null) soundLaser.play(0.35f);
        reiniciarCooldown();
    }

    //Construcción del proyectil, similar a crearBala() de metralla (ashprita si benjoid, ojala sirva u_u)
    protected RayoLaser crearLaser(Jugador nave) {
        // El último parámetro (1) elige el rayo “delgado” según RayHitbox
        return new RayoLaser(nave, anchoLaser, texturaLaser, 1);
    }
}
