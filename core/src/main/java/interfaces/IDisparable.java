package interfaces;

import entidades.Player;
import managers.ProjectileManager;

public interface IDisparable {
	public void atack(float delta, Player p, ProjectileManager manager);
}
