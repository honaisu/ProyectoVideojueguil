package enumeradores.recursos.texturas;

import interfaces.ITexture;

public enum EHealthBarType implements ITexture{
	// ESPECIALES
	NORMAL("healthbar.png");
	
	EHealthBarType(String ruta) {
		this.ruta = "textures/healthbars/" + ruta;
	}
	
	private final String ruta;
	private final int width = 96;
	private final int height = 352;

	@Override
	public String getRuta() { return ruta; }
	
	@Override
	public int getWidth() { return width; }

	@Override
	public int getHeight() { return height; }
}