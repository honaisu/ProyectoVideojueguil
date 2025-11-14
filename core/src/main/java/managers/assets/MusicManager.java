package managers.assets;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

import enumeradores.recursos.EGameMusic;

public class MusicManager implements Disposable {
	private Map<EGameMusic, Music> musics = new EnumMap<>(EGameMusic.class);

	public void load() {
		for (EGameMusic eMusic : EGameMusic.values()) {
    		Music newMusic = Gdx.audio.newMusic(Gdx.files.internal(eMusic.getRuta()));
    		newMusic.setLooping(eMusic.hasLoop());
    		musics.put(eMusic, newMusic);
    	}
	}
	
	@Override
	public void dispose() {
		for (Music music : musics.values()) {
    		music.dispose();
    	}
		musics.clear();
	}

	public Music get(EGameMusic music) {
		return musics.get(music);
	}
}
