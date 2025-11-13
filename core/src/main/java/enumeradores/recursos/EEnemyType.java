package enumeradores.recursos;

import interfaces.IAssetRoute;

public enum EEnemyType implements IAssetRoute {
	GENERIC("Mono.png");
	
	private final String ruta;
	
	EEnemyType(String ruta) {
		this.ruta = "textures/enemies/" + ruta;
	}

	@Override
	public String getRuta() {
		return ruta;
	}
}
