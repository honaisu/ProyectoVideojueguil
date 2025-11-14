package enumeradores.recursos;

import interfaces.IAssetRoute;
import interfaces.INavigableOption;

public enum EPlayerSkin implements INavigableOption, IAssetRoute {
	ORIGINAL("Skin Original", "JugadorOriginal.png"),
	ALT_1("Skin Alt 1", "JugadorAlt1.png"),
	ALT_2("Skin Alt 2", "JugadorAlt2.png");
	
	private final String nombre;
	private final String ruta;
	
	EPlayerSkin(String nombre, String ruta) {
		this.nombre = nombre;
		this.ruta = "textures/player/" + ruta;
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
