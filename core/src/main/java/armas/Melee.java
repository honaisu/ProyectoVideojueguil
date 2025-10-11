package armas;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import hitboxes.ArcHitbox;

public class Melee extends Arma {

    private float radioAtaque = 80f;
    private float anguloInicio = -60f; // ejemplo: semicírculo frontal
    private float anguloFin = 60f;
    private ArcHitbox hitbox;

    public Melee(int daño, float cadencia, int municion, Sprite spr) {
        super(daño, cadencia, municion, spr);
        // no necesitas "area" como atributo si usas radio + ángulos
    }

    /** Llama a esto cuando el jugador realiza un ataque. */
    public void atacar(Vector2 posicionJugador, float direccionJugador, Texture efectoTextura) {
        // Calculamos el arco frente al jugador, rotado según la dirección
        float inicioRotado = direccionJugador + anguloInicio;
        float finRotado = direccionJugador + anguloFin;
        hitbox = new ArcHitbox(posicionJugador, radioAtaque, inicioRotado, finRotado, efectoTextura);
    }

    /** Verifica si golpea a un enemigo. */
    public boolean golpea(Vector2 posicionEnemigo) {
        if (hitbox == null) return false;
        return hitbox.contiene(posicionEnemigo);
    }

    /** Dibuja el arco (solo visualización opcional). */
    public void draw(SpriteBatch batch) {
        if (hitbox != null) {
            hitbox.draw(batch);
        }
    }
}

