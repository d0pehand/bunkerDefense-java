package unit;

import main.*;

public class Unit {
	
	public String name;
	public int attack;
	public int plusAttack;
	public int range;
	public boolean splash;
	
	public int attack() {
		return attack + plusAttack * Hero.upgrade;
	}
	
}
