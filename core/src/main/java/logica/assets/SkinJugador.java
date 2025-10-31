package logica.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import interfaces.AssetRoute;
import interfaces.NavigableOption;

public enum SkinJugador implements NavigableOption, AssetRoute {
	JUGADOR_ORIGINAL("Skin Original", "JugadorOriginal.png"),
	JUGADOR_ALT_1("Skin Alt 1", "JugadorAlt1.png"),
	JUGADOR_ALT_2("Skin Alt 2", "JugadorAlt2.png");
	
	private final String nombre;
	private final String ruta;
	
	SkinJugador(String nombre, String ruta) {
		this.nombre = nombre;
		this.ruta = ruta;
	}
	
	public Sprite crearSprite() {
		Texture textura = AssetManager.getInstancia().getSkinTexture(this);
		return new Sprite(textura, 64, 64);
	}
	
	@Override
	public String getRuta()  {
		return ruta;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}
}
