package armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import armas.proyectiles.LaserBeam;
import pantallas.PantallaJuego;
import personajes.Jugador;

public class CanonLaser extends Weapon {

    private final Texture texturaLaser;
    private final Sound soundLaser;

    // Configuración del pulso
    private final float anchoLaser = 20f;     // grosor visual/efectivo
    private final float duracionPulso = 0.25f; // duración del rayo por disparo (segundos)
    private final int estiloRayo = 2;         // usa el “num” para grosor/estilo en RayHitbox

    public CanonLaser() {
        // cadencia controla cada cuánto puede volver a disparar (cooldown)
        super(1.8f, 15, Gdx.audio.newSound(Gdx.files.internal("canonLaserSoud.mp3")));
        this.texturaLaser = new Texture(Gdx.files.internal("cosaFea.png")); // o la textura que uses
        this.soundLaser = this.soundBala;
    }

    @Override
    public void disparar(Jugador nave, PantallaJuego pantalla, float delta) {
        // avanza cooldown base
        actualizar(delta);

        // tiro a tiro: solo dispara cuando el gate de cadencia lo permite
        if (!puedeDisparar() || municion <= 0) return;

        // crear rayo como pulso (no se refresca cada frame)
        LaserBeam rayo = new LaserBeam(nave, anchoLaser, texturaLaser, estiloRayo);
        rayo.configurarPulso(duracionPulso); // fija TTL del pulso

        // registrar en melee (para update/draw/colisión existentes)
        pantalla.agregarSwing(rayo);

        // feedback y consumo
        if (soundLaser != null) soundLaser.play(0.1f);
        municion = Math.max(0, municion - 1);

        // entra en cooldown; aunque se mantenga Z, no se disparará de nuevo hasta que termine
        reiniciarCooldown();
    }
}
