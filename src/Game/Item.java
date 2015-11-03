package Game;

public class Item {

	String name = "";
	boolean onGround;
	int attack;
	double speed;
	int mana;
	int health;
	int armor;
	int xID;
	int yID;

	//item types
	String type;

	public Item(String name, boolean onGround, String type) {

		this.name = name;
		this.onGround = onGround;
		this.type = type;

	}
}
