package enumeradores.recursos;

import interfaces.IAssetPath;

public enum EGameMusic implements IAssetPath {
	MAIN("doom.mp3", true),
	TUTORIAL("tutorial.mp3", false);

	private final String ruta;
	private final boolean loop;
	
	EGameMusic(String ruta, boolean loop) {
		this.ruta = "audios/music/" + ruta;
		this.loop = loop;
	}
	
	@Override
	public String getRuta() {
		return ruta;
	}
	
	public boolean hasLoop() {
		return loop;
	}
}
