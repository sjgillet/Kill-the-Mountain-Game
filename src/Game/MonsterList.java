package Game;

import java.util.ArrayList;

public class MonsterList {
	
	public ArrayList<Enemy> monsters;
	private ArrayList<Double[]> weights;
	
	
	private final Enemy golem = new Enemy("Golem", "A big rock with legs.", 1.0);
	private final Enemy goblin = new Enemy("Goblin", "Ugly little thing. Doesn't look that threatening.", 0.3);
	
	public MonsterList()
	{
		this.monsters = new ArrayList<Enemy>();
		this.weights = new ArrayList<Double[]>();

		golem.setWeights(0.1, 0.3, 0.05, 0.5, 0.05, 0.075, 0.0, 0.2, 0.225, 0.1);
		monsters.add(golem);
		goblin.setWeights(0.15, 0.08, 0.12, 0.2, 0.075, 0.125, 0.0, 0.05, 0.05, 0.05);
		monsters.add(goblin);
		
		
	}
	
	public double getLowestCR()
	{
		double min = monsters.get(0).getChallenge();
		for(Enemy e : monsters)
			if(e.getChallenge() < min)
				min = e.getChallenge();
		return min;
	}
	
	
	
	
	
	
	
	
}
