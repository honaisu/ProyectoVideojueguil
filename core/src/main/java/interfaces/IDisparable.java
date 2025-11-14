package interfaces;

import entidades.Player;
import managers.ProjectileManager;

public interface IDisparable {
	public void atacar(float delta, Player p, ProjectileManager manager);
}
