package managers.assets;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import enumeradores.recursos.EGameSound;

public class SoundManager implements Disposable {
	private Map<EGameSound, Sound> sounds = new EnumMap<>(EGameSound.class);
	
	public void load() {
		for (EGameSound eSound : EGameSound.values()) {
    		Sound newSound = Gdx.audio.newSound(Gdx.files.internal(eSound.getRuta()));
    		sounds.put(eSound, newSound);
    	}
	}

	public Sound get(EGameSound sound) {
		return sounds.get(sound);
	}
	
	public Sound get(String name) {
		return sounds.get(EGameSound.valueOf(name));
	}

	@Override
	public void dispose() {
		for (Sound sound : sounds.values()) {
			sound.dispose();
		}
		sounds.clear();
	}
}
