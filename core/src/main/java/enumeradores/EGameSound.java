package enumeradores;

import interfaces.IAssetRoute;

public enum EGameSound implements IAssetRoute {
	EXPLOSION("audios/explosionSound.ogg"),
	HURT("audios/popSound.mp3"),
	SHOOT("audios/danoSound.mp3"),
	DEATH("audios/muerteSound.mp3");
	
	private String ruta;
	
	EGameSound(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public String getRuta() {
		return ruta;
	}

}
