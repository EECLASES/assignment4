package assignment4;


/*
 * This critter has a randomized mood. If its mood is 0, it does nothing.
 * If its mood is 1, it walks in any random direction. If its mood is 2,
 * it runs in any random direction. If its mood is 3, it reproduces. If its 
 * mood is one of the first two cases, it surrenders in fights. If its of
 * the latter two cases, it wants to fight.
 */
public class NumanCritter1 extends Critter {

	int mood;
	
	public NumanCritter1() {
		mood = Critter.getRandomInt(4);
	}
	
	@Override
	public String toString() { return "N"; }
		
	@Override
	public void doTimeStep() {

		if(mood == 1) {
			walk(Critter.getRandomInt(8));
		} else if(mood == 2) {
			run(Critter.getRandomInt(8));
		} else if(mood == 3) {
			NumanCritter1 child = new NumanCritter1();
			reproduce(child,Critter.getRandomInt(8));
		}
		
	}

	@Override
	public boolean fight(String oponent) {
		
		if(mood < 2) {
			return false;
		} else {
			return true;
		}
		
	}
	
}
