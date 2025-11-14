package enumeradores.recursos;

import interfaces.IAssetRoute;

public enum EBackgroundType implements IAssetRoute {
	ONE("fondoNivelUno.png"),
	TWO("fondoNivelDos.png");
	
	private final String ruta;
	
	EBackgroundType(String ruta) {
		this.ruta = "fondos/" + ruta;
	}

	@Override
	public String getRuta() {
		return ruta;
	}
}
