package armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import armas.proyectiles.Swing;
import pantallas.PantallaJuego;
import personajes.Jugador;

public class Melee extends Arma {

    private Swing swingActual;

    public Melee() {
        super(0.8f, 9999, new Texture(Gdx.files.internal("semicirculo.png")), Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));  // cadencia de medio segundo, sin munición real
    }

    @Override
    public void disparar(Jugador nave, PantallaJuego juego, float delta) {
        actualizar(delta);

        // No hay munición que contar en ataques melee
        if (!puedeDisparar()) return;

        // Crear golpe (Swing)
        crearSwing(nave, juego);
        reiniciarCooldown();
    }

    private void crearSwing(Jugador nave, PantallaJuego juego) {
    	float radians = (float) Math.toRadians(nave.getRotacion() + 90);

        float centerX = nave.spr.getX() + nave.spr.getWidth() / 2;
        float centerY = nave.spr.getY() + nave.spr.getHeight() / 2;
        float length = nave.spr.getHeight() / 2;
        
        float radio = 100f;  // Alcance del golpe
        
        float swingX = centerX + (float) Math.cos(radians) * length;
        float swingY = centerY + (float) Math.sin(radians) * length;
        
        soundBala.play(0.1f);
        swingActual = new Swing(swingX, swingY, radio , nave);
        juego.agregarSwing(swingActual);
        

    }

    public Swing getSwingActual() {
        return swingActual;
    }
}

