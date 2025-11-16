package factories;

import com.badlogic.gdx.audio.Sound;

import enumeradores.recursos.EGameSound;
import managers.assets.AssetManager;

public class SoundFactory {
	private SoundFactory() {}
	
	public static Sound getSound(EGameSound sound) {
		return AssetManager.getInstancia().getSound(sound);
	}
}
