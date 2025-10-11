package hitboxes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Representa un ataque en forma de arco (semicírculo).
 */
public class ArcHitbox {
    private Vector2 centro;
    private float radio;
    private float anguloInicio;
    private float anguloFin;
    private Texture textura; // opcional, si quieres visualizar el arco

    public ArcHitbox(Vector2 centro, float radio, float anguloInicio, float anguloFin, Texture textura) {
        this.centro = centro;
        this.radio = radio;
        this.anguloInicio = anguloInicio;
        this.anguloFin = anguloFin;
        this.textura = textura;
    }

    public boolean contiene(Vector2 punto) {
        Vector2 dir = new Vector2(punto).sub(centro);
        float distancia = dir.len();
        if (distancia > radio) return false;

        float angulo = dir.angleDeg(); // devuelve el ángulo entre 0 y 360
        return angulo >= anguloInicio && angulo <= anguloFin;
    }

    public void draw(SpriteBatch batch) {
        if (textura != null) {
            batch.draw(textura, centro.x - radio, centro.y - radio, radio * 2, radio * 2);
        }
    }

    public void setCentro(Vector2 centro) {
        this.centro = centro;
    }
}
