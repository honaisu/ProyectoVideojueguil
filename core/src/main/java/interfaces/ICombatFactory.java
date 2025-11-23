package interfaces;

/**
 * Factory abstracta que se encarga de poder crear un "combate". Su principal
 * uso es para poder crear armas (atacables) en base a estados y estrategias.
 */
public interface ICombatFactory {
	IAttackable createWeapon(IState state, IAttackStrategy strategy);

	IAttackStrategy createStrategy();

	IState createState();
}
