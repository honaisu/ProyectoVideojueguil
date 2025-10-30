package enumeradores;

import interfaces.AssetRoute;

public enum GameSound implements AssetRoute {
	EXPLOSION("audios/explosionSound.ogg"),
	HURT("audios/popSound.mp3"),
	SHOOT("audios/danoSound.mp3"),
	DEATH("audios/muerteSound.mp3");
	
	private String ruta;
	
	GameSound(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public String getRuta() {
		return ruta;
	}

}
