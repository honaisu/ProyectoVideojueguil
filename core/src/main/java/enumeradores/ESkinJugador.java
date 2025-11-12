package enumeradores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import interfaces.IAssetRoute;
import interfaces.INavigableOption;
import managers.AssetManager;

public enum ESkinJugador implements INavigableOption, IAssetRoute {
	JUGADOR_ORIGINAL("Skin Original", "textures/player/JugadorOriginal.png"),
	JUGADOR_ALT_1("Skin Alt 1", "textures/player/JugadorAlt1.png"),
	JUGADOR_ALT_2("Skin Alt 2", "textures/player/JugadorAlt2.png");
	
	private final String nombre;
	private final String ruta;
	private Sprite sprite;
	
	ESkinJugador(String nombre, String ruta) {
		this.nombre = nombre;
		this.ruta = ruta;
	}
	
	public Sprite crearSprite() {
		if (sprite == null) {
				Texture textura = AssetManager.getInstancia().getSkinTexture(this);				
				this.sprite = new Sprite(textura, 64, 64); 
				sprite.setScale(2f);
		}
		return sprite;
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
