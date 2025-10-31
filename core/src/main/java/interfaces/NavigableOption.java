package interfaces;

import logica.MainGame;

public interface NavigableOption {
	public String getNombre();
	public int ordinal();
	
	default public void ejecutar(MainGame game) {}
}