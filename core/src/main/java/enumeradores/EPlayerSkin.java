package enumeradores;

import interfaces.ITexture;
import interfaces.INavigableOption;

public enum EPlayerSkin implements INavigableOption, ITexture {
	ORIGINAL("Skin Original", "original.png"),
	ALT_1("Skin Alt 1", "alt_1.png"),
	ALT_2("Skin Alt 2", "alt_2.png")
	;
	
	EPlayerSkin(String nombre, String ruta) {
		this.nombre = nombre;
		this.ruta = "textures/player/" + ruta;
	}

	private final String nombre;
	private final String ruta;
	private final int width = DEFAULT_SIZE;
	private final int height = DEFAULT_SIZE;

	@Override
	public String getRuta() { return ruta; }
	
	@Override
	public String getName() { return nombre; }
	
	@Override
	public int getWidth() { return width; }

	@Override
	public int getHeight() { return height; }
}
