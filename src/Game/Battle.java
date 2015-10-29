package Game;

import java.util.Random;
import java.util.ArrayList;

public class Battle {
	CombatEntity player;
	ArrayList<CombatEntity> enemies;
	MonsterList monsters = new MonsterList();
	Random rand = new Random();
	
	public Battle()
	{
		
	}
	public Battle(double challengeRating)
	{
		/* Choose opponents*/
		while(challengeRating > monsters.getLowestCR())
			enemies.add(getOpponent(challengeRating)); 
	}
	
	/**
	 * Retrieve a random monster whose challenge is less than or equal to a given
	 * double-precision value
	 * @param challengeRating	Determines the highest difficulty creature to be fought
	 * @return	The Enemy to be added to the group of monsters the player will fight
	 */
	private Enemy getOpponent(double challengeRating)
	{
		Enemy opponent;
		
		//retrieve a random monster
		while(true)
		{
			opponent = monsters.monsters.get(rand.nextInt(monsters.monsters.size() - 1));
			if(opponent.getChallenge() >= challengeRating)
				{
					opponent.Initialize();
					return opponent;
				}
		}
	}
}
