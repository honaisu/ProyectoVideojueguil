package personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import logica.AssetsLoader;

public enum SkinJugador {
	JUGADOR_ORIGINAL("Skin Original", "JugadorOriginal.png"),
	JUGADOR_ALT_1("Skin Alt 1", "JugadorAlt1.png"),
	JUGADOR_ALT_2("Skin Alt 2", "JugadorAlt2.png");
	
	private String nombre;
	private String rutaTextura;
	
	SkinJugador(String nombre, String rutaTextura) {
		this.nombre = nombre;
		this.rutaTextura = rutaTextura;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getRutaTextura()  {
		return rutaTextura;
	}
	
	public Sprite crearSprite() {
        Texture textura = AssetsLoader.getInstancia().getSkinTexture(this);
        return new Sprite(textura, 64, 64);
	}
}
