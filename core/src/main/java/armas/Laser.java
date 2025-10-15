package armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import armas.proyectiles.RayoLaser;
import pantallas.PantallaJuego;
import personajes.Jugador;


public class Laser extends Arma {

    private final Texture texturaLaser;
    private final Sound soundLaser;


    private RayoLaser rayoActivo;

    // Config
    private final float anchoLaser = 10f;         // grosor del rayo
    private final float consumoPorSegundo = 10f;  // munición/seg mientras está encendido

    public Laser() {
        // cadencia solo como gate de reactivación
        super(0.01f, 30, Gdx.audio.newSound(Gdx.files.internal("laserSong.mp3")));
        this.texturaLaser = new Texture(Gdx.files.internal("cosaFea.png"));
        this.soundLaser = this.soundBala;
    }

    @Override
    public void disparar(Jugador nave, PantallaJuego pantalla, float delta) {
        // Avanza cooldown base
        actualizar(delta);

        // Si ya hay rayo encendido, mantenerlo vivo y drenar munición por tiempo
        if (rayoActivo != null && !rayoActivo.isDestroyed()) {
            // Mantener vivo (si no se refresca por unos ms, se apagará solo)
            rayoActivo.refrescarEncendido();

            // Drenaje de munición por tiempo
            rayoActivo.drenarMunicionPorTiempo(this, consumoPorSegundo, delta);

            // Si se agotó, cortar
            if (getMunicion() <= 0) {
                rayoActivo.destroy();
                rayoActivo = null;
            }
            return;
        }

        // Si no hay rayo, crear uno si hay munición y cadencia lista
        if (!puedeDisparar() || getMunicion() <= 0) return;

        rayoActivo = new RayoLaser(nave, anchoLaser, texturaLaser, 1);
        pantalla.agregarSwing(rayoActivo); // se gestiona vía MeleeManager
        rayoActivo.refrescarEncendido();        // arranca vivo

        if (soundLaser != null) soundLaser.play(0.35f);

        reiniciarCooldown();
    }

    // Opcional si hay hook de key-up; si no, el TTL lo apagará al no recibir ping
    public void soltarDisparo() {
        if (rayoActivo != null) {
            rayoActivo.destroy();
            rayoActivo = null;
        }
    }

    // Exponer setter para consumo desde el rayo sin tocar Arma
    public void consumirUnidadMunicion(int unidades) {
        municion = Math.max(0, municion - unidades);
    }

}
